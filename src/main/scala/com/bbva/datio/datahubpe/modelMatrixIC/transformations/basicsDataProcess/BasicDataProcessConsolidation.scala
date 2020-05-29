package com.bbva.datio.datahubpe.modelMatrixIC.transformations.basicsDataProcess

import com.bbva.datio.datahubpe.modelMatrixIC.beans.{PricingInit, MatrixIcOut}
import org.apache.spark.sql.functions.lit
import com.bbva.datio.datahubpe.modelMatrixIC.config._
import com.bbva.datio.datahubpe.modelMatrixIC.utils.caseUtils.{getAlias, getAllColumsAlias}
import com.bbva.datio.datahubpe.utils.processing.data.DataReader
import com.bbva.datio.datahubpe.utils.processing.flow.Transformer
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions.{col, row_number}

/** Class BasicDataProcessConsolidation for process data of model Matrix IC.
 *
 *  @constructor create a new BasicDataProcessConsolidation with parameters: parameter
 *  @param parameter
 */
class BasicDataProcessConsolidation(parameter: Parameter) extends Transformer[DataReader, DataFrame] {

  val PeopleBoardAggregate: PeopleBoardAggregate = parameter.fields.peopleBoardAggregate
  val BranchCatalog: BranchCatalog = parameter.fields.branchCatalog
  val HierarchyBranch: HierarchyBranch = parameter.fields.hierarchyBranch
  val Employees: Employee = parameter.fields.employee
  val PayrollBdphMov: PayrollBdphMov = parameter.fields.payrollBdphMov
  val Manager: Manager = parameter.fields.manager
  val GeoLocationMaster: GeoLocationMaster = parameter.fields.geoLocationMaster
  val MatrixIcOut: MatrixIcOut = new MatrixIcOut(parameter.fields.matrixIcOut)

  // Column names
  val identVal = "id"
  val rnkFilter = "rnk_filter"
  val sourceName = "source"
  val personalId = "personal_id"
  val personalType = "personal_type"


  /** Method for prepare Df Payroll Bdph Mov Join People Board Agg with a given dfPayrollBdphMov and dfPeopleBoardAggregate
   *
   *  @param dfPayrollBdphMov
   *  @param dfPeopleBoardAggregate
   *  @return a new DataFrame with a given dfPayrollBdphMov and dfPeopleBoardAggregate
   */
  def prepareDfPayrollBdphMovJoinPeopleBoardAgg(dfPayrollBdphMov: DataFrame,
                                                dfPeopleBoardAggregate: DataFrame): DataFrame = {
    val BPH: String = "BPH"
    val HPE: String = "HPE"

    dfPayrollBdphMov.select(
      PayrollBdphMov.employerCustomerId
    ).distinct().alias(BPH).join(
      dfPeopleBoardAggregate.alias(HPE),
      col(getAlias(BPH, PayrollBdphMov.employerCustomerId)) === col(getAlias(HPE, PeopleBoardAggregate.customerId)),
      "left_outer"
    ).select(
      col(getAlias(BPH, PayrollBdphMov.employerCustomerId)),
      col(getAlias(HPE, PeopleBoardAggregate.personalId)).alias(PeopleBoardAggregate.employerTaxpayerDunsId)
    )
  }

  /** Method for prepare Df Payroll Bdph Mov Join Self with a given dfPayrollBdphMov and dfPayrollBdphMovJoinPeopleBoardAgg
   *
   *  @param dfPayrollBdphMov
   *  @param dfPayrollBdphMovJoinPeopleBoardAgg
   *  @return a new DataFrame with a given dfPayrollBdphMov and dfPayrollBdphMovJoinPeopleBoardAgg
   */
  def prepareDfPayrollBdphMovJoinSelf(dfPayrollBdphMov: DataFrame,
                                      dfPayrollBdphMovJoinPeopleBoardAgg: DataFrame): DataFrame = {
    val PBM: String = "PBM"
    val PBMJ: String = "PBMJ"

    dfPayrollBdphMov.alias(PBM).join(
      dfPayrollBdphMovJoinPeopleBoardAgg.alias(PBMJ),
      col(getAlias(PBM, PayrollBdphMov.employerCustomerId)) === col(getAlias(PBMJ, PayrollBdphMov.employerCustomerId)),
      "leftOuter"
    ).select(
      col(getAllColumsAlias(PBM)),
      col(getAlias(PBMJ, PeopleBoardAggregate.employerTaxpayerDunsId))
    )
  }

