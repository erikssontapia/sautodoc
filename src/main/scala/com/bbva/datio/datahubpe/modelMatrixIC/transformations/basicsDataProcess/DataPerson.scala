package com.bbva.datio.datahubpe.modelMatrixIC.transformations.basicsDataProcess

import com.bbva.datio.datahubpe.modelMatrixIC.beans.MatrixIcOut
import org.apache.spark.sql.functions._
import com.bbva.datio.datahubpe.modelMatrixIC.config._
import com.bbva.datio.datahubpe.utils.processing.data.DataReader
import com.bbva.datio.datahubpe.utils.processing.flow.Transformer
import org.apache.spark.sql.{Column, DataFrame}
import com.bbva.datio.datahubpe.modelMatrixIC.utils.caseUtils.getAlias
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions.{col, lit, row_number, substring}

/** Class DataPerson for process data of model Matrix IC.
 *
 *  @constructor create a new DataPerson with parameters: parameter
 *  @param parameter
 */
class DataPerson(parameter: Parameter) extends Transformer[DataReader, DataFrame]{

  var PeopleBoardAggregate: PeopleBoardAggregate = parameter.fields.peopleBoardAggregate
  var PayrollBdphMov: PayrollBdphMov = parameter.fields.payrollBdphMov
  var SegmentGroup: SegmentGroup = parameter.fields.segmentGroup
  val MatrixIcOut: MatrixIcOut = new MatrixIcOut(parameter.fields.matrixIcOut)

  val PBA = "PBA"
  val PBM = "PBM"
  val SEG = "SEG"

  val identVal = "id"

  /** Method for prepare Df Segment Group with a given dfSegmentGroup
   *
   *  @param dfSegmentGroup
   *  @return a new DataFrame with a given dfSegmentGroup
   */
  def prepareDfSegmentGroup(dfSegmentGroup: DataFrame): DataFrame =  {
    dfSegmentGroup.select(
      col(SegmentGroup.segmentId),
      col(SegmentGroup.groupSegmentName),
      col(SegmentGroup.corpCustSegmentGroupName),
      row_number().over(
        Window.partitionBy(
          col(SegmentGroup.segmentId)).orderBy(substring(col(SegmentGroup.versionId),1,4).desc
        )
      ).alias(identVal)
    ).filter(col(identVal) === lit("1")).drop(identVal)
  }

  /** Method for get Employer Taxpayer Duns Id with a given 
   *
   *  @return a new Column with a given 
   */
  def getEmployerTaxpayerDunsId(): Column ={
    when(
      col(getAlias(PBM, PeopleBoardAggregate.employerTaxpayerDunsId)).isNull,
      when(
        col(getAlias(PBA, PeopleBoardAggregate.employerTaxpayerDunsId)).isNull,""
      ).otherwise(
        col(getAlias(PBA, PeopleBoardAggregate.employerTaxpayerDunsId))
      )
    ).otherwise(col(getAlias(PBM, PeopleBoardAggregate.employerTaxpayerDunsId)))
  }

