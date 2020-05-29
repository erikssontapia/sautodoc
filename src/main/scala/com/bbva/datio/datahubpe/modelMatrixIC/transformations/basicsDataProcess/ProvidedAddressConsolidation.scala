package com.bbva.datio.datahubpe.modelMatrixIC.transformations.basicsDataProcess

import com.bbva.datio.datahubpe.modelMatrixIC.beans.MatrixIcOut
import org.apache.spark.sql.functions._
import com.bbva.datio.datahubpe.modelMatrixIC.utils.caseUtils.getAlias
import com.bbva.datio.datahubpe.modelMatrixIC.config._
import com.bbva.datio.datahubpe.utils.processing.data.DataReader
import com.bbva.datio.datahubpe.utils.processing.flow.Transformer
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.{Column, DataFrame}
import org.apache.spark.sql.functions.{coalesce, col, lit}

/** Class ProvidedAddressConsolidation for process data of model Matrix IC.
 *
 *  @constructor create a new ProvidedAddressConsolidation with parameters: parameter
 *  @param parameter
 */
class ProvidedAddressConsolidation(parameter: Parameter) extends Transformer[DataReader, DataFrame]{

  var TaxPayerDuns: TaxPayerDuns = parameter.fields.taxPayerDuns
  val MatrixIcOut: MatrixIcOut = new MatrixIcOut(parameter.fields.matrixIcOut)
  var SunatPeople: SunatPeople = parameter.fields.sunatPeople
  val AddressContactability : AddressContactability = parameter.fields.addressContactability
  val Reniec: Reniec = parameter.fields.reniec
  val Address: Address = parameter.fields.address

  val DP = "DP"
  val SUN = "SUN"
  val SUP = "SUP"
  val ADDR = "ADDR"
  val CON = "CON"
  val TPD = "TPD"

  val nuRnk = "nu_rnk"
  val sourceName = "source"

  /** Method for prepare Df Tax Payer Duns with a given dfTaxPayerDuns
   *
   *  @param dfTaxPayerDuns
   *  @return a new DataFrame with a given dfTaxPayerDuns
   */
  def prepareDfTaxPayerDuns(dfTaxPayerDuns: DataFrame): DataFrame = {
    dfTaxPayerDuns.select(
      TaxPayerDuns.taxpayerId,
      TaxPayerDuns.companyTradeName
    ).distinct
  }

  /** Method for get Data Person Filtered with a given dfDataPerson
   *
   *  @param dfDataPerson
   *  @return a new DataFrame with a given dfDataPerson
   */
  def getDataPersonFiltered(dfDataPerson: DataFrame): DataFrame = {
    dfDataPerson.filter(
      coalesce(col(MatrixIcOut.employerTaxpayerDunsId), lit("")) =!= lit("")
    )
  }

  /** Method for get Company Name with a given 
   *
   *  @return a new Column with a given 
   */
  def getCompanyName(): Column = {
    when(
      col(getAlias(SUP, SunatPeople.companyTradeName)).isNotNull,
      col(getAlias(SUP, SunatPeople.companyTradeName))
    ).when(
      coalesce(col(getAlias(SUP, SunatPeople.customerName)), lit("-")) !== lit("-"),
      col(getAlias(SUP, SunatPeople.customerName))
    ).when(
      col(getAlias(TPD, TaxPayerDuns.companyTradeName)).isNotNull,
      col(getAlias(TPD, TaxPayerDuns.companyTradeName))
    )
  }

  /** Method for get Validate Location with a given columnName
   *
   *  @param columnName
   *  @return a new Column with a given columnName
   */
  def getValidateLocation(columnName: String): Column = {
    when(col(getAlias(SUN, columnName)).isNull,
      when(col(getAlias(ADDR, columnName)).isNull,
        when(col(getAlias(CON, columnName)).isNull,""
        ).otherwise(col(getAlias(CON, columnName)))
      ).otherwise(col(getAlias(ADDR, columnName)))
    ).otherwise(col(getAlias(SUN, columnName)))
  }

  /** Method for get Ubigeo with a given 
   *
   *  @return a new Column with a given 
   */
  def getUbigeo(): Column = {
    getValidateLocation(MatrixIcOut.geolocSunatId)
  }

