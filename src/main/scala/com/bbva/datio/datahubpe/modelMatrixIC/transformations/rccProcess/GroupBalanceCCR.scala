package com.bbva.datio.datahubpe.modelMatrixIC.transformations.rccProcess

import com.bbva.datio.datahubpe.modelMatrixIC.config.{AccountSbsAggregate, CodeSbsAggregate, Parameter, RccBalance}
import com.bbva.datio.datahubpe.modelMatrixIC.utils.caseUtils.getAlias
import com.bbva.datio.datahubpe.utils.processing.data.DataReader
import com.bbva.datio.datahubpe.utils.processing.flow.Transformer
import org.apache.spark.sql.{Column, DataFrame}
import org.apache.spark.sql.expressions.Window.partitionBy
import org.apache.spark.sql.functions._

/** Class GroupBalanceCCR for process data of model Matrix IC.
 *
 *  @constructor create a new GroupBalanceCCR with parameters: parameter
 *  @param parameter
 */
class GroupBalanceCCR (parameter: Parameter) extends Transformer[DataReader, DataFrame]{

  val accountSbsAggregate : AccountSbsAggregate = parameter.fields.accountSbsAggregate
  val codeSbsAggregate : CodeSbsAggregate = parameter.fields.codeSbsAggregate
  val rccBalance : RccBalance = parameter.fields.rccBalance

  //Alias
  val MAE = "MAE"
  val BAL = "BAL"
  val DEF = "DEF"

  //campos
  val accCreditLine = "acc_credit_line"
  val unusedLine = "unused_line"
  val creditLine = "credit_line"
  val debt = "debt"
  val rnkProdGroupSitEnt = "rnk_by_bal_product_group_sit_ent"
  val rnkProdEnt = "rnk_by_bal_product_ent"

  /** Method for get Validate Balance Registered Rcc Type with a given RccType
   *
   *  @param RccType
   *  @return a new Column with a given RccType
   */
  def getValidateBalanceRegisteredRccType(RccType: Int): Column = {
    when(col(getAlias(DEF, accountSbsAggregate.balanceRegisteredRccType)) === lit(RccType),
      col(getAlias(BAL,rccBalance.balanceAmount))
    ).otherwise(
      0
    )
  }

  /** Method for get Credit Line with a given 
   *
   *  @return a new Column with a given 
   */
  def getCreditLine(): Column = {
    when(
      col(accCreditLine) > lit(0), col(accCreditLine)
    ).otherwise(
      col(debt) + col(unusedLine)
    )
  }

  /** Method for get Rnk Prod Group Sit Ent with a given 
   *
   *  @return a new Column with a given 
   */
  def getRnkProdGroupSitEnt(): Column = {
    row_number().over(
      partitionBy(
        col(codeSbsAggregate.personalId),
        col(codeSbsAggregate.personalType),
        col(rccBalance.sbsEntityId),
        col(accountSbsAggregate.productGroupType),
        col(accountSbsAggregate.commercialProductId),
        col(accountSbsAggregate.commercialSubproductId),
        col(accountSbsAggregate.creditType)
      ).orderBy(
        col(debt).desc,
        col(accCreditLine).desc,
        (col(debt) + col(unusedLine)).desc
      )
    )
  }

  /** Method for get Rnk Prod Ent with a given 
   *
   *  @return a new Column with a given 
   */
  def getRnkProdEnt(): Column = {
    row_number().over(
      partitionBy(
        col(codeSbsAggregate.personalId),
        col(codeSbsAggregate.personalType),
        col(rccBalance.sbsEntityId),
        col(accountSbsAggregate.productGroupType),
        col(accountSbsAggregate.commercialProductId),
        col(accountSbsAggregate.commercialSubproductId),
        col(accountSbsAggregate.creditType)
      ).orderBy(
        sum(col(debt)).over(
          partitionBy(
            col(codeSbsAggregate.personalId),
            col(codeSbsAggregate.personalType),
            col(rccBalance.sbsEntityId),
            col(accountSbsAggregate.productGroupType),
            col(accountSbsAggregate.commercialProductId),
            col(accountSbsAggregate.commercialSubproductId)
          )
        )
      )
    )
  }

  /** Method for transform with a given dataReader
   *
   *  @param dataReader
   *  @return a new DataFrame with a given dataReader
   */
  override def transform(dataReader: DataReader): DataFrame = {

    val dfAccountSbsAggregate = dataReader.get("accountSbsAggregate")
    val dfCodeSbsAggregate = dataReader.get("codeSbsAggregate")
    val dfRccBalance = dataReader.get("rccBalance")

    dfCodeSbsAggregate.alias(MAE).
      join(
        dfRccBalance.alias(BAL),
        col(getAlias(MAE, codeSbsAggregate.sbsCustomerId)) === col(getAlias(BAL, rccBalance.sbsCustomerId)),
        "inner"
      ).join(
        dfAccountSbsAggregate.alias(DEF),
        col(getAlias(DEF, accountSbsAggregate.productDefinerId)) === col(getAlias(BAL, accountSbsAggregate.productDefinerId)) and
        col(getAlias(DEF, accountSbsAggregate.sbsCreditType)) === col(getAlias(BAL, rccBalance.sbsCreditType)),
        "inner"
      ).groupBy(
        col(getAlias(MAE, codeSbsAggregate.personalId)),
        col(getAlias(MAE, codeSbsAggregate.personalType)),
        col(getAlias(BAL, rccBalance.sbsEntityId)),
        col(getAlias(DEF, accountSbsAggregate.productGroupType)),
        col(getAlias(DEF, accountSbsAggregate.commercialProductId)),
        col(getAlias(DEF, accountSbsAggregate.commercialSubproductId)),
        col(getAlias(DEF, accountSbsAggregate.creditType))
      ).agg(
        sum(getValidateBalanceRegisteredRccType(1)).alias(debt),
        sum(getValidateBalanceRegisteredRccType(2)).alias(unusedLine),
        sum(getValidateBalanceRegisteredRccType(3)).alias(accCreditLine)
      ).select(
        col("*") ,
        //CreditLine
        getCreditLine().alias(creditLine),
        //RnkProdGroupSitEnt
        getRnkProdGroupSitEnt().alias(rnkProdGroupSitEnt),
        //RnkProdEnt
        getRnkProdEnt().alias(rnkProdEnt)
      )
  }

}
