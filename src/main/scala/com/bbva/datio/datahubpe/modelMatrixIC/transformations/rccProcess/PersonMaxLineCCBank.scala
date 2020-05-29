package com.bbva.datio.datahubpe.modelMatrixIC.transformations.rccProcess

import com.bbva.datio.datahubpe.modelMatrixIC.beans.MatrixIcOut
import com.bbva.datio.datahubpe.modelMatrixIC.config.{AccountSbsAggregate, Parameter, RccBalance}
import com.bbva.datio.datahubpe.utils.processing.data.DataReader
import com.bbva.datio.datahubpe.utils.processing.flow.Transformer
import com.bbva.datio.datahubpe.modelMatrixIC.utils.caseUtils.getAlias
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.expressions.Window.partitionBy
import org.apache.spark.sql.functions.{col, lit, row_number}

/** Class PersonMaxLineCCBank for process data of model Matrix IC.
 *
 *  @constructor create a new PersonMaxLineCCBank with parameters: parameter
 *  @param parameter
 */
class PersonMaxLineCCBank (parameter: Parameter) extends Transformer[DataReader, DataFrame]{

  val accountSbsAggregate : AccountSbsAggregate = parameter.fields.accountSbsAggregate
  val matrixIcOut: MatrixIcOut = new MatrixIcOut(parameter.fields.matrixIcOut)
  val rccBalance : RccBalance = parameter.fields.rccBalance

  //Alias
  val A = "A"
  val B = "B"
  val orden = "Orden"
  val creditLine = "credit_line"
  val rnkProdGroupSitEnt = "rnk_by_bal_product_group_sit_ent"
  val whenCreditCardNP = col(accountSbsAggregate.commercialProductId) === lit(2) and col(accountSbsAggregate.creditType) === lit(11)

  /** Method for transform with a given dataReader
   *
   *  @param dataReader
   *  @return a new DataFrame with a given dataReader
   */
  override def transform(dataReader: DataReader): DataFrame = {
    val personCCRVariables2 = dataReader.get("personCCRVariables2")
    val dfGroupBalanceCCR = dataReader.get("dfGroupBalanceCCR")

    /*
    * We cross by client and that the maximum line is equal to some line of the RCC group
    * we make the filters so that the crossing is only with TC
    */
    personCCRVariables2.alias(A)
      .join(
        dfGroupBalanceCCR.alias(B),
        col(getAlias(A,matrixIcOut.personalType)) === col(getAlias(B,matrixIcOut.personalType))
          && col(getAlias(A,matrixIcOut.personalId)) === col(getAlias(B,matrixIcOut.personalId))
          && col(getAlias(B,creditLine)) === col(getAlias(A,matrixIcOut.maxCcLine))
      ).filter(
        col(matrixIcOut.maxCcLine) > 0
          && col(getAlias(B,rnkProdGroupSitEnt)) === lit(1)
          && whenCreditCardNP
      ).select(
        col(getAlias(A,matrixIcOut.personalType)),
        col(getAlias(A,matrixIcOut.personalId)),
        col(getAlias(B,rccBalance.sbsEntityId)).alias(matrixIcOut.maxCcLineBank),
        row_number().over(
          partitionBy(col(getAlias(A,matrixIcOut.personalType)),
            col(getAlias(A,matrixIcOut.personalId))
          ).orderBy(col(getAlias(B,rccBalance.sbsEntityId)).asc))
          .alias(orden)
      ).filter(
        col(orden) === lit("1")
      ).drop(orden)
  }

}
