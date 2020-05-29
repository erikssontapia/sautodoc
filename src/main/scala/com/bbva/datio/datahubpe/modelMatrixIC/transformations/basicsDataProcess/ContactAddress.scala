package com.bbva.datio.datahubpe.modelMatrixIC.transformations.basicsDataProcess

import com.bbva.datio.datahubpe.modelMatrixIC.beans.MatrixIcOut
import org.apache.spark.sql.functions._
import com.bbva.datio.datahubpe.modelMatrixIC.config.{AddressContactability, GeoLocationMaster, Parameter}
import com.bbva.datio.datahubpe.utils.processing.data.DataReader
import com.bbva.datio.datahubpe.utils.processing.flow.Transformer
import com.bbva.datio.datahubpe.modelMatrixIC.utils.caseUtils.getAlias
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.{Column, DataFrame}

/** Class ContactAddress for process data of model Matrix IC.
 *
 *  @constructor create a new ContactAddress with parameters: parameter
 *  @param parameter
 */
class ContactAddress (parameter: Parameter) extends Transformer[DataReader, DataFrame]{

  val AddressContactability : AddressContactability = parameter.fields.addressContactability
  val GeoLocationMaster: GeoLocationMaster = parameter.fields.geoLocationMaster
  val MatrixIcOut: MatrixIcOut = new MatrixIcOut(parameter.fields.matrixIcOut)

  val CON = "CON"
  val GEO = "GEO"

  val source = "contactability"
  val sourceName = "source"
  val rnkFilter = "rnk_filter"

  /** Method for get Address with a given 
   *
   *  @return a new Column with a given 
   */
  def getAddress(): Column ={
    concat(
      when(
        col(getAlias(CON, AddressContactability.additionalAddressDesc)) === lit(parameter.local.addressVoid),
        when(col(getAlias(CON, AddressContactability.additionalAddressDesc)).isNull,"").otherwise("")
      ).otherwise(col(getAlias(CON, AddressContactability.additionalAddressDesc)))
    )
  }

  /** Method for get Urbanization with a given 
   *
   *  @return a new Column with a given 
   */
  def getUrbanization(): Column = {
    when(
      col(getAlias(CON, AddressContactability.urbanizationName)).isNull,""
    ).otherwise(col(getAlias(CON, AddressContactability.urbanizationName)))
  }

  /** Method for get Reference with a given 
   *
   *  @return a new Column with a given 
   */
  def getReference(): Column = {
    when(
      col(getAlias(CON, AddressContactability.additionalAddressDataDesc)).isNull,""
    ).otherwise(col(getAlias(CON, AddressContactability.additionalAddressDataDesc)))
  }

  /** Method for transform with a given dataReader
   *
   *  @param dataReader
   *  @return a new DataFrame with a given dataReader
   */
  override def transform(dataReader: DataReader): DataFrame = {
    val dfAddressContactability = dataReader.get("addressContactability")
    val dfGeoLocationMaster = dataReader.get("dfGeoLocationMaster")

    dfAddressContactability.alias(CON).join(
        dfGeoLocationMaster.alias(GEO),
        col(getAlias(GEO, AddressContactability.sunatAddressGeolocationId)) === col(getAlias(CON, AddressContactability.sunatAddressGeolocationId))
        ,"leftOuter"
      ).select(
        col(getAlias(CON, AddressContactability.personalId)),
        col(getAlias(CON, AddressContactability.personalType)),
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
            col(getAlias(CON, AddressContactability.personalId)),
            col(getAlias(CON, AddressContactability.personalType))
          ).orderBy(
            col(getAlias(CON, AddressContactability.personalId)),
            col(getAlias(CON, AddressContactability.personalType))
          )
        ).alias(rnkFilter)
    ).filter(col(rnkFilter)===lit(1)).drop(col(rnkFilter))
  }
}