  /** Method for get Adress with a given 
   *
   *  @return a new Column with a given 
   */
  def getAdress(): Column = {
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
    getValidateLocation(MatrixIcOut.stateName)
  }

  /** Method for get Province with a given 
   *
   *  @return a new Column with a given 
   */
  def getProvince(): Column = {
    getValidateLocation(MatrixIcOut.provinceName)
  }

  /** Method for get District with a given 
   *
   *  @return a new Column with a given 
   */
  def getDistrict(): Column = {
    getValidateLocation(MatrixIcOut.districtName)
  }

  /** Method for getsource with a given 
   *
   *  @return a new Column with a given 
   */
  def getsource(): Column = {
    concat(
      when(col(getAlias(SUN, sourceName)).isNull,"").otherwise(col(getAlias(SUN, sourceName))),
      lit(parameter.local.sourceSeparator),
      when(col(getAlias(ADDR, sourceName)).isNull,"").otherwise(col(getAlias(ADDR, sourceName))),
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
        col(getAlias(DP, MatrixIcOut.employerTaxpayerDunsId)),
        col(getAlias(DP, MatrixIcOut.employerCustomerId))
      ).orderBy(
        col(getAlias(DP, MatrixIcOut.employerTaxpayerDunsId)),
        col(getAlias(DP, MatrixIcOut.employerCustomerId))
      )
    )
  }

  /** Method for transform with a given dataReader
   *
   *  @param dataReader
   *  @return a new DataFrame with a given dataReader
   */
  override def transform(dataReader: DataReader): DataFrame = {
    val dfDataPersonFiltered = getDataPersonFiltered(dataReader.get("dfDataPerson"))
    val dfSunatAddress = dataReader.get("dfSunatAddress")
    val dfCustomerAddress = dataReader.get("dfCustomerAddress")
    val dfContactAddress = dataReader.get("dfContactAddress")
    val dfSunatPeople = dataReader.get("sunatPeople")
    val dfTaxPayerDuns = dataReader.get("taxpayerDuns")

    dfDataPersonFiltered.alias(DP).join(
      dfSunatAddress.alias(SUN),
      col(getAlias(DP, MatrixIcOut.employerTaxpayerDunsId)) === col(getAlias(SUN, SunatPeople.taxpayerId)),
      "left_outer"
    ).join(
      dfCustomerAddress.alias(ADDR),
      col(getAlias(DP, MatrixIcOut.employerCustomerId)) === col(getAlias(ADDR, Address.customerId)),
      "left_outer"
    ).join(
      dfContactAddress.alias(CON),
      col(getAlias(DP, MatrixIcOut.employerTaxpayerDunsId)) === col(getAlias(CON, AddressContactability.personalId)),
      "left_outer"
    ).join(
      dfSunatPeople.alias(SUP),
      col(getAlias(DP, MatrixIcOut.employerTaxpayerDunsId)) === col(getAlias(SUP, SunatPeople.taxpayerId)),
      "left_outer"
    ).join(
      dfTaxPayerDuns.alias(TPD),
      col(getAlias(DP, MatrixIcOut.employerTaxpayerDunsId)) === col(getAlias(TPD, TaxPayerDuns.taxpayerId)),
      "left_outer"
    ).select(
      //col(getAlias(DP, MatrixIcOut.employerTaxpayerDunsId)),
      col(getAlias(DP, MatrixIcOut.employerCustomerId)),
      getCompanyName().alias(MatrixIcOut.companyName),
      // Ubigeo
      getUbigeo().alias("codigoemisora7"),
      // Direccion
      getAdress().alias(MatrixIcOut.issuingEntityAddressDesc),
      //Urbanización
      //getUrbanization().alias(MatrixIcOut.urbanizationName),
      // Referencia
      //getReference().alias(MatrixIcOut.additionalAddressDataDesc),
      // Departamento
      getState().alias(MatrixIcOut.issuingDepartmentName),
      // Provincia
      getProvince().alias(MatrixIcOut.issuingProvinceName),
      // Distrito
      getDistrict().alias(MatrixIcOut.issuingDistrictName),
      // Fuente
      //getsource().alias(sourceName),
      getNuRnk().alias(nuRnk)
    ).filter(col(nuRnk) === lit(1)).drop(nuRnk)
  }
}
