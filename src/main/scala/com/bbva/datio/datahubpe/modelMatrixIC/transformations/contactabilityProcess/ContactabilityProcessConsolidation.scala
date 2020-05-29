package com.bbva.datio.datahubpe.modelMatrixIC.transformations.contactabilityProcess

import com.bbva.datio.datahubpe.modelMatrixIC.beans.MatrixIcOut
import com.bbva.datio.datahubpe.modelMatrixIC.config.{AddressContactability, Contactability, GeoLocationMaster, Parameter}
import com.bbva.datio.datahubpe.utils.processing.data.DataReader
import com.bbva.datio.datahubpe.utils.processing.flow.Transformer
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._
import org.apache.spark.sql.expressions.Window._
import com.bbva.datio.datahubpe.modelMatrixIC.utils.caseUtils.{getAlias, getAllColumsAlias}


/** Class ContactabilityProcessConsolidation for process data of model Matrix IC.
 *
 *  @constructor create a new ContactabilityProcessConsolidation with parameters: parameter
 *  @param parameter
 */
class ContactabilityProcessConsolidation(parameter: Parameter) extends Transformer[DataReader, DataFrame]{

  val AddressContactability: AddressContactability = parameter.fields.addressContactability
  val GeoLocationMaster: GeoLocationMaster = parameter.fields.geoLocationMaster
  val Contactability: Contactability = parameter.fields.contactability
  val MatrixIcOut: MatrixIcOut = new MatrixIcOut(parameter.fields.matrixIcOut)
  val RnkFilter = "rnk_filter"
  val Asterisk = "*"

  /** Method for prepare Df Geo Location Master with a given dfGeoLocationMaster
   *
   *  @param dfGeoLocationMaster
   *  @return a new DataFrame with a given dfGeoLocationMaster
   */
  def prepareDfGeoLocationMaster(dfGeoLocationMaster: DataFrame): DataFrame = {
    dfGeoLocationMaster.select(
      col(Asterisk),
      row_number().over(partitionBy(col(GeoLocationMaster.addressGeolocationId))
        .orderBy(col(GeoLocationMaster.ineiAddressGeolocationId).desc)).alias(RnkFilter))
  }

  /** Method for prepare Df Address Contactability with a given dfAddressContactability
   *
   *  @param dfAddressContactability
   *  @return a new DataFrame with a given dfAddressContactability
   */
  def prepareDfAddressContactability(dfAddressContactability: DataFrame): DataFrame = {
    dfAddressContactability.select(
      col(Asterisk),
      row_number().over(partitionBy(col(AddressContactability.personalId),col(AddressContactability.personalType))
        .orderBy(col(AddressContactability.orderLoadType).desc)).alias(RnkFilter))
      .filter(col(RnkFilter) === lit(1)).drop(RnkFilter)
  }

  /** Method for prepare Df Contactability with a given dfContactability
   *
   *  @param dfContactability
   *  @return a new DataFrame with a given dfContactability
   */
  def prepareDfContactability(dfContactability: DataFrame): DataFrame = {
    dfContactability.select(
      col(Asterisk),
      row_number().over(partitionBy(col(Contactability.personalId),col(Contactability.personalType))
        .orderBy(col(Contactability.personalType).desc)).alias(RnkFilter))
      .filter(col(RnkFilter) === lit(1)).drop(RnkFilter)
  }

  /** Method for transform with a given dataReader
   *
   *  @param dataReader
   *  @return a new DataFrame with a given dataReader
   */
  override def transform(dataReader: DataReader): DataFrame = {
    val dfAddressContactability = prepareDfAddressContactability(dataReader.get("addressContactability"))
    val dfContactability = prepareDfContactability(dataReader.get("contactability"))
    val dfGeoLocationMaster = prepareDfGeoLocationMaster(dataReader.get("geoLocationMaster"))
    val ADR = "ADR"
    val CCM = "CCM"
    val GEO = "GEO"
    dfAddressContactability.alias(ADR).
      join( dfContactability.alias(CCM),(trim(col(getAlias(ADR, AddressContactability.personalId))) === trim(col(getAlias(CCM, Contactability.personalId))))
        and (trim(col(getAlias(ADR, AddressContactability.personalType))) === trim(col(getAlias(CCM, Contactability.personalType)))),"full_outer").
      join(dfGeoLocationMaster.alias(GEO), (trim(col(getAlias(GEO, GeoLocationMaster.addressGeolocationId))) === trim(col(getAlias(ADR, AddressContactability.sunatAddressGeolocationId))))
        and (trim(col(getAlias(GEO, RnkFilter))) === lit(1)),"left_outer"


      ).select(coalesce(col(getAlias(ADR, AddressContactability.personalId)),col(getAlias(CCM, Contactability.personalId))).alias(MatrixIcOut.personalId)
        ,coalesce(col(getAlias(ADR, AddressContactability.personalType)),col(getAlias(CCM, Contactability.personalType))).alias(MatrixIcOut.personalType)
        ,col(getAlias(CCM, Contactability.cellphoneNumberId)).alias(MatrixIcOut.phone1Id)
        ,col(getAlias(CCM, Contactability.cellphoneNumber1Id)).alias(MatrixIcOut.phone2Id)
        ,col(getAlias(CCM, Contactability.cellphoneNumber2Id)).alias(MatrixIcOut.phone3Id)
        ,col(getAlias(CCM, Contactability.cellphoneNumber3Id)).alias(MatrixIcOut.phone4Id)
        ,col(getAlias(CCM, Contactability.cellphoneNumber4Id)).alias(MatrixIcOut.phone5Id)
        ,col(getAlias(CCM, Contactability.cellphoneNumber5Id)).alias(MatrixIcOut.phone6Id)
        ,col(getAlias(CCM, Contactability.landlineNumberId)).alias(MatrixIcOut.h1PhoneNumber)
        ,col(getAlias(CCM, Contactability.landlineNumber1Id)).alias(MatrixIcOut.h2PhoneNumber)
        ,col(getAlias(CCM, Contactability.landlineNumber2Id)).alias(MatrixIcOut.h3PhoneNumber)
        ,col(getAlias(CCM, Contactability.landlineNumber3Id)).alias(MatrixIcOut.h4PhoneNumber)
        ,col(getAlias(CCM, Contactability.landlineNumber4Id)).alias(MatrixIcOut.h5PhoneNumber)
        ,col(getAlias(CCM, Contactability.landlineNumber5Id)).alias(MatrixIcOut.h6PhoneNumber)
        ,col(getAlias(CCM, Contactability.emailDesc)).alias(MatrixIcOut.email1Desc)
        ,col(getAlias(CCM, Contactability.email1Desc)).alias(MatrixIcOut.email2Desc)
        ,col(getAlias(CCM, Contactability.email2Desc)).alias(MatrixIcOut.email3Desc)
      ).distinct
  }
}
