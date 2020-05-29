package com.bbva.datio.datahubpe.modelMatrixIC.transformations

import com.bbva.datio.datahubpe.modelMatrixIC.beans.MatrixIcOut
import com.bbva.datio.datahubpe.modelMatrixIC.config.{Parameter, ParameterFileIc}
import com.bbva.datio.datahubpe.modelMatrixIC.utils.caseUtils.nullToEmptyString
import com.bbva.datio.datahubpe.utils.processing.data.DataReader
import com.bbva.datio.datahubpe.utils.processing.flow.Transformer
import org.apache.spark.sql.{Column, DataFrame, Row}
import org.apache.spark.sql.functions.{col, current_timestamp, lit, when}

import scala.collection.JavaConverters._

/** Class MatrixCampaignConsolidation for process data of model Matrix IC.
 *
 *  @constructor create a new MatrixCampaignConsolidation with parameters: parameter
 *  @param parameter
 */
class MatrixCampaignConsolidation(parameter: Parameter) extends Transformer[DataReader, DataFrame]{

  val ParameterFileIc: ParameterFileIc = parameter.fields.parameterFileIc
  val MatrixIcOut: MatrixIcOut = new MatrixIcOut(parameter.fields.matrixIcOut)


  /** Method for prepare Df Parameter File Ic with a given dfParameterFileIc
   *
   *  @param dfParameterFileIc
   *  @return a new ataFrame with a given dfParameterFileIc
   */
  def prepareDfParameterFileIc(dfParameterFileIc: DataFrame):DataFrame = {
    dfParameterFileIc.filter(
      col(ParameterFileIc.executionProcessId) === lit(parameter.local.customerValueGroupProcessCode)
    ).select(
      col(ParameterFileIc.parameterId),
      col(ParameterFileIc.parameterValueDesc),
      col(ParameterFileIc.parameterValue1Desc),
      col(ParameterFileIc.parameterValue3Desc),
      col(ParameterFileIc.parameterValue5Desc),
      col(ParameterFileIc.parameterValue7Desc),
      col(ParameterFileIc.parameterValue9Desc),
      col(ParameterFileIc.parameterValue11Desc)
    )
  }

  /** Method for get Customer Type Value with a given row
   *
   *  @param row
   *  @return a new Column with a given row
   */
  def getCustomerTypeValue(row: Row): Column = {
    val double: String = "double"
    val customerValueID= row.getString(0)
    val activePayrollEQ= row.getString(2)
    val marginGE= row.getString(3)
    val marginLT= row.getString(4)
    val segmentNameLK= nullToEmptyString(row.getString(7))

    when(
      (lit(activePayrollEQ).isNull || col(MatrixIcOut.netholdPayType) === lit(activePayrollEQ))
        && (lit(marginGE).isNull || col(MatrixIcOut.marMonthlyAmount) >= lit(marginGE).cast(double))
        && (lit(marginLT).isNull || col(MatrixIcOut.marMonthlyAmount) < lit(marginLT).cast(double))
        && (lit(segmentNameLK) === lit("") || col(MatrixIcOut.groupSegmentName).like(segmentNameLK))
      ,lit(customerValueID)
    )
  }

  /** Method for get Customer Type with a given dfParameterFileIc
   *
   *  @param dfParameterFileIc
   *  @return a new olumn with a given dfParameterFileIc
   */
  def getCustomerType(dfParameterFileIc: DataFrame):Column = {
    var customerType:Column = lit("")
    dfParameterFileIc.collect().foreach(row => {
      if(customerType == null){
        customerType = getCustomerTypeValue(row)
      }else{
        customerType = getCustomerTypeValue(row).otherwise(customerType)
      }
    })
    customerType
  }

