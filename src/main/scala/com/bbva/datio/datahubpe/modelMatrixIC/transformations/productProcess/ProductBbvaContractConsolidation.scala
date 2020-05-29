package com.bbva.datio.datahubpe.modelMatrixIC.transformations.productProcess

import com.bbva.datio.datahubpe.modelMatrixIC.beans.{CardMaster, MatrixIcOut}
import com.bbva.datio.datahubpe.modelMatrixIC.config._
import com.bbva.datio.datahubpe.modelMatrixIC.utils.caseUtils.{getAlias, nullToEmptyString}
import com.bbva.datio.datahubpe.utils.processing.data.DataReader
import com.bbva.datio.datahubpe.utils.processing.flow.Transformer
import org.apache.spark.sql.{Column, DataFrame, Row}
import org.apache.spark.sql.functions._

/** Class ProductBbvaContractConsolidation for process data of model Matrix IC.
 *
 *  @constructor create a new ProductBbvaContractConsolidation with parameters: parameter
 *  @param parameter
 */
class ProductBbvaContractConsolidation(parameter: Parameter) extends Transformer[DataReader, DataFrame] {

  val ParameterFileIc: ParameterFileIc = parameter.fields.parameterFileIc
  val BalanceAggregateMonthly: BalanceAggregateMonthly = parameter.fields.balanceAggregateMonthly
  val ContractMaster: ContractMaster = parameter.fields.contractMaster
  val PeopleDaily: PeopleDaily = parameter.fields.peopleDaily
  val ProductSubproduct: ProductSubproduct = parameter.fields.productSubproduct
  val ProductLoanDaily: ProductLoanDaily = parameter.fields.productLoanDaily
  val CardMaster: CardMaster = new CardMaster(parameter.fields.cardMaster)
  val Bin: Bin = parameter.fields.bin
  val MatrixIcOut: MatrixIcOut = new MatrixIcOut(parameter.fields.matrixIcOut)

  // Alias
  val CMA = "CMA"
  val BAM = "BAM"
  val PD = "PD"
  val PRO = "PRO"
  val CAR = "CAR"
  val LOA = "LOA"

  val currentBalanceAvg = "current_balance_avg"
  val creditLine = "credit_line"
  val productMasterType = "product_master_type"
  val productGroup = "product_group"

  // Others
  val double = "double"

  /** Method for prepare Df Parameter File Ic with a given dfParameterFileIc
   *
   *  @param dfParameterFileIc
   *  @return a new DataFrame with a given dfParameterFileIc
   */
  def prepareDfParameterFileIc(dfParameterFileIc: DataFrame): DataFrame = {
    dfParameterFileIc.filter(
      col(ParameterFileIc.executionProcessId) === lit(parameter.local.productBBVAGroupProcessCode)
    ).select(
      col(ParameterFileIc.parameterId),
      col(ParameterFileIc.parameterDesc),
      col(ParameterFileIc.parameterValue1Desc),
      col(ParameterFileIc.parameterValue3Desc),
      col(ParameterFileIc.parameterValue5Desc),
      col(ParameterFileIc.parameterValue7Desc),
      col(ParameterFileIc.parameterValue9Desc),
      col(ParameterFileIc.parameterValue11Desc),
      col(ParameterFileIc.parameterValue13Desc))
  }

  /** Method for get Group Product Value with a given row
   *
   *  @param row
   *  @return a new Column with a given row
   */
  def getGroupProductValue(row: Row): Column = {

    val prodGroupName = row.getString(1)
    val commerProdIDLike = nullToEmptyString(row.getString(2))
    val commerProdNameLike = nullToEmptyString(row.getString(3))
    val creditLineGT = row.getString(4)
    val currentBalanceGT = row.getString(5)
    val prodMasterType = row.getString(6)
    val cardBlockType = row.getString(7)
    val cardBusinessType = row.getString(8)

    when(
      (lit(commerProdIDLike) === lit("") || col(ProductSubproduct.commercialProductId).like(commerProdIDLike))
        && (lit(commerProdNameLike) === lit("") || col(ProductSubproduct.commercialProductDesc).like(commerProdNameLike))
        && (lit(creditLineGT).isNull || col(creditLine) > lit(creditLineGT).cast(double))
        && (lit(currentBalanceGT).isNull || col(currentBalanceAvg) > lit(currentBalanceGT).cast(double))
        && (lit(prodMasterType).isNull || col(productMasterType) === lit(prodMasterType))
        && (lit(cardBlockType).isNull || col(CardMaster.cardBlockType) === lit(cardBlockType))
        && (lit(cardBusinessType).isNull || col(Bin.businessCardBankType) === lit(cardBusinessType))
      , lit(prodGroupName)
    )
  }

  /** Method for get Group Product with a given dfParameterFileIc
   *
   *  @param dfParameterFileIc
   *  @return a new Column with a given dfParameterFileIc
   */
  def getGroupProduct(dfParameterFileIc: DataFrame): Column = {
    var groupProductValue: Column = null
    dfParameterFileIc.collect().foreach(row => {
      if (groupProductValue == null) {
        groupProductValue = getGroupProductValue(row)
      } else {
        groupProductValue = getGroupProductValue(row).otherwise(groupProductValue)
      }
    })
    groupProductValue
  }