  /** Method for transform with a given dataReader
   *
   *  @param dataReader
   *  @return a new DataFrame with a given dataReader
   */
  override def transform(dataReader: DataReader): DataFrame = {
    val dfSegmentGroup =  prepareDfSegmentGroup(dataReader.get("segmentGroup"))
    val dfPeopleBoardAggregate = dataReader.get("peopleBoardAggregate")
    val dfPayrollBdphMov = dataReader.get("dfPayrollBdphMovJoinSelf")

    dfPeopleBoardAggregate.alias(PBA)
      .join(
        dfPayrollBdphMov.alias(PBM),
        col(getAlias(PBM, PayrollBdphMov.customerId)) === col(getAlias(PBA, PeopleBoardAggregate.customerId)),
        "left_outer"
      ).join(
        dfSegmentGroup.alias(SEG),
        col(getAlias(PBA, PeopleBoardAggregate.segmentId)) === col(getAlias(SEG, SegmentGroup.segmentId)),
        "left_outer"
      ).select(
        col(getAlias(PBA, PeopleBoardAggregate.personalType)).alias(MatrixIcOut.personalType),
        col(getAlias(PBA, PeopleBoardAggregate.personalId)).alias(MatrixIcOut.personalId),
        col(getAlias(PBA, PeopleBoardAggregate.customerStatusType)).alias(MatrixIcOut.customerStatusType),
        col(getAlias(PBA, PeopleBoardAggregate.customerActivityType)).alias(MatrixIcOut.nonCustomerType),
        col(getAlias(PBA, PeopleBoardAggregate.customerId)).alias(MatrixIcOut.customerId),
        col(getAlias(PBA, PeopleBoardAggregate.segmentId)).alias(MatrixIcOut.segmentId),
        col(getAlias(SEG, SegmentGroup.corpCustSegmentGroupName)).alias(MatrixIcOut.corpCustSegmentGroupName),
        col(getAlias(SEG, SegmentGroup.groupSegmentName)).alias(MatrixIcOut.groupSegmentName),
        col(getAlias(PBA, PeopleBoardAggregate.firstName)).alias(MatrixIcOut.firstName),
        col(getAlias(PBA, PeopleBoardAggregate.lastName)).alias(MatrixIcOut.lastName),
        col(getAlias(PBA, PeopleBoardAggregate.secondLastName)).alias(MatrixIcOut.secondLastName),
        col(getAlias(PBA, PeopleBoardAggregate.birthDate)).alias(MatrixIcOut.birthDate),
        col(getAlias(PBA, PeopleBoardAggregate.ageNumber)).alias(MatrixIcOut.ageNumber),
        col(getAlias(PBA, PeopleBoardAggregate.genderType)).alias(MatrixIcOut.genderType),
        col(getAlias(PBA, PeopleBoardAggregate.incomeMonthlyAmount)).alias(MatrixIcOut.incomeAmount),
        col(getAlias(PBA, PeopleBoardAggregate.incomeMonthlySourceDesc)).alias(MatrixIcOut.incomeSource),
        col(getAlias(PBA, PeopleBoardAggregate.defaultSituationComType)).alias(MatrixIcOut.defaultSituationComType),//Nuevo
        col(getAlias(PBA, PeopleBoardAggregate.unwantedType)).alias(MatrixIcOut.unwantedType),//Nuevo
        col(getAlias(PBA, PeopleBoardAggregate.persDataProtectType)).alias(MatrixIcOut.persDataProtectType),//Nuevo
        when(col(getAlias(PBA, PeopleBoardAggregate.maritalStatusType)) === lit("F"),1).otherwise(0).alias(MatrixIcOut.deceasedType),//Nuevo
        col(getAlias(PBA, PeopleBoardAggregate.robinsonType)).alias(MatrixIcOut.noContactType),//Nuevo
        col(getAlias(PBA, PeopleBoardAggregate.custBranchId)).alias(MatrixIcOut.branchId),
        col(getAlias(PBA, PeopleBoardAggregate.portfolioAllocationType)).alias(MatrixIcOut.allocationCustomerType),
        col(getAlias(PBA, PeopleBoardAggregate.mainManagerId)).alias(MatrixIcOut.localManagerId),
        col(getAlias(PBA, PeopleBoardAggregate.commercialFailedCampaignType)).alias(MatrixIcOut.campaignContacType),
        col(getAlias(PBA, PeopleBoardAggregate.sbsCustomerId)).alias(MatrixIcOut.sbsCustomerId),
        col(getAlias(PBA, PeopleBoardAggregate.personalType)).alias(MatrixIcOut.secondPersonalType),
        col(getAlias(PBA, PeopleBoardAggregate.addressGeolocationId)),                             // Ubigeo Persona
        col(getAlias(PBM, PayrollBdphMov.currentContractId)).alias(MatrixIcOut.currentContractId),                         // Contrato ph
        col(getAlias(PBM, PayrollBdphMov.employerCustomerId)).alias(MatrixIcOut.employerCustomerId),     // Codigo de Emisora
        getEmployerTaxpayerDunsId().alias(MatrixIcOut.employerTaxpayerDunsId)    // RUC Emisora
      )
  }
}
