package com.bbva.datio.datahubpe.modelMatrixIC.transformations.rccProcess

import com.bbva.datio.datahubpe.modelMatrixIC.beans.MatrixIcOut
import com.bbva.datio.datahubpe.modelMatrixIC.config._
import com.bbva.datio.datahubpe.utils.processing.data.DataReader
import com.bbva.datio.datahubpe.utils.processing.flow.Transformer
import org.apache.spark.sql.{Column, DataFrame}
import org.apache.spark.sql.functions._

/** Class PersonCCRV2 for process data of model Matrix IC.
 *
 *  @constructor create a new PersonCCRV2 with parameters: parameter
 *  @param parameter
 */
class PersonCCRV2 (parameter: Parameter) extends Transformer[DataReader, DataFrame]{

  val entitys : Entitys = parameter.entitys

  val codeSbsAggregate : CodeSbsAggregate = parameter.fields.codeSbsAggregate
  val accountSbsAggregate : AccountSbsAggregate = parameter.fields.accountSbsAggregate
  val rccBalance : RccBalance = parameter.fields.rccBalance
  val matrixIcOut: MatrixIcOut = new MatrixIcOut(parameter.fields.matrixIcOut)

  //campos
  val debt = "debt"
  val rnkProdGroupSitEnt = "rnk_by_bal_product_group_sit_ent"
  val creditLine = "credit_line"
  //Validacion
  val whenCreditCardNP = col(accountSbsAggregate.commercialProductId) === lit(2) and col(accountSbsAggregate.creditType) === lit(11)
  val whenPersonalLoanNP = col(accountSbsAggregate.commercialProductId) === lit(3) and col(accountSbsAggregate.creditType) === lit(11)
  val whenVehicularLoanNP = col(accountSbsAggregate.commercialProductId) === lit(6) and col(accountSbsAggregate.creditType) === lit(11)
  val whenMortgageNP = col(accountSbsAggregate.commercialProductId) === lit(7) and col(accountSbsAggregate.creditType) === lit(11)
  //format
  val decimal = "decimal(17,2)"

  /** Method for get Balance Entity with a given nameEntityID
   *
   *  @param nameEntityID
   *  @return a new Column with a given nameEntityID
   */
  def getBalanceEntity(nameEntityID: String): Column = {
    when(
      whenCreditCardNP and
      col(rccBalance.sbsEntityId) === lit(nameEntityID),
      col(debt)
    ).otherwise(0)
  }

  /** Method for get Balance Others with a given 
   *
   *  @return a new Column with a given 
   */
  def getBalanceOthers(): Column = {
    when(
      whenCreditCardNP and
        !col(rccBalance.sbsEntityId).isin(List(entitys.fsEntIDBCP,
          entitys.fsEntIDIBK,
          entitys.fsEntIDSCO,
          entitys.fsEntIDBBVA,
          entitys.fsEntIDFINANA,
          entitys.fsEntIDSAGA): _*),
      col(debt)
    ).otherwise(0)
  }

  /** Method for get Number Of Entity Consumer Creditcard with a given 
   *
   *  @return a new Column with a given 
   */
  def getNumberOfEntityConsumerCreditcard(): Column = {
    when(whenCreditCardNP,
      col(rccBalance.sbsEntityId)
    )
  }

  /** Method for get Max Line with a given nameColumn
   *
   *  @param nameColumn
   *  @return a new Column with a given nameColumn
   */
  def getMaxLine(nameColumn: String): Column = {
    when(
      whenCreditCardNP and
        col(rnkProdGroupSitEnt) === lit(1),
      col(nameColumn)
    ).otherwise(0)
  }

  /** Method for get Loan Balance Amount with a given whenNP
   *
   *  @param whenNP
   *  @return a new Column with a given whenNP
   */
  def getLoanBalanceAmount(whenNP: Column): Column = {
    when(
      whenNP,
      col(debt)
    ).otherwise(0)
  }

  /** Method for get Loan BalBBVA Amt with a given whenNP and EntityId
   *
   *  @param whenNP
   *  @param EntityId
   *  @return a new Column with a given whenNP and EntityId
   */
  def getLoanBalBBVAAmt(whenNP: Column, EntityId: String): Column = {
    when(
      whenNP and
        col(rccBalance.sbsEntityId) === lit(EntityId),
      col(debt)
    ).otherwise(0)
  }

