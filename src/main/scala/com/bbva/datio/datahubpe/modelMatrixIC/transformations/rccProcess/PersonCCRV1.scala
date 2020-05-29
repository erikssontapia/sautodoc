package com.bbva.datio.datahubpe.modelMatrixIC.transformations.rccProcess

import com.bbva.datio.datahubpe.modelMatrixIC.beans.MatrixIcOut
import com.bbva.datio.datahubpe.modelMatrixIC.config._
import com.bbva.datio.datahubpe.utils.processing.data.DataReader
import com.bbva.datio.datahubpe.utils.processing.flow.Transformer
import org.apache.spark.sql.{Column, DataFrame}
import org.apache.spark.sql.functions.{col, lit, sum, when}

/** Class PersonCCRV1 for process data of model Matrix IC.
 *
 *  @constructor create a new PersonCCRV1 with parameters: parameter
 *  @param parameter
 */
class PersonCCRV1 (parameter: Parameter) extends Transformer[DataReader, DataFrame]{

  val entitys : Entitys = parameter.entitys

  val accountSbsAggregate : AccountSbsAggregate = parameter.fields.accountSbsAggregate
  val codeSbsAggregate : CodeSbsAggregate = parameter.fields.codeSbsAggregate
  val rccBalance : RccBalance = parameter.fields.rccBalance
  val matrixIcOut: MatrixIcOut = new MatrixIcOut(parameter.fields.matrixIcOut)

  //Columns
  val debt = "debt"
  val decimal = "decimal(17,2)"

  //Conditions
  val whenDirectRisk = col(accountSbsAggregate.productGroupType) === lit(1)

  /** Method for get Balance Entity with a given NameEntityId
   *
   *  @param NameEntityId
   *  @return a new Column with a given NameEntityId
   */
  def getBalanceEntity(NameEntityId: String): Column = {
    when(
        whenDirectRisk and
        col(rccBalance.sbsEntityId) === lit(NameEntityId),
        col(debt)
    ).otherwise(0)
  }

  /** Method for transform with a given dataReader
   *
   *  @param dataReader
   *  @return a new DataFrame with a given dataReader
   */
  override def transform(dataReader: DataReader): DataFrame = {
    val dfGroupBalanceCCR = dataReader.get("dfGroupBalanceCCR")

    dfGroupBalanceCCR.
      groupBy(
        col(codeSbsAggregate.personalId).alias(matrixIcOut.personalId),
        col(codeSbsAggregate.personalType).alias(matrixIcOut.personalType)
      ).agg(
        sum(when(whenDirectRisk, col(debt)).otherwise(0)).cast(decimal).alias(matrixIcOut.fsBalanceAmount)
        ,sum(getBalanceEntity(entitys.fsEntIDBCP)).cast(decimal).alias(matrixIcOut.fsBalanceBcpAmount)
        ,sum(getBalanceEntity(entitys.fsEntIDIBK)).cast(decimal).alias(matrixIcOut.fsBalanceIbkAmount)
        ,sum(getBalanceEntity(entitys.fsEntIDSCO)).cast(decimal).alias(matrixIcOut.fsBalanceScoAmount)
        ,sum(getBalanceEntity(entitys.fsEntIDBBVA)).cast(decimal).alias(matrixIcOut.fsBalanceBbvaAmount)
        ,sum(getBalanceEntity(entitys.fsEntIDSAGA)).cast(decimal).alias(matrixIcOut.fsBalanceSagaAmount)
        ,sum(getBalanceEntity(entitys.fsEntIDMIBCO)).cast(decimal).alias(matrixIcOut.fsBalanceMibcoAmount)
        ,sum(getBalanceEntity(entitys.fsEntIDCJPIU)).cast(decimal).alias(matrixIcOut.fsBalanceCjBiuAmount)
        ,sum(getBalanceEntity(entitys.fsEntIDCJTRU)).cast(decimal).alias(matrixIcOut.fsBalanceCjTruAmount)
        ,sum(getBalanceEntity(entitys.fsEntIDCJAQP)).cast(decimal).alias(matrixIcOut.fsBalanceCjArqAmount)
        ,sum(getBalanceEntity(entitys.fsEntIDCJHYO)).cast(decimal).alias(matrixIcOut.fsBalanceCjHyoAmount)
        ,sum(getBalanceEntity(entitys.fsEntIDCJCUS)).cast(decimal).alias(matrixIcOut.fsBalanceCjCusAmount)
        ,sum(getBalanceEntity(entitys.fsEntIDCJMAY)).cast(decimal).alias(matrixIcOut.fsBalanceCjMayAmount)
        ,sum(getBalanceEntity(entitys.fsEntIDCJPIS)).cast(decimal).alias(matrixIcOut.fsBalanceCjPisAmount)
        ,sum(getBalanceEntity(entitys.fsEntIDCJSULL)).cast(decimal).alias(matrixIcOut.fsBalanceCjSullAmount)
        ,sum(getBalanceEntity(entitys.fsEntIDCJTAC)).cast(decimal).alias(matrixIcOut.fsBalanceCjTacAmount)
        ,sum(getBalanceEntity(entitys.fsEntIDCJICA)).cast(decimal).alias(matrixIcOut.fsBalanceCjIcaAmount)
      )
  }

}
