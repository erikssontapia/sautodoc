package com.bbva.datio.datahubpe.modelMatrixIC.transformations.basicsDataProcess

import com.bbva.datio.datahubpe.modelMatrixIC.beans.MatrixIcOut
import org.apache.spark.sql.functions._
import com.bbva.datio.datahubpe.modelMatrixIC.utils.caseUtils.getAlias
import com.bbva.datio.datahubpe.modelMatrixIC.config._
import com.bbva.datio.datahubpe.utils.processing.data.DataReader
import com.bbva.datio.datahubpe.utils.processing.flow.Transformer
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.{Column, DataFrame}

/** Class AddressConsolidation for process data of model Matrix IC.
 *
 *  @constructor create a new AddressConsolidation with parameters: parameter
 *  @param parameter
 */
class AddressConsolidation(parameter: Parameter
                          ) extends Transformer[DataReader, DataFrame]{

  val MatrixIcOut: MatrixIcOut = new MatrixIcOut(parameter.fields.matrixIcOut)
  var SunatPeople: SunatPeople = parameter.fields.sunatPeople
  val AddressContactability : AddressContactability = parameter.fields.addressContactability
  val Reniec: Reniec = parameter.fields.reniec
  val Address: Address = parameter.fields.address

  val nuRnk = "nu_rnk"
  val sourceName = "source"

  val DP = "DP"
  val SUN = "SUN"
  val ADDR = "ADDR"
  val CON = "CON"
  val REN = "REN"

  /** Method for get Validate Location with a given columnName
   *
   *  @param columnName
   *  @return a new Column with a given columnName
   */
  def getValidateLocation(columnName: String): Column = {
    when(col(getAlias(ADDR, columnName)).isNull,
      when(col(getAlias(SUN, columnName)).isNull,
        when(col(getAlias(CON, columnName)).isNull,""
        ).otherwise(col(getAlias(CON, columnName)))
      ).otherwise(col(getAlias(SUN, columnName)))
    ).otherwise(col(getAlias(ADDR, columnName)))
  }

  /** Method for get Validate Political Location with a given columnName
   *
   *  @param columnName
   *  @return a new Column with a given columnName
   */
  def getValidatePoliticalLocation(columnName: String): Column = {
    when(col(getAlias(ADDR, columnName)).isNull,
      when(col(getAlias(REN, columnName)).isNull,
        when(col(getAlias(SUN, columnName)).isNull,
          when(col(getAlias(CON, columnName)).isNull,""
          ).otherwise(col(getAlias(CON, columnName)))
        ).otherwise(col(getAlias(SUN, columnName)))
      ).otherwise(col(getAlias(REN, columnName)))
    ).otherwise(col(getAlias(ADDR, columnName)))
  }

  /** Method for get Ubigeo with a given 
   *
   *  @return a new Column with a given 
   */
  def getUbigeo(): Column = {
    when(col(getAlias(ADDR, MatrixIcOut.geolocSunatId)).isNull,
      when(col(getAlias(REN, MatrixIcOut.geolocSunatId)).isNull,
        when(col(getAlias(SUN, MatrixIcOut.geolocSunatId)).isNull,
          when(col(getAlias(CON, MatrixIcOut.geolocSunatId)).isNull,""
          ).otherwise(col(getAlias(CON, MatrixIcOut.geolocSunatId)))
        ).otherwise(col(getAlias(SUN, MatrixIcOut.geolocSunatId)))
      ).otherwise(col(getAlias(REN, MatrixIcOut.geolocSunatId)))
    ).otherwise(col(getAlias(ADDR, MatrixIcOut.geolocSunatId)))
  }

  /** Method for get Address with a given 
   *
   *  @return a new Column with a given 
   */
  def getAddress(): Column = {
    getValidateLocation(MatrixIcOut.issuingEntityAddressDesc)
  }

  /** Method for get Urbanization with a given 
   *
   *  @return a new Column with a given 
   */
  def getUrbanization(): Column = {
    getValidateLocation(MatrixIcOut.urbanizationName)
  }

  /** Method for get Reference with a given 
   *
   *  @return a new Column with a given 
   */
  def getReference(): Column = {
    getValidateLocation(MatrixIcOut.additionalAddressDataDesc)
  }

  /** Method for get State with a given 
   *
   *  @return a new Column with a given 
   */
  def getState(): Column = {
    getValidatePoliticalLocation(MatrixIcOut.stateName)
  }

  /** Method for get Province with a given 
   *
   *  @return a new Column with a given 
   */
  def getProvince(): Column = {
    getValidatePoliticalLocation(MatrixIcOut.provinceName)
  }

  /** Method for get District with a given 
   *
   *  @return a new Column with a given 
   */
  def getDistrict(): Column = {
    getValidatePoliticalLocation(MatrixIcOut.districtName)
  }

  /** Method for get Source with a given 
   *
   *  @return a new Column with a given 
   */
  def getSource(): Column = {
    concat(
      when(col(getAlias(ADDR, sourceName)).isNull,"").otherwise(col(getAlias(ADDR, sourceName))),
      lit(parameter.local.sourceSeparator),
      when(col(getAlias(REN, sourceName)).isNull,"").otherwise(col(getAlias(REN, sourceName))),
      lit(parameter.local.sourceSeparator),
      when(col(getAlias(SUN, sourceName)).isNull,"").otherwise(col(getAlias(SUN, sourceName))),
      lit(parameter.local.sourceSeparator),
      when(col(getAlias(CON, sourceName)).isNull,"").otherwise(col(getAlias(CON, sourceName)))
    )
  }

  /** Method for get Nu Rnk with a given 
   *
   *  @return a new Column with a given 
   */
  def getNuRnk(): Column = {
    row_number().over(
      Window.partitionBy(
        col(getAlias(DP, MatrixIcOut.personalId)),
        col(getAlias(DP, MatrixIcOut.personalType))
      ).orderBy(
        col(getAlias(DP, MatrixIcOut.personalId)),
        col(getAlias(DP, MatrixIcOut.personalType))
      )
    )
  }

  /** Method for transform with a given dataReader
   *
   *  @param dataReader
   *  @return a new DataFrame with a given dataReader
   */
  override def transform(dataReader: DataReader): DataFrame = {
    val dfDataPerson = dataReader.get("dfDataPerson")
    val dfCustomerAddress = dataReader.get("dfCustomerAddress")
    val dfSunatAddress = dataReader.get("dfSunatAddress")
    val dfContactAddress = dataReader.get("dfContactAddress")
    val dfReniecAddress = dataReader.get("dfReniecAddress")

    dfDataPerson.alias(DP).join(
      dfCustomerAddress.alias(ADDR),
      col(getAlias(DP, MatrixIcOut.customerId)) === col(getAlias(ADDR, Address.customerId)),
      "leftOuter"
    ).join(
      dfSunatAddress.alias(SUN),
      (col(getAlias(DP, MatrixIcOut.personalId)) === col(getAlias(SUN, SunatPeople.personalId)))
        and (col(getAlias(DP, MatrixIcOut.personalType)) === col(getAlias(SUN, SunatPeople.personalType))),
      "leftOuter"
    ).join(
      dfContactAddress.alias(CON),
      (col(getAlias(DP, MatrixIcOut.personalId)) === col(getAlias(CON, AddressContactability.personalId)))
        and (col(getAlias(DP, MatrixIcOut.personalType)) === col(getAlias(CON, AddressContactability.personalType))),
      "leftOuter"
    ).join(
      dfReniecAddress.alias(REN),
      (col(getAlias(DP, MatrixIcOut.personalId)) === col(getAlias(REN, Reniec.personalId)))
        and (col(getAlias(DP, MatrixIcOut.personalType)) === col(getAlias(REN, Reniec.personalType))),
      "leftOuter"
    ).select(
      col(getAlias(DP, MatrixIcOut.personalId)),
      col(getAlias(DP, MatrixIcOut.personalType)),
      //col(getAlias(DP, matrixIcOut.customerId)),
      //Ubigeo
      getUbigeo().alias("codigo7"),
      // Direccion
      getAddress().alias(MatrixIcOut.additionalAddressDesc),
      // Urbanizacion
      getUrbanization().alias(MatrixIcOut.urbanizationName),
      // Referencia
      getReference().alias(MatrixIcOut.additionalAddressDataDesc),
      // Departamento
      getState().alias(MatrixIcOut.stateName),
      // Provincia
      getProvince().alias(MatrixIcOut.provinceName),
      // District
      getDistrict().alias(MatrixIcOut.districtName),
      // Fuente
      //getSource().alias(sourceName),
      // nuRnk
      getNuRnk().alias(nuRnk)
    ).filter(col(nuRnk) === lit(1)).drop(nuRnk)
  }
}
