package com.bbva.datio.datahubpe.modelMatrixIC.transformations.rccProcess

import com.bbva.datio.datahubpe.modelMatrixIC.beans.MatrixIcOut
import com.bbva.datio.datahubpe.modelMatrixIC.config._
import com.bbva.datio.datahubpe.utils.processing.data.DataReader
import com.bbva.datio.datahubpe.utils.processing.flow.Transformer
import org.apache.spark.sql.{Column, DataFrame}
import org.apache.spark.sql.functions._

/** Class PersonCCRV3 for process data of model Matrix IC.
 *
 *  @constructor create a new PersonCCRV3 with parameters: parameter
 *  @param parameter
 */
class PersonCCRV3 (parameter: Parameter) extends Transformer[DataReader, DataFrame]{

  val entitys : Entitys = parameter.entitys

  val codeSbsAggregate : CodeSbsAggregate = parameter.fields.codeSbsAggregate
  val rccBalance : RccBalance = parameter.fields.rccBalance
  val matrixIcOut: MatrixIcOut = new MatrixIcOut(parameter.fields.matrixIcOut)
  val accountSbsAggregate : AccountSbsAggregate = parameter.fields.accountSbsAggregate

  //Columns
  val debt = "debt"
  val decimal = "decimal(17,2)"
  val rnkProdEnt = "rnk_by_bal_product_ent"

