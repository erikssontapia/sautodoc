package com.bbva.datio.datahubpe.modelMatrixIC.transformations.productProcess

import com.bbva.datio.datahubpe.modelMatrixIC.beans.CardMaster
import com.bbva.datio.datahubpe.modelMatrixIC.config.{Bin, Parameter}
import com.bbva.datio.datahubpe.modelMatrixIC.utils.caseUtils.getAlias
import com.bbva.datio.datahubpe.utils.processing.data.DataReader
import com.bbva.datio.datahubpe.utils.processing.flow.Transformer
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.expressions.Window.partitionBy
import org.apache.spark.sql.functions._

import scala.collection.JavaConverters._

/** Class CardConsolidation for process data of model Matrix IC.
 *
 *  @constructor create a new CardConsolidation with parameters: parameter
 *  @param parameter
 */
class CardConsolidation(parameter: Parameter) extends Transformer[DataReader, DataFrame] {

  val cardMaster: CardMaster = new CardMaster(parameter.fields.cardMaster)
  val bin: Bin = parameter.fields.bin

  // Alias

  val rnkFilter = "rnk_filter"

  /** Method for transform with a given dataReader
   *
   *  @param dataReader
   *  @return a new DataFrame with a given dataReader
   */
  override def transform(dataReader: DataReader): DataFrame = {
    val dfCardMaster = dataReader.get("cardMaster")
    val dfBin = dataReader.get("bin")
    val CAR = "CAR"
    val BIN = "BIN"

    dfCardMaster.alias(CAR).join(
      dfBin.alias(BIN),
      trim(col(getAlias(CAR, cardMaster.cardBinNumber))) === trim(col(getAlias(BIN, bin.cardBinNumber))),
      "inner"
    ).filter(
        trim(col(getAlias(CAR, cardMaster.participantType))) === lit(parameter.local.participantType)
        and trim(col(getAlias(CAR, cardMaster.currentExpiryDate))) =!= lit(parameter.local.currentExpiryDate)
        and trim(col(getAlias(CAR, cardMaster.cardStatusType))).isin(parameter.local.cardStatusTypeList.asScala:_*)
        and col(getAlias(CAR, cardMaster.cardBlockType)).isin(parameter.local.cardBlockTypeList.asScala: _*)
    ).select(
      col(getAlias(CAR, cardMaster.currentContractId)),
      col(getAlias(CAR, cardMaster.creditLimitAmount)),
      when(
        col(getAlias(CAR, cardMaster.cardBlockType)) === lit(""), parameter.local.cardStatusTypeList.get(1)
      ).otherwise(
        col(getAlias(CAR, cardMaster.cardBlockType))
      ).alias(cardMaster.cardBlockType),
      col(getAlias(BIN, bin.businessCardBankType)),
      col(getAlias(BIN, bin.cardType)),
      row_number().over(
        partitionBy(col(getAlias(CAR, cardMaster.currentContractId))
        ).orderBy(
          col(getAlias(CAR, cardMaster.participationOrderNumber)).asc,
          col(getAlias(CAR, cardMaster.creditLimitAmount)).desc
        )
      ).alias(rnkFilter)).filter(col(rnkFilter) === lit(1)).drop(rnkFilter)
  }
}