  /** Method for get Loan Bal Others Amt with a given whenNP and EntitysIds
   *
   *  @param whenNP
   *  @param EntitysIds
   *  @return a new Column with a given whenNP and EntitysIds
   */
  def getLoanBalOthersAmt(whenNP: Column, EntitysIds: List[String]): Column = {
    when(
      whenNP and
        !col(rccBalance.sbsEntityId).isin(EntitysIds: _*),
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
          col(codeSbsAggregate.personalType).alias(matrixIcOut.personalType))
        .agg(
          sum(when(whenCreditCardNP, col(debt)).otherwise(0)).cast(decimal).alias(matrixIcOut.fsConsumerCcBalanceAmount)
          ,sum(getBalanceEntity(entitys.fsEntIDBCP)).cast(decimal).alias(matrixIcOut.fsConsumerCcBalanceBcpAmount)
          ,sum(getBalanceEntity(entitys.fsEntIDIBK)).cast(decimal).alias(matrixIcOut.fsConsumerCcBalanceIbkAmount)
          ,sum(getBalanceEntity(entitys.fsEntIDSCO)).cast(decimal).alias(matrixIcOut.fsConsumerCcBalanceScoAmount)
          ,sum(getBalanceEntity(entitys.fsEntIDBBVA)).cast(decimal).alias(matrixIcOut.fsConsumerCcBalanceBbvaAmount)
          ,sum(getBalanceEntity(entitys.fsEntIDFINANA)).cast(decimal).alias(matrixIcOut.fsConsumerCcBalanceFinanAmount)
          ,sum(getBalanceEntity(entitys.fsEntIDSAGA)).cast(decimal).alias(matrixIcOut.fsConsumerCcBalanceSagaAmount)
          ,sum(getBalanceOthers()).cast(decimal).alias(matrixIcOut.fsConsumerCcBalanceOthersAmount)

          ,countDistinct(getNumberOfEntityConsumerCreditcard()).alias(matrixIcOut.fsNumberOfEntityConsumerCreditcard)
          ,max(getMaxLine(debt)).alias(matrixIcOut.fsPrincipalConsumerCcBalanceAmount)
          ,max(getMaxLine(creditLine)).cast(decimal).alias(matrixIcOut.maxCcLine)
          ,sum(getLoanBalanceAmount(whenPersonalLoanNP)).cast(decimal).alias(matrixIcOut.fsPersonalLoanBalanceAmount)
          ,sum(getLoanBalBBVAAmt(whenPersonalLoanNP,entitys.fsEntIDBBVA)).cast(decimal).alias(matrixIcOut.fsPersonalLoanBalanceBbvaAmount)

          ,sum(getLoanBalOthersAmt(whenPersonalLoanNP ,List(entitys.fsEntIDBBVA))).cast(decimal).alias(matrixIcOut.fsPersonalLoanBalanceOthersAmount)
          ,sum(getLoanBalanceAmount(whenVehicularLoanNP)).cast(decimal).alias(matrixIcOut.fsVehicularLoanBalanceAmount)
          ,sum(getLoanBalBBVAAmt(whenVehicularLoanNP,entitys.fsEntIDBBVA)).cast(decimal).alias(matrixIcOut.fsVehicularLoanBalanceBbvaAmount)
          ,sum(getLoanBalBBVAAmt(whenVehicularLoanNP,entitys.fsEntIDBBVACF)).cast(decimal).alias(matrixIcOut.fsVehicularLoanBalanceBbvaCfAmount)
          ,sum(
            getLoanBalOthersAmt(
              whenVehicularLoanNP,
              List(entitys.fsEntIDBBVA, entitys.fsEntIDBBVACF)
            )
          ).cast(decimal).alias(matrixIcOut.fsVehicularLoanBalanceOthersCfAmount)
          ,sum(getLoanBalanceAmount(whenMortgageNP)).cast(decimal).alias(matrixIcOut.fsMortgageBalanceAmount)
          ,sum(getLoanBalBBVAAmt(whenMortgageNP,entitys.fsEntIDBBVA)).cast(decimal).alias(matrixIcOut.fsMortageBalanceBbvaAmount)
          ,sum(getLoanBalOthersAmt(whenMortgageNP,List(entitys.fsEntIDBBVA))).cast(decimal).alias(matrixIcOut.fsMortageBalanceOthersAmount))
  }

}
