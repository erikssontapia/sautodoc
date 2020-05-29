package com.bbva.datio.datahubpe.modelMatrixIC.transformations.productProcess

import com.bbva.datio.datahubpe.modelMatrixIC.beans.MatrixIcOut
import com.bbva.datio.datahubpe.modelMatrixIC.config.{BalanceMonthlyCorp, Parameter, PeopleDaily}
import com.bbva.datio.datahubpe.modelMatrixIC.utils.caseUtils.getAlias
import com.bbva.datio.datahubpe.utils.processing.data.DataReader
import com.bbva.datio.datahubpe.utils.processing.flow.Transformer
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._
import scala.collection.JavaConverters._

/** Class MarginConsolidation for process data of model Matrix IC.
 *
 *  @constructor create a new MarginConsolidation with parameters: parameter
 *  @param parameter
 */
class MarginConsolidation(parameter: Parameter) extends Transformer[DataReader, DataFrame]{

  val PeopleDaily: PeopleDaily = parameter.fields.peopleDaily
  val BalanceMonthlyCorp: BalanceMonthlyCorp = parameter.fields.balanceMonthlyCorp
  val MatrixIcOut: MatrixIcOut = new MatrixIcOut(parameter.fields.matrixIcOut)

  // Alias

  /** Method for prepare Df Balance Monthly Corp with a given dfBalanceMonthlyCorp
   *
   *  @param dfBalanceMonthlyCorp
   *  @return a new DataFrame with a given dfBalanceMonthlyCorp
   */
  def prepareDfBalanceMonthlyCorp(dfBalanceMonthlyCorp: DataFrame): DataFrame = {
    dfBalanceMonthlyCorp.filter(
      col(BalanceMonthlyCorp.creatingServiceId).isin(parameter.local.creatingServiceIdList.asScala:_*)
    )
  }

  /** Method for transform with a given dataReader
   *
   *  @param dataReader
   *  @return a new DataFrame with a given dataReader
   */
  override def transform(dataReader: DataReader): DataFrame = {
    val dfPeopleDaily = dataReader.get("peopleDaily")
    val dfBalanceMonthlyCorp = prepareDfBalanceMonthlyCorp(dataReader.get("balanceMonthlyCorp"))
    val PD = "PD"
    val BMC = "BMC"

    dfBalanceMonthlyCorp.alias(BMC).join(
      dfPeopleDaily.alias(PD),
      Seq(BalanceMonthlyCorp.customerId),
      "left"
    ).groupBy(
      getAlias(PD, PeopleDaily.personalId),
      getAlias(PD, PeopleDaily.personalType)
    ).agg(
        sum(BalanceMonthlyCorp.resultMonthBalCtvalAmount).alias(MatrixIcOut.marMonthlyAmount),
        sum(BalanceMonthlyCorp.resultYearBalCtvalAmount).alias(MatrixIcOut.marYearlyAmount)
    )
  }
}
