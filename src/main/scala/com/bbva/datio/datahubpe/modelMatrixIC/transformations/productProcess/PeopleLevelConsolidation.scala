package com.bbva.datio.datahubpe.modelMatrixIC.transformations.productProcess

import com.bbva.datio.datahubpe.modelMatrixIC.beans.MatrixIcOut
import com.bbva.datio.datahubpe.modelMatrixIC.config.{Parameter, PeopleBoardAggregate, PeopleDaily}
import com.bbva.datio.datahubpe.modelMatrixIC.utils.caseUtils.{getAlias, getAllColumsAlias}
import com.bbva.datio.datahubpe.utils.processing.data.DataReader
import com.bbva.datio.datahubpe.utils.processing.flow.Transformer
import org.apache.spark.sql.functions._
import org.apache.spark.sql.DataFrame

/** Class PeopleLevelConsolidation for process data of model Matrix IC.
 *
 *  @constructor create a new PeopleLevelConsolidation with parameters: parameter
 *  @param parameter
 */
class PeopleLevelConsolidation(parameter: Parameter) extends Transformer[DataReader, DataFrame] {

  val PeopleDaily: PeopleDaily = parameter.fields.peopleDaily
  val MatrixIcOut: MatrixIcOut = new MatrixIcOut(parameter.fields.matrixIcOut)
  val PeopleBoardAggregate: PeopleBoardAggregate = parameter.fields.peopleBoardAggregate

  // Alias
  val productGroup = "product_group"

  val consumerCc: String = parameter.local.productGroupTypes.get(0)
  val personalLoan: String = parameter.local.productGroupTypes.get(1)
  val renewdPerloan: String = parameter.local.productGroupTypes.get(2)
  val mortgage: String = parameter.local.productGroupTypes.get(3)
  val vehicularLoan: String = parameter.local.productGroupTypes.get(4)
  val cts: String = parameter.local.productGroupTypes.get(5)
  val mutualFunds: String = parameter.local.productGroupTypes.get(6)
  val fixedDeposit: String = parameter.local.productGroupTypes.get(7)
  val saving: String = parameter.local.productGroupTypes.get(8)
  val insuranceOncology: String = parameter.local.productGroupTypes.get(9)
  val insuranceMultiple: String = parameter.local.productGroupTypes.get(10)
  val insuranceCardprotection: String = parameter.local.productGroupTypes.get(11)
  val insuranceVehicular: String = parameter.local.productGroupTypes.get(12)
  val businessCc: String = parameter.local.productGroupTypes.get(13)

  val YES: String = parameter.local.productGroupValues.get(0)
  val NO: String = parameter.local.productGroupValues.get(1)

  val PBC = "PBC"
  val PBA = "PBA"

  /** Method for prepare Df Product Bbva Contract Consolidation with a given dfProductBbvaContractConsolidation
   *
   *  @param dfProductBbvaContractConsolidation
   *  @return a new DataFrame with a given dfProductBbvaContractConsolidation
   */
  def prepareDfProductBbvaContractConsolidation(dfProductBbvaContractConsolidation: DataFrame): DataFrame = {
    dfProductBbvaContractConsolidation.groupBy(
      col(PeopleDaily.personalType).alias(PeopleDaily.personalType),
      col(PeopleDaily.personalId).alias(PeopleDaily.personalId)
    ).agg(
      max(when(col(productGroup) === lit(consumerCc), lit(YES)).otherwise(NO)).alias(MatrixIcOut.ownerCreditCardType2),
      max(when(col(productGroup) === lit(personalLoan), lit(YES)).otherwise(NO)).alias(MatrixIcOut.netholdPlType),
      max(when(col(productGroup) === lit(renewdPerloan), lit(YES)).otherwise(NO)).alias(MatrixIcOut.netholdRplType),
      max(when(col(productGroup) === lit(mortgage), lit(YES)).otherwise(NO)).alias(MatrixIcOut.netholdMorType),
      max(when(col(productGroup) === lit(vehicularLoan), lit(YES)).otherwise(NO)).alias(MatrixIcOut.netholdVehType),
      max(when(col(productGroup) === lit(cts), lit(YES)).otherwise(NO)).alias(MatrixIcOut.netholdCtsType),
      max(when(col(productGroup) === lit(mutualFunds), lit(YES)).otherwise(NO)).alias(MatrixIcOut.netholdMfsType),
      max(when(col(productGroup) === lit(fixedDeposit), lit(YES)).otherwise(NO)).alias(MatrixIcOut.netholdFtdType),
      max(when(col(productGroup) === lit(saving), lit(YES)).otherwise(NO)).alias(MatrixIcOut.netholdSavType),
      max(when(col(productGroup) === lit(insuranceOncology), lit(YES)).otherwise(NO)).alias(MatrixIcOut.netholdIonType),
      max(when(col(productGroup) === lit(insuranceMultiple), lit(YES)).otherwise(NO)).alias(MatrixIcOut.netholdImuType),
      max(when(col(productGroup) === lit(insuranceCardprotection), lit(YES)).otherwise(NO)).alias(MatrixIcOut.netholdIcpType),
      max(when(col(productGroup) === lit(insuranceVehicular), lit(YES)).otherwise(NO)).alias(MatrixIcOut.netholdIvhType),
      max(when(col(productGroup) === lit(businessCc), lit(YES)).otherwise(NO)).alias(MatrixIcOut.netholdBccType),
      sum(col(MatrixIcOut.punDailyBalPasAmount)).cast("decimal(17, 2)").alias(MatrixIcOut.punDailyBalPasAmount),
      sum(col(MatrixIcOut.punDailyBalActAmount)).cast("decimal(17, 2)").alias(MatrixIcOut.punDailyBalActAmount),
      sum(col(MatrixIcOut.punDailyBalOutAmount)).alias(MatrixIcOut.punDailyBalOutAmount) //nueva columna
    )
  }

  /** Method for transform with a given dataReader
   *
   *  @param dataReader
   *  @return a new DataFrame with a given dataReader
   */
  override def transform(dataReader: DataReader): DataFrame = {
    val dfProductBbvaContractConsolidation = prepareDfProductBbvaContractConsolidation(
      dataReader.get("dfProductBbvaContractConsolidation")
    )

    val dfPeopleBoardAggregate = dataReader.get("peopleBoardAggregate")

    dfProductBbvaContractConsolidation.alias(PBC).join(
      dfPeopleBoardAggregate.alias(PBA),
      trim(col(getAlias(PBC, MatrixIcOut.personalType))) === trim(col(getAlias(PBA, PeopleBoardAggregate.personalType)))
      and trim(col(getAlias(PBC, MatrixIcOut.personalId))) === trim(col(getAlias(PBA, PeopleBoardAggregate.personalId))),
      "inner"
    ).select(
      col(getAllColumsAlias(PBC)),
      col(getAlias(PBA, PeopleBoardAggregate.receivablePaymentType)).alias(MatrixIcOut.netholdPayType)
    )
  }
}