  /** Method for get Current Balance Avg with a given 
   *
   *  @return a new Column with a given 
   */
  def getCurrentBalanceAvg(): Column = {
    (
      coalesce(col(getAlias(BAM, BalanceAggregateMonthly.monthAvgBalLiabilityAmount)), lit(0))
        + coalesce(col(getAlias(BAM, BalanceAggregateMonthly.monthAvgBalAssetAmount)), lit(0))
        + coalesce(col(getAlias(BAM, BalanceAggregateMonthly.monthAvgOutOfBalanceAmount)), lit(0))
      )
  }

  /** Method for get Credit Line with a given 
   *
   *  @return a new Column with a given 
   */
  def getCreditLine(): Column = {
    coalesce(
      col(getAlias(LOA, ProductLoanDaily.drawnAmount)),
      col(getAlias(CAR, CardMaster.creditLimitAmount))
    )
  }

  /** Method for get Product Master Type with a given 
   *
   *  @return a new Column with a given 
   */
  def getProductMasterType(): Column = {
    coalesce(
      col(getAlias(LOA, ProductLoanDaily.loanClassType)), col(getAlias(CAR, Bin.cardType))
    )
  }

  /** Method for transform with a given dataReader
   *
   *  @param dataReader
   *  @return a new DataFrame with a given dataReader
   */
  override def transform(dataReader: DataReader): DataFrame = {
    val dfContractMaster = dataReader.get("contractMaster")
    val dfBalanceAggregateMonthly = dataReader.get("balanceAggregateMonthly")
    val dfPeopleDaily = dataReader.get("peopleDaily")
    val dfProductSubproduct = dataReader.get("productSubproduct")
    val dfCardConsolidation = dataReader.get("dfCardConsolidation")
    val dfProductLoanDaily = dataReader.get("productLoanDaily")
    val dfProductBbvaGroup =  getGroupProduct(prepareDfParameterFileIc(dataReader.get("parameterFileIc")))

    dfContractMaster.alias(CMA).
      join(dfBalanceAggregateMonthly.distinct().alias(BAM),
        trim(col(getAlias(BAM, BalanceAggregateMonthly.currentContractId))) === trim(col(getAlias(CMA, ContractMaster.currentContractId))),
        "left_outer"
      ).join(dfPeopleDaily.alias(PD),
        trim(col(getAlias(PD, PeopleDaily.customerId))) === trim(col(getAlias(CMA, ContractMaster.customerId))),
        "inner"
      ).join(dfProductSubproduct.alias(PRO),
        trim(col(getAlias(CMA,ContractMaster.commercialProductId))) === trim(col(getAlias(PRO, ProductSubproduct.commercialProductId))),
        "left_outer"
      ).join(dfCardConsolidation.alias(CAR),
        trim(col(getAlias(CAR, CardMaster.currentContractId))) === trim(col(getAlias(CMA, ContractMaster.currentContractId))),
        "left_outer"
      ).join(dfProductLoanDaily.alias(LOA),
        trim(col(getAlias(LOA, ProductLoanDaily.currentContractId))) === trim(col(getAlias(CMA, ContractMaster.currentContractId))),
        "left_outer"
      ).select(
        col(getAlias(CMA, ContractMaster.commercialProductId)),
        col(getAlias(CMA, ContractMaster.currentContractId)),
        col(getAlias(CMA, ContractMaster.customerId)),
        col(getAlias(CMA, ContractMaster.contractCancelDate)),
        col(getAlias(BAM, BalanceAggregateMonthly.mOrdinaryMarginAmount)),
        col(getAlias(BAM, BalanceAggregateMonthly.monthAvgBalLiabilityAmount)).alias(MatrixIcOut.punDailyBalPasAmount),
        col(getAlias(BAM, BalanceAggregateMonthly.monthAvgBalAssetAmount)).alias(MatrixIcOut.punDailyBalActAmount),
        col(getAlias(BAM, BalanceAggregateMonthly.monthAvgOutOfBalanceAmount)).alias(MatrixIcOut.punDailyBalOutAmount),
        getCurrentBalanceAvg().alias(currentBalanceAvg),
        col(getAlias(PD, PeopleDaily.personalType)),
        col(getAlias(PD, PeopleDaily.personalId)),
        trim(col(getAlias(PRO, ProductSubproduct.commercialProductDesc))).alias(ProductSubproduct.commercialProductDesc),
        getCreditLine().alias(creditLine),
        getProductMasterType().alias(productMasterType),
        col(getAlias(CAR, CardMaster.cardBlockType)).alias(CardMaster.cardBlockType),
        col(getAlias(CAR, Bin.businessCardBankType)).alias(Bin.businessCardBankType)
      ).filter(col(ContractMaster.contractCancelDate) === lit(parameter.local.contractCancelDate)
      ).withColumn(productGroup, dfProductBbvaGroup)
  }
}
