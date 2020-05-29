package com.bbva.datio.datahubpe.modelMatrixIC.transformations.basicsDataProcess

import com.bbva.datio.datahubpe.modelMatrixIC.beans.MatrixIcOut
import org.apache.spark.sql.functions._
import com.bbva.datio.datahubpe.modelMatrixIC.config.{GeoLocationMaster, Parameter, Reniec}
import com.bbva.datio.datahubpe.utils.processing.data.DataReader
import com.bbva.datio.datahubpe.utils.processing.flow.Transformer
import com.bbva.datio.datahubpe.modelMatrixIC.utils.caseUtils.getAlias
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.col

/** Class ReniecAddress for process data of model Matrix IC.
 *
 *  @constructor create a new ReniecAddress with parameters: parameter
 *  @param parameter
 */
class ReniecAddress (parameter: Parameter) extends Transformer[DataReader, DataFrame]{

  val Reniec: Reniec = parameter.fields.reniec
  val GeoLocationMaster: GeoLocationMaster = parameter.fields.geoLocationMaster
  val MatrixIcOut: MatrixIcOut = new MatrixIcOut(parameter.fields.matrixIcOut)

  val source = "reniec"
  val sourceName = "source"
  val rnkFilter = "rnk_filter"

  /** Method for prepare Df Reniec with a given dfReniec
   *
   *  @param dfReniec
   *  @return a new DataFrame with a given dfReniec
   */
  def prepareDfReniec(dfReniec: DataFrame): DataFrame = {
    dfReniec.select(
      col(Reniec.personalId),
      col(Reniec.personalType),
      col(Reniec.voteGeolocationId)
    )
  }

  /** Method for transform with a given dataReader
   *
   *  @param dataReader
   *  @return a new DataFrame with a given dataReader
   */
  override def transform(dataReader: DataReader): DataFrame = {
    val dfReniec = prepareDfReniec(dataReader.get("reniec"))
    val dfGeoLocationMaster = dataReader.get("dfGeoLocationMaster")
    val REN = "REN"
    val GEO = "GEO"

    dfReniec.alias(REN).
      join(
        dfGeoLocationMaster.alias(GEO)
        ,col(getAlias(GEO, GeoLocationMaster.reniecAddressGeolocationId)) === col(getAlias(REN, Reniec.voteGeolocationId))
        ,"left_outer"
      ).
      select(
        col(getAlias(REN, Reniec.personalId)),
        col(getAlias(REN, Reniec.personalType)),
        col(getAlias(GEO, GeoLocationMaster.reniecAddressGeolocationId)),
        col(getAlias(GEO, GeoLocationMaster.addressGeolocationId)).alias(MatrixIcOut.geolocSunatId),
        col(getAlias(GEO, GeoLocationMaster.addressDistrictName)).alias(MatrixIcOut.districtName),
        col(getAlias(GEO, GeoLocationMaster.provinceName)).alias(MatrixIcOut.provinceName),
        col(getAlias(GEO, GeoLocationMaster.addressDepartmentName)).alias(MatrixIcOut.stateName),
        lit(source).alias(sourceName),
        row_number().over(
          Window.partitionBy(
            col(getAlias(REN, Reniec.personalId)),
            col(getAlias(REN, Reniec.personalType))
          ).orderBy(
            col(getAlias(REN, Reniec.personalId)),
            col(getAlias(REN, Reniec.personalType))
          )
        ).alias(rnkFilter)
      ).filter(col(rnkFilter)===lit(1)).drop(col(rnkFilter))
  }
}

