package com.bbva.datio.datahubpe.modelMatrixIC.transformations.rccProcess

import com.bbva.datio.datahubpe.modelMatrixIC.beans.MatrixIcOut
import com.bbva.datio.datahubpe.modelMatrixIC.config.{AccountSbsAggregate, Parameter, RccBalance}
import com.bbva.datio.datahubpe.utils.processing.data.DataReader
import com.bbva.datio.datahubpe.utils.processing.flow.Transformer
import com.bbva.datio.datahubpe.modelMatrixIC.utils.caseUtils.getAlias
import org.apache.spark.sql.expressions.Window.partitionBy
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.{col, lit, row_number}

/** Class PersonMainBankCC for process data of model Matrix IC.
 *
 *  @constructor create a new PersonMainBankCC with parameters: parameter
 *  @param parameter
 */
class PersonMainBankCC (parameter: Parameter) extends Transformer[DataReader, DataFrame]{

  val rccBalance : RccBalance = parameter.fields.rccBalance
  val accountSbsAggregate : AccountSbsAggregate = parameter.fields.accountSbsAggregate
  val matrixIcOut: MatrixIcOut = new MatrixIcOut(parameter.fields.matrixIcOut)
  //Alias
  val A = "A"
  val B = "B"
  val orden = "Orden"
  //Column
  val rnkProdGroupSitEnt = "rnk_by_bal_product_group_sit_ent"
  val debt = "debt"
  val creditLine = "credit_line"
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
    * We make the filters so that the crossing is only with TC and has a principal balance> 0
    */
    personCCRVariables2.alias(A)
      .join(
        dfGroupBalanceCCR.alias(B),
        col(getAlias(A,matrixIcOut.personalType)) === col(getAlias(B,matrixIcOut.personalType))
        && col(getAlias(A,matrixIcOut.personalId)) === col(getAlias(B,matrixIcOut.personalId))
        && col(debt) === col(matrixIcOut.fsPrincipalConsumerCcBalanceAmount)
      ).filter(
        col(getAlias(B,rnkProdGroupSitEnt)) === lit(1)
          && whenCreditCardNP
          && col(matrixIcOut.fsPrincipalConsumerCcBalanceAmount) > 0
      ).select(
      col(getAlias(A,matrixIcOut.personalType)),
      col(getAlias(A,matrixIcOut.personalId)),
      col(getAlias(B,rccBalance.sbsEntityId)).alias(matrixIcOut.mainCcBank),//El banco principal
      col(creditLine).alias(matrixIcOut.fsConsumerCreditcardPrincipalLineAmount),//La linea principal proviene del saldo mayor
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