  /** Method for prepare Df Matrix Campaign with a given dfBasicDataProcessConsolidation and dfProductProcessConsolidation and dfContactabilityProcessConsolidation and dfRccProcessConsolidation and dfParameterFileIc
   *
   *  @param dfBasicDataProcessConsolidation
   *  @param dfProductProcessConsolidation
   *  @param dfContactabilityProcessConsolidation
   *  @param dfRccProcessConsolidation
   *  @param dfParameterFileIc
   *  @return a new DataFrame with a given dfBasicDataProcessConsolidation and dfProductProcessConsolidation and dfContactabilityProcessConsolidation and dfRccProcessConsolidation and dfParameterFileIc
   */
  def prepareDfMatrixCampaign(dfBasicDataProcessConsolidation: DataFrame,
                              dfProductProcessConsolidation: DataFrame,
                              dfContactabilityProcessConsolidation: DataFrame,
                              dfRccProcessConsolidation: DataFrame,
                              dfParameterFileIc: DataFrame
                             ): DataFrame = {
    val BDP = "BDP"
    val PP = "PP"
    val CP = "CP"
    val RP = "RP"

    dfBasicDataProcessConsolidation.alias(BDP).join(
      dfProductProcessConsolidation.alias(PP),
      Seq(MatrixIcOut.personalId, MatrixIcOut.personalType),
      "left_outer"
    ).join(
      dfContactabilityProcessConsolidation.alias(CP),
      Seq(MatrixIcOut.personalId, MatrixIcOut.personalType),
      "left_outer"
    ).join(
      dfRccProcessConsolidation.alias(RP),
      Seq(MatrixIcOut.personalId, MatrixIcOut.personalType),
      "left_outer"
    ).withColumn(
      MatrixIcOut.netholdCccType,
      when(
        col(MatrixIcOut.ownerCreditCardType2).isNull,
        lit(parameter.local.matrixCampaignDefaultValue)
      ).otherwise(col(MatrixIcOut.ownerCreditCardType2))
    ).withColumn(MatrixIcOut.customerValueType, getCustomerType(dfParameterFileIc))
      .withColumn(MatrixIcOut.cutoffDate, lit(parameter.processDate))
      .withColumn(MatrixIcOut.audtiminsertDate, current_timestamp())
      .withColumn(MatrixIcOut.netholdPlType,
        when(col(MatrixIcOut.netholdPlType).isNull, lit(parameter.local.matrixCampaignDefaultValue)).otherwise(col(MatrixIcOut.netholdPlType))
      ).withColumn(
        MatrixIcOut.netholdVehType,
        when(col(MatrixIcOut.netholdVehType).isNull, lit(parameter.local.matrixCampaignDefaultValue)).otherwise(col(MatrixIcOut.netholdVehType))
      ).withColumn(
        MatrixIcOut.netholdMfsType,
        when(col(MatrixIcOut.netholdMfsType).isNull, lit(parameter.local.matrixCampaignDefaultValue)).otherwise(col(MatrixIcOut.netholdMfsType))
      )
  }

  /** Method for transform with a given dataReader
   *
   *  @param dataReader
   *  @return a new DataFrame with a given dataReader
   */
  override def transform(dataReader: DataReader): DataFrame = {
    val dfBasicDataProcessConsolidation = dataReader.get("dfBasicDataProcessConsolidation")
    val dfProductProcessConsolidation = dataReader.get("dfProductProcessConsolidation")
    val dfContactabilityProcessConsolidation = dataReader.get("dfContactabilityProcessConsolidation")
    val dfRccProcessConsolidation = dataReader.get("dfRccProcessConsolidation")
    val dfParameterFileIc = prepareDfParameterFileIc(dataReader.get("parameterFileIc"))
    val cols = parameter.local.matrixDefaultStructure.asScala

    val dfMatrixCampaign = prepareDfMatrixCampaign(
      dfBasicDataProcessConsolidation,
      dfProductProcessConsolidation,
      dfContactabilityProcessConsolidation,
      dfRccProcessConsolidation,
      dfParameterFileIc
    )

    dfMatrixCampaign.select(cols.head, cols.tail:_*)
  }
}
