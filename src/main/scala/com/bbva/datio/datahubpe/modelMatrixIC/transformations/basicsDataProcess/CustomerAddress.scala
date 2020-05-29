package com.bbva.datio.datahubpe.modelMatrixIC.transformations.basicsDataProcess

import com.bbva.datio.datahubpe.modelMatrixIC.beans.MatrixIcOut
import org.apache.spark.sql.functions._
import com.bbva.datio.datahubpe.modelMatrixIC.config.{Address, GeoLocationMaster, Parameter}
import com.bbva.datio.datahubpe.utils.processing.data.DataReader
import com.bbva.datio.datahubpe.utils.processing.flow.Transformer
import org.apache.spark.sql.{Column, DataFrame}
import org.apache.spark.sql.functions.col
import com.bbva.datio.datahubpe.modelMatrixIC.utils.caseUtils.{getAlias, getAllColumsAlias}
import org.apache.spark.sql.expressions.Window

/** Class CustomerAddress for process data of model Matrix IC.
 *
 *  @constructor create a new CustomerAddress with parameters: parameter
 *  @param parameter
 */
class CustomerAddress (parameter: Parameter) extends Transformer[DataReader, DataFrame]{

  val Address: Address = parameter.fields.address
  val GeoLocationMaster: GeoLocationMaster = parameter.fields.geoLocationMaster
  val MatrixIcOut: MatrixIcOut = new MatrixIcOut(parameter.fields.matrixIcOut)

  val ADR = "ADR"
  val GEO = "GEO"
  val ZERO = "0"

  val source = "customer"
  val sourceName = "source"
  val rnkFilter = "rnk_filter"

  /** Method for prepare Df Address with a given dfAddress
   *
   *  @param dfAddress
   *  @return a new DataFrame with a given dfAddress
   */
  def prepareDfAddress(dfAddress: DataFrame): DataFrame = {
    dfAddress.select(
      col(Address.customerId),
      col(Address.effectiveEndDate),
      col(Address.residenceType),
      col(Address.addressLastChangeDate),
      col(Address.addressDepartmentId),
      col(Address.provinceId),
      col(Address.addressDistrictId),
      col(Address.addressDesc),
      col(Address.roadNumberId),
      col(Address.additionalAddressDesc),
      col(Address.additionalAddressDataDesc),
      col(Address.urbanizationName),
      col(Address.roadTypeId)
    )
  }

  /** Method for get Geoloc with a given 
   *
   *  @return a new Column with a given 
   */
  def getGeoloc(): Column = {
    concat(
      lpad(col(getAlias(ADR, Address.addressDepartmentId)),2,ZERO),
      lpad(col(getAlias(ADR, Address.provinceId)),2,ZERO),
      lpad(col(getAlias(ADR, Address.addressDistrictId)),3,ZERO)
    )
  }

  /** Method for get Issuing Entity Address Desc with a given 
   *
   *  @return a new Column with a given 
   */
  def getIssuingEntityAddressDesc(): Column = {
    concat(
      when(
        col(getAlias(ADR, Address.roadTypeId)).isNull, ""
      ).otherwise(col(getAlias(ADR, Address.roadTypeId)))
      ,lit(parameter.local.geolocSeparator)
      ,when(
        col(getAlias(ADR, Address.addressDesc)).isNull, ""
      ).otherwise(col(getAlias(ADR, Address.addressDesc)))
      ,lit(parameter.local.geolocSeparator)
      ,when(
        col(getAlias(ADR, Address.roadNumberId)).isNull, ""
      ).otherwise(col(getAlias(ADR, Address.roadNumberId)))
      ,lit(parameter.local.geolocSeparator)
      ,when(
        col(getAlias(ADR, Address.additionalAddressDesc)).isNull, ""
      ).otherwise(col(getAlias(ADR, Address.additionalAddressDesc)))
    )
  }

  /** Method for transform with a given dataReader
   *
   *  @param dataReader
   *  @return a new DataFrame with a given dataReader
   */
  override def transform(dataReader: DataReader): DataFrame = {
    val dfAddress = prepareDfAddress(dataReader.get("address"))
    val dfGeoLocationMaster = dataReader.get("dfGeoLocationMaster")

    dfAddress.filter(
      col(Address.residenceType) === lit(parameter.local.residenceType)
    ).alias(ADR).join(
        dfGeoLocationMaster.alias(GEO),
        col(getAlias(GEO, GeoLocationMaster.addressGeolocationId)) === getGeoloc(),
      "leftOuter"
    ).select(
      col(getAlias(ADR, Address.customerId)),
      getGeoloc().alias(MatrixIcOut.geolocSunatId),
      getIssuingEntityAddressDesc().alias(MatrixIcOut.issuingEntityAddressDesc),
      col(Address.urbanizationName).alias(MatrixIcOut.urbanizationName),
      col(Address.additionalAddressDataDesc).alias(MatrixIcOut.additionalAddressDataDesc),
      col(getAlias(GEO, GeoLocationMaster.addressDistrictName)).alias(MatrixIcOut.districtName),
      col(getAlias(GEO, GeoLocationMaster.provinceName)).alias(MatrixIcOut.provinceName),
      col(getAlias(GEO, GeoLocationMaster.addressDepartmentName)).alias(MatrixIcOut.stateName),
      lit(source).alias(sourceName),
      row_number().over(
          Window.partitionBy(col(getAlias(ADR, Address.customerId))).
            orderBy(
              col(getAlias(ADR, Address.effectiveEndDate)).desc,
              col(getAlias(ADR, Address.addressLastChangeDate)).desc
            )
        ).alias(rnkFilter)
    ).filter(
      col(rnkFilter) === lit(1)
    ).drop(col(rnkFilter))
  }
}
