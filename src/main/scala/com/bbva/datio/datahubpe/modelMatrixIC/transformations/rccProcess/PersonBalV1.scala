package com.bbva.datio.datahubpe.modelMatrixIC.transformations.rccProcess

import com.bbva.datio.datahubpe.modelMatrixIC.beans.MatrixIcOut
import com.bbva.datio.datahubpe.modelMatrixIC.config.{AccountSbsAggregate, CodeSbsAggregate, Parameter, RccBalance}
import com.bbva.datio.datahubpe.utils.processing.data.DataReader
import com.bbva.datio.datahubpe.utils.processing.flow.Transformer
import org.apache.spark.sql.{Column, DataFrame}
import org.apache.spark.sql.expressions.Window.partitionBy
import org.apache.spark.sql.functions.{col, lit, row_number, sum, when}

/** Class PersonBalV1 for process data of model Matrix IC.
 *
 *  @constructor create a new PersonBalV1 with parameters: parameter
 *  @param parameter
 */
class PersonBalV1 (parameter: Parameter) extends Transformer[DataReader, DataFrame]{


  val accountSbsAggregate : AccountSbsAggregate = parameter.fields.accountSbsAggregate
  val codeSbsAggregate : CodeSbsAggregate = parameter.fields.codeSbsAggregate
  val rccBalance : RccBalance = parameter.fields.rccBalance
  val matrixIcOut: MatrixIcOut = new MatrixIcOut(parameter.fields.matrixIcOut)

  //Columns
  val higherRiskBalEntId = "higher_risk_bal_entity_id"
  val entityHigherRiskBalAmount = "entity_higher_risk_bal_amount"
  val debt = "debt"
  val rnkByValEnt = "rnk_by_bal_entity"
  //Format
  val decimal23 = "decimal(23,10)"

  /** Method for get Direct Risk with a given 
   *
   *  @return a new Column with a given 
   */
  def getDirectRisk(): Column = {
    when(
      col(accountSbsAggregate.productGroupType) === lit(1),
      col(debt)
    ).otherwise(0)
  }

  /** Method for get Rnk By Val Ent with a given 
   *
   *  @return a new Column with a given 
   */
  def getRnkByValEnt(): Column = {
    row_number().over(
      partitionBy(
        matrixIcOut.personalId,
        matrixIcOut.personalType
      ).orderBy(col(entityHigherRiskBalAmount).desc)
    )
  }

  /** Method for transform with a given dataReader
   *
   *  @param dataReader
   *  @return a new DataFrame with a given dataReader
   */
  override def transform(dataReader: DataReader): DataFrame = {

    val dfGroupBalanceCCR = dataReader.get("dfGroupBalanceCCR")

    val dfGroupBalanceCCR_ = dfGroupBalanceCCR.orderBy(col(codeSbsAggregate.personalId).desc,
      col(codeSbsAggregate.personalType).desc,
      col(rccBalance.sbsEntityId).desc)

    dfGroupBalanceCCR_.
      groupBy(
        col(codeSbsAggregate.personalId).alias(matrixIcOut.personalId),
        col(codeSbsAggregate.personalType).alias(matrixIcOut.personalType),
        col(rccBalance.sbsEntityId).alias(higherRiskBalEntId)
      ).agg(
        sum(getDirectRisk())
          .cast(decimal23)
          .alias(entityHigherRiskBalAmount)
      ).withColumn(
        rnkByValEnt,
        getRnkByValEnt()
      )
      .filter(col(rnkByValEnt) === lit(1))
      .drop(rnkByValEnt)


  }
}