  /** Method for prepare Df Geo Location Master with a given dfGeoLocationMaster
   *
   *  @param dfGeoLocationMaster
   *  @return a new DataFrame with a given dfGeoLocationMaster
   */
  def prepareDfGeoLocationMaster(dfGeoLocationMaster: DataFrame): DataFrame = {
    dfGeoLocationMaster.select(
      col("*"),
      row_number().over(
        Window.partitionBy(
          col(GeoLocationMaster.addressGeolocationId)
        ).orderBy(col(GeoLocationMaster.ineiAddressGeolocationId).desc)
      ).alias(rnkFilter)
    )
  }

  /** Method for prepare Df Basic Data Process Consolidation with a given dfDataPerson and dfAddresConsolidation and dfProvidedAddressConsolidation and dfDataPersonConsolidation
   *
   *  @param dfDataPerson
   *  @param dfAddresConsolidation
   *  @param dfProvidedAddressConsolidation
   *  @param dfDataPersonConsolidation
   *  @return a new DataFrame with a given dfDataPerson and dfAddresConsolidation and dfProvidedAddressConsolidation and dfDataPersonConsolidation
   */
  def prepareDfBasicDataProcessConsolidation(dfDataPerson: DataFrame,
                                             dfAddresConsolidation: DataFrame,
                                             dfProvidedAddressConsolidation: DataFrame,
                                             dfDataPersonConsolidation: DataFrame
                                            ) : DataFrame = {
    val DP: String = "DP"
    val AC: String = "AC"
    val PAC: String = "PAC"
    val DPC: String = "DPC"

    dfDataPerson.alias(DP).join(
      dfDataPersonConsolidation.alias(DPC),
      Seq(MatrixIcOut.personalId, MatrixIcOut.personalType),
      "left"
    ).join(dfAddresConsolidation.alias(AC),
      Seq(MatrixIcOut.personalId, MatrixIcOut.personalType),
      "left"
    ).join(
      dfProvidedAddressConsolidation.alias(PAC),
      Seq(MatrixIcOut.employerCustomerId),
      "left"
    )
  }

  /** Method for transform with a given dataReader
   *
   *  @param dataReader
   *  @return a new DataFrame with a given dataReader
   */
  override def transform(dataReader: DataReader): DataFrame = {
    val dfPeopleBoardAggregate = dataReader.get("peopleBoardAggregate")
    val dfPayrollBdphMov = dataReader.get("payrollBdphMov")
    val dfPayrollBdphMovJoinPeopleBoardAgg = prepareDfPayrollBdphMovJoinPeopleBoardAgg(dfPayrollBdphMov, dfPeopleBoardAggregate)
    val dfGeoLocationMaster = prepareDfGeoLocationMaster(dataReader.get("geoLocationMaster"))

    val BDPC: String = "BDPC"
    val GLM: String = "GLM"

    dataReader.add("dfGeoLocationMaster", dfGeoLocationMaster)
    dataReader.add("dfPayrollBdphMovJoinSelf", prepareDfPayrollBdphMovJoinSelf(dfPayrollBdphMov, dfPayrollBdphMovJoinPeopleBoardAgg))

    // Process
    val dfCutomerAddress = new CustomerAddress(parameter).transform(dataReader)
    dataReader.add("dfCustomerAddress", dfCutomerAddress)

    val dfSunatAddress = new SunatAddress(parameter).transform(dataReader)
    dataReader.add("dfSunatAddress", new SunatAddress(parameter).transform(dataReader))

    val dfContactAddress = new ContactAddress(parameter).transform(dataReader)
    dataReader.add("dfContactAddress", new ContactAddress(parameter).transform(dataReader))

    val dfReniecAddress = new ReniecAddress(parameter).transform(dataReader)
    dataReader.add("dfReniecAddress", new ReniecAddress(parameter).transform(dataReader))

    val dfDataPerson = new DataPerson(parameter).transform(dataReader)
    dataReader.add("dfDataPerson", dfDataPerson)

    // personDF_1
    val dfAddresConsolidation = new AddressConsolidation(parameter).transform(dataReader)

    // personDF_2
    val dfProvidedAddressConsolidation = new ProvidedAddressConsolidation(parameter).transform(dataReader)

    // personDF_3
    val dfDataPersonConsolidation = new DataPersonConsolidation(parameter).transform(dataReader)

    // personDF_4
    val dfBasicDataProcessConsolidation = prepareDfBasicDataProcessConsolidation(
      dfDataPerson, dfAddresConsolidation, dfProvidedAddressConsolidation, dfDataPersonConsolidation
    )

    dfBasicDataProcessConsolidation.alias(BDPC).join(dfGeoLocationMaster.alias(GLM),
      col(getAlias(BDPC, "codigo7")) === col(getAlias(GLM, GeoLocationMaster.addressGeolocationId)),
      "leftOuter"
    ).select(
      col(getAllColumsAlias(BDPC)),
      col(getAlias(GLM, GeoLocationMaster.sunatAddressGeolocationId)).alias(MatrixIcOut.geolocSunatId)
    )
  }
}
