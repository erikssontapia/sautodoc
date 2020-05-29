package com.bbva.datio.datahubpe.modelMatrixIC.transformations.basicsDataProcess

import com.bbva.datio.datahubpe.modelMatrixIC.beans.MatrixIcOut
import org.apache.spark.sql.functions._
import com.bbva.datio.datahubpe.modelMatrixIC.config.{GeoLocationMaster, Parameter, SunatPeople}
import com.bbva.datio.datahubpe.utils.processing.data.DataReader
import com.bbva.datio.datahubpe.utils.processing.flow.Transformer
import com.bbva.datio.datahubpe.modelMatrixIC.utils.caseUtils.getAlias
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.{Column, DataFrame}

/** Class SunatAddress for process data of model Matrix IC.
 *
 *  @constructor create a new SunatAddress with parameters: parameter
 *  @param parameter
 */
class SunatAddress (parameter: Parameter) extends Transformer[DataReader, DataFrame]{

  val SunatPeople: SunatPeople = parameter.fields.sunatPeople
  val GeoLocationMaster: GeoLocationMaster = parameter.fields.geoLocationMaster
  val MatrixIcOut: MatrixIcOut = new MatrixIcOut(parameter.fields.matrixIcOut)

  val SUN = "SUN"
  val GEO = "GEO"

  val source = "customer"
  val sourceName = "source"

  val rnkFilter = "rnk_filter"

  /** Method for get Address Part with a given columnName
   *
   *  @param columnName
   *  @return a new Column with a given columnName
   */
  def getAddressPart(columnName: String): Column = {
    when(
      col(columnName) === lit(parameter.local.addressVoid),
      when(
        col(columnName).isNull,""
      ).otherwise("")
    ).otherwise(col(columnName))
  }

  /** Method for get Address with a given 
   *
   *  @return a new Column with a given 
   */
  def getAddress(): Column = {
    concat(
      getAddressPart(getAlias(SUN, SunatPeople.addressDesc)),
      lit(parameter.local.geolocSeparator),
      getAddressPart(getAlias(SUN, SunatPeople.roadNumberId)),
      lit(parameter.local.geolocSeparator),
      getAddressPart(getAlias(SUN, SunatPeople.addressRoadNumberId))
    )
  }

  /** Method for get Urbanization with a given 
   *
   *  @return a new Column with a given 
   */
  def getUrbanization(): Column = {
    when(
      col(getAlias(SUN, SunatPeople.urbanizationName)) === lit(parameter.local.addressVoid),""
    ).otherwise(
      col(getAlias(SUN, SunatPeople.urbanizationName))
    )
  }

  /** Method for get Reference with a given 
   *
   *  @return a new Column with a given 
   */
  def getReference(): Column = {
    when(
      col(getAlias(SUN, SunatPeople.additionalAddressDataDesc))===lit(parameter.local.addressVoid),""
    ).otherwise(
      col(getAlias(SUN, SunatPeople.additionalAddressDataDesc))
    )
  }

  /** Method for transform with a given dataReader
   *
   *  @param dataReader
   *  @return a new DataFrame with a given dataReader
   */
  override def transform(dataReader: DataReader): DataFrame = {
    val dfSunatPeople = dataReader.get("sunatPeople")
    val dfGeoLocationMaster = dataReader.get("dfGeoLocationMaster")

    dfSunatPeople.alias(SUN).
      join(
        dfGeoLocationMaster.alias(GEO),
        col(getAlias(GEO, GeoLocationMaster.sunatAddressGeolocationId))===col(getAlias(SUN, SunatPeople.zipcodeId)),
        "left_outer"
      ).select(
        col(getAlias(SUN, SunatPeople.taxpayerId)),
        col(getAlias(SUN, SunatPeople.personalId)),
        col(getAlias(SUN, SunatPeople.personalType)),
        col(getAlias(GEO, GeoLocationMaster.addressGeolocationId)).alias(MatrixIcOut.geolocSunatId),
        // Direccion
        getAddress().alias(MatrixIcOut.issuingEntityAddressDesc),
        // Urbanizacion
        getUrbanization().alias(MatrixIcOut.urbanizationName),
        // Referencia
        getReference().alias(MatrixIcOut.additionalAddressDataDesc),
        col(getAlias(GEO, GeoLocationMaster.addressDistrictName)).alias(MatrixIcOut.districtName),
        col(getAlias(GEO, GeoLocationMaster.provinceName)).alias(MatrixIcOut.provinceName),
        col(getAlias(GEO, GeoLocationMaster.addressDepartmentName)).alias(MatrixIcOut.stateName),
        lit(source).alias(sourceName),
        row_number().over(
          Window.partitionBy(
            col(getAlias(SUN, SunatPeople.taxpayerId)),
            col(getAlias(SUN, SunatPeople.personalId)),
            col(getAlias(SUN, SunatPeople.personalType))
          ).orderBy(
              col(getAlias(SUN, SunatPeople.personalId)),
              col(getAlias(SUN, SunatPeople.personalType))
            )
        ).alias(rnkFilter)
      ).filter(
        col(rnkFilter)===lit(1)
      ).drop(col(rnkFilter))
  }
}

