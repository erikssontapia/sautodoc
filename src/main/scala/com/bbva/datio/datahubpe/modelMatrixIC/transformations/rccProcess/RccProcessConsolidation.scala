package com.bbva.datio.datahubpe.modelMatrixIC.transformations.rccProcess

import com.bbva.datio.datahubpe.modelMatrixIC.beans.MatrixIcOut
import com.bbva.datio.datahubpe.modelMatrixIC.config.Parameter
import com.bbva.datio.datahubpe.utils.processing.data.DataReader
import com.bbva.datio.datahubpe.utils.processing.flow.Transformer
import org.apache.spark.sql.DataFrame

/** Class RccProcessConsolidation for process data of model Matrix IC.
 *
 *  @constructor create a new RccProcessConsolidation with parameters: parameter
 *  @param parameter
 */
class RccProcessConsolidation (parameter: Parameter) extends Transformer[DataReader, DataFrame] {

  val matrixIcOut: MatrixIcOut = new MatrixIcOut(parameter.fields.matrixIcOut)

  /** Method for transform with a given dataReader
   *
   *  @param dataReader
   *  @return a new DataFrame with a given dataReader
   */
  override def transform(dataReader: DataReader): DataFrame = {

    val dfGroupBalanceCCR = new GroupBalanceCCR(parameter).transform(dataReader)
    dataReader.add("dfGroupBalanceCCR", dfGroupBalanceCCR)

    val personCCRVariables1 = new PersonCCRV1(parameter).transform(dataReader)

    val personBalVariables1 = new PersonBalV1(parameter).transform(dataReader)

    val personCCRVariables2 = new PersonCCRV2(parameter).transform(dataReader)
    dataReader.add("personCCRVariables2", personCCRVariables2)

    val personMainBankCC = new PersonMainBankCC(parameter).transform(dataReader)
    dataReader.add("personMainBankCC", personMainBankCC)

    val personMaxLineCCBank = new PersonMaxLineCCBank(parameter).transform(dataReader)
    dataReader.add("personMaxLineCCBank", personMaxLineCCBank)

    val personSpecialFeaturesCC = personMainBankCC.join(personMaxLineCCBank,
      Seq(matrixIcOut.personalId, matrixIcOut.personalType),
      "full")
    val personCCRVariables3 = new PersonCCRV3(parameter).transform(dataReader)

    personCCRVariables1
      .join(personCCRVariables2, usingColumns = Seq(matrixIcOut.personalId, matrixIcOut.personalType), "inner")
      .join(personCCRVariables3, usingColumns = Seq(matrixIcOut.personalId, matrixIcOut.personalType), "inner")
      .join(personSpecialFeaturesCC, usingColumns = Seq(matrixIcOut.personalId, matrixIcOut.personalType), "left")
      .join(personBalVariables1, usingColumns = Seq(matrixIcOut.personalId, matrixIcOut.personalType), "left")
  }
}
