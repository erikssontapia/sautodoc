package com.bbva.datio.datahubpe.modelMatrixIC.transformations.productProcess

import com.bbva.datio.datahubpe.modelMatrixIC.beans.MatrixIcOut
import com.bbva.datio.datahubpe.modelMatrixIC.config.{Parameter, PeopleDaily}
import com.bbva.datio.datahubpe.modelMatrixIC.utils.caseUtils.{getAlias, getAllColumsAlias}
import com.bbva.datio.datahubpe.utils.processing.data.DataReader
import com.bbva.datio.datahubpe.utils.processing.flow.Transformer
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.{col, lit}

/** Class ProductProcessConsolidation for process data of model Matrix IC.
 *
 *  @constructor create a new ProductProcessConsolidation with parameters: parameter
 *  @param parameter
 */
class ProductProcessConsolidation(parameter: Parameter) extends Transformer[DataReader, DataFrame]{

  val PeopleDaily: PeopleDaily = parameter.fields.peopleDaily
  val MatrixIcOut: MatrixIcOut = new MatrixIcOut(parameter.fields.matrixIcOut)

  // Alias
  val PLC = "PLC"
  val MC = "MC"

  /** Method for transform with a given dataReader
   *
   *  @param dataReader
   *  @return a new DataFrame with a given dataReader
   */
  override def transform(dataReader: DataReader): DataFrame = {
    val dfCardConsolidation = new CardConsolidation(parameter).transform(dataReader)
    dataReader.add("dfCardConsolidation", dfCardConsolidation)

    val dfProductBbvaConctractConsolidation = new ProductBbvaContractConsolidation(parameter).transform(dataReader)
    dataReader.add("dfProductBbvaContractConsolidation", dfProductBbvaConctractConsolidation)

    val dfPeopleLevelConsolidation = new PeopleLevelConsolidation(parameter).transform(dataReader)

    val dfMarginConsolidation = new MarginConsolidation(parameter).transform(dataReader)

    dfPeopleLevelConsolidation.alias(PLC).join(
      dfMarginConsolidation.alias(MC),
      (col(getAlias(PLC, PeopleDaily.personalId)) === col(getAlias(MC, PeopleDaily.personalId)))&&
        (col(getAlias(PLC, PeopleDaily.personalType)) === col(getAlias(MC,  PeopleDaily.personalType))),
      "left"
    ).
      select(
        col(getAllColumsAlias(PLC)),
        col(getAlias(MC, MatrixIcOut.marMonthlyAmount)),
        col(getAlias(MC, MatrixIcOut.marYearlyAmount))
      )
  }
}