  val whenDirectRiskBusiness = col(accountSbsAggregate.commercialProductId) === lit(3) and col(accountSbsAggregate.creditType).isin(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
  val whenEnorseLet = col(accountSbsAggregate.commercialSubproductId) === lit(26)
  val whenMortgageGuar = col(accountSbsAggregate.commercialSubproductId) === lit(34)

  val entitySelected = List(entitys.fsEntIDBCP,
                            entitys.fsEntIDIBK,
                            entitys.fsEntIDSCO,
                            entitys.fsEntIDBBVA,
                            entitys.fsEntIDSAGA,
                            entitys.fsEntIDMIBCO)

  /** Method for get Balance Amount with a given whenAditional
   *
   *  @param whenAditional
   *  @return a new Column with a given whenAditional
   */
  def getBalanceAmount(whenAditional: Column): Column = {
    when(
      whenAditional,
      col(debt)
    ).otherwise(0)
  }

  /** Method for get Business Balance Entity with a given NameEntityId
   *
   *  @param NameEntityId
   *  @return a new Column with a given NameEntityId
   */
  def getBusinessBalanceEntity(NameEntityId: String): Column = {
    when(
      whenDirectRiskBusiness and
        col(rccBalance.sbsEntityId) === lit(NameEntityId),
      col(debt)
    ).otherwise(0)
  }

  /** Method for get Endorsement Balance Entity with a given NameEntityId
   *
   *  @param NameEntityId
   *  @return a new Column with a given NameEntityId
   */
  def getEndorsementBalanceEntity(NameEntityId: String): Column = {
    when(
      whenEnorseLet and
      col(rccBalance.sbsEntityId) === lit(NameEntityId),
      col(debt)
    ).otherwise(0)
  }

  /** Method for get Mortgage Balance Entity with a given NameEntityId
   *
   *  @param NameEntityId
   *  @return a new Column with a given NameEntityId
   */
  def getMortgageBalanceEntity(NameEntityId: String): Column = {
    when(
      whenMortgageGuar and
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
        sum(getBalanceAmount(whenDirectRiskBusiness)).cast(decimal).alias(matrixIcOut.fsBusinessBalanceAmount),
        sum(getBusinessBalanceEntity(entitys.fsEntIDBCP)).cast(decimal).alias(matrixIcOut.fsBusinessBalanceBcpAmount),
        sum(getBusinessBalanceEntity(entitys.fsEntIDIBK)).cast(decimal).alias(matrixIcOut.fsBusinessBalanceIbkAmount),
        sum(getBusinessBalanceEntity(entitys.fsEntIDSCO)).cast(decimal).alias(matrixIcOut.fsBusinessBalanceScoAmount),
        sum(getBusinessBalanceEntity(entitys.fsEntIDBBVA)).cast(decimal).alias(matrixIcOut.fsBusinessBalanceBbvaAmount),
        sum(getBusinessBalanceEntity(entitys.fsEntIDMIBCO)).cast(decimal).alias(matrixIcOut.fsBusinessBalanceMibcoAmount),
        sum(getBusinessBalanceEntity(entitys.fsEntIDCJPIU)).cast(decimal).alias(matrixIcOut.fsBusinessBalanceCjPiuAmount),
        sum(getBusinessBalanceEntity(entitys.fsEntIDCJTRU)).cast(decimal).alias(matrixIcOut.fsBusinessBalanceCjTruAmount),
        sum(getBusinessBalanceEntity(entitys.fsEntIDCJAQP)).cast(decimal).alias(matrixIcOut.fsBusinessBalanceCjArqAmount),
        sum(getBusinessBalanceEntity(entitys.fsEntIDCJHYO)).cast(decimal).alias(matrixIcOut.fsBusinessBalanceCjHyoAmount),
        sum(getBusinessBalanceEntity(entitys.fsEntIDCJCUS)).cast(decimal).alias(matrixIcOut.fsBusinessBalanceCjCusAmount),
        sum(getBusinessBalanceEntity(entitys.fsEntIDCJMAY)).cast(decimal).alias(matrixIcOut.fsBusinessBalanceCjMayAmount),
        sum(getBusinessBalanceEntity(entitys.fsEntIDCJPIS)).cast(decimal).alias(matrixIcOut.fsBusinessBalanceCjPisAmount),
        sum(getBusinessBalanceEntity(entitys.fsEntIDCJSULL)).cast(decimal).alias(matrixIcOut.fsBusinessBalanceCjSullAmount),
        sum(getBusinessBalanceEntity(entitys.fsEntIDCJTAC)).cast(decimal).alias(matrixIcOut.fsBusinessBalanceBcjTacAmount),
        sum(getBusinessBalanceEntity(entitys.fsEntIDCJICA)).cast(decimal).alias(matrixIcOut.fsBusinessBalanceCjIcaAmount),
        sum(getBalanceAmount(whenEnorseLet)).cast(decimal).alias(matrixIcOut.fsEndorsementBalanceAmount),
        sum(getEndorsementBalanceEntity(entitys.fsEntIDBCP)).cast(decimal).alias(matrixIcOut.fsEndorsementBalanceBcpAmount),
        sum(getEndorsementBalanceEntity(entitys.fsEntIDIBK)).cast(decimal).alias(matrixIcOut.fsEndorsementBalanceIbkAmount),
        sum(getEndorsementBalanceEntity(entitys.fsEntIDSCO)).cast(decimal).alias(matrixIcOut.fsEndorsementBalanceScoAmount),
        sum(getEndorsementBalanceEntity(entitys.fsEntIDBBVA)).cast(decimal).alias(matrixIcOut.fsEndorsementBalanceBbvaAmount),
        sum(getEndorsementBalanceEntity(entitys.fsEntIDSAGA)).cast(decimal).alias(matrixIcOut.fsEndorsementBalanceSagaAmount),
        sum(getEndorsementBalanceEntity(entitys.fsEntIDMIBCO)).cast(decimal).alias(matrixIcOut.fsEndorsementBalanceMibcoAmount),
        sum(getBalanceAmount(whenMortgageGuar)).cast(decimal).alias(matrixIcOut.fsMortgageBackedBalanceAmount),
        max(when(whenMortgageGuar and col(rnkProdEnt) === lit(1), col(debt)).otherwise(0)
        ).cast(decimal).alias(matrixIcOut.fsPrincipalMortgageBackedBalanceAmount),
        sum(getMortgageBalanceEntity(entitys.fsEntIDBCP)).cast(decimal).alias(matrixIcOut.fsMortgageBackedBalanceBcpAmount),
        sum(getMortgageBalanceEntity(entitys.fsEntIDIBK)).cast(decimal).alias(matrixIcOut.fsMortgageBackedBalanceIbkAmount),
        sum(getMortgageBalanceEntity(entitys.fsEntIDSCO)).cast(decimal).alias(matrixIcOut.fsMortgageBackedBalanceScoAmount),
        sum(getMortgageBalanceEntity(entitys.fsEntIDBBVA)).cast(decimal).alias(matrixIcOut.fsMortgageBackedBalanceBbvaAmount),
        sum(getMortgageBalanceEntity(entitys.fsEntIDSAGA)).cast(decimal).alias(matrixIcOut.fsMortgageBackedBalanceSagaAmount),
        sum(getMortgageBalanceEntity(entitys.fsEntIDMIBCO)).cast(decimal).alias(matrixIcOut.fsMortgageBackedBalanceMibcoAmount),
        sum(
          when(
            whenMortgageGuar and
              !col(rccBalance.sbsEntityId).isin(entitySelected: _*),
            col(debt)
          ).otherwise(0)
        ).cast(decimal).alias(matrixIcOut.fsMortgageBackedBalanceOthersAmount)
      )
  }

}
