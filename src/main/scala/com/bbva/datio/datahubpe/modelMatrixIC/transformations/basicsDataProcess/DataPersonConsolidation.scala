package com.bbva.datio.datahubpe.modelMatrixIC.transformations.basicsDataProcess

import com.bbva.datio.datahubpe.modelMatrixIC.beans.MatrixIcOut
import org.apache.spark.sql.functions._
import com.bbva.datio.datahubpe.modelMatrixIC.config._
import com.bbva.datio.datahubpe.modelMatrixIC.utils.caseUtils.getAlias
import com.bbva.datio.datahubpe.utils.processing.data.DataReader
import com.bbva.datio.datahubpe.utils.processing.flow.Transformer
import org.apache.spark.sql.{Column, DataFrame}
import org.apache.spark.sql.functions.{col, lit, max, trim}

/** Class DataPersonConsolidation for process data of model Matrix IC.
 *
 *  @constructor create a new DataPersonConsolidation with parameters: parameter
 *  @param parameter
 */
class DataPersonConsolidation(parameter: Parameter) extends Transformer[DataReader, DataFrame]{

  val Manager: Manager = parameter.fields.manager
  val Employee: Employee = parameter.fields.employee
  val BranchCatalog: BranchCatalog = parameter.fields.branchCatalog
  val HierarchyBranch: HierarchyBranch = parameter.fields.hierarchyBranch
  val MatrixIcOut: MatrixIcOut = new MatrixIcOut(parameter.fields.matrixIcOut)
  val PeopleBoardAggregate: PeopleBoardAggregate = parameter.fields.peopleBoardAggregate

  val DP: String = "DP"
  val MNG: String = "MNG"
  val MEP: String = "MEP"
  val PBA: String = "PBA"
  val BRC: String = "BRC"
  val HEP: String = "HEP"

  val levelTerritDesc: String = "level50_territorial_desc"
  val levelOperDesc: String = "level60_operarea_desc"

  /** Method for prepare Df Manager with a given dfManager
   *
   *  @param dfManager
   *  @return a new DataFrame with a given dfManager
   */
  def prepareDfManager(dfManager: DataFrame): DataFrame = {
    dfManager.filter(
      col(Manager.entityId) === lit(parameter.local.bbvaEntityID)
    )
  }

  /** Method for prepare Df Employees with a given dfEmployees
   *
   *  @param dfEmployees
   *  @return a new DataFrame with a given dfEmployees
   */
  def prepareDfEmployees(dfEmployees: DataFrame): DataFrame = {
    dfEmployees.groupBy(
      Employee.employeeType,
      Employee.managerEmployeeId,
      Employee.personalType,
      Employee.personalId,
      Employee.workPhoneNumberId
    ).agg(
      max(col(Employee.userEmailDesc)).alias(Employee.userEmailDesc)
    ).filter(col(Employee.employeeType) === parameter.local.employeeType)
  }

  /** Method for prepare Df Employees Grouped with a given dfEmployees
   *
   *  @param dfEmployees
   *  @return a new DataFrame with a given dfEmployees
   */
  def prepareDfEmployeesGrouped(dfEmployees: DataFrame): DataFrame = {
    dfEmployees.groupBy(
      Employee.personalType,
      Employee.personalId
    ).agg(
      max(Employee.userEmailDesc).alias(Employee.userEmailDesc)
    )
  }

  /** Method for prepare Df Branch Catalog with a given dfBranchCatalog
   *
   *  @param dfBranchCatalog
   *  @return a new DataFrame with a given dfBranchCatalog
   */
  def prepareDfBranchCatalog(dfBranchCatalog: DataFrame): DataFrame = {
    dfBranchCatalog.select(
      BranchCatalog.branchId,
      BranchCatalog.branchDesc,
      BranchCatalog.childrenBranchLevelId,
      BranchCatalog.addressGeolocationId).distinct
  }

  /** Method for prepare Df Hierarchy Branch with a given dfHierarchyBranch
   *
   *  @param dfHierarchyBranch
   *  @return a new DataFrame with a given dfHierarchyBranch
   */
  def prepareDfHierarchyBranch(dfHierarchyBranch: DataFrame): DataFrame = {
    dfHierarchyBranch.select(
      HierarchyBranch.branchId,
      HierarchyBranch.level50TerritorialId,
      HierarchyBranch.level60OperareaId
    ).distinct()
  }

  /** Method for prepare Df Branch with a given dfBranchCatalog and dfHierarchyBranch
   *
   *  @param dfBranchCatalog
   *  @param dfHierarchyBranch
   *  @return a new DataFrame with a given dfBranchCatalog and dfHierarchyBranch
   */
  def prepareDfBranch(dfBranchCatalog: DataFrame, dfHierarchyBranch: DataFrame): DataFrame = {
    val HIR: String = "HIR"
    val L00: String = "L00"
    val L50: String = "L50"
    val L60: String = "L60"

    dfHierarchyBranch.alias(HIR).join(
      dfBranchCatalog.alias(L00),
      trim(col(getAlias(L00, BranchCatalog.branchId))) === trim(col(getAlias(HIR, HierarchyBranch.branchId))),
      "left_outer"
    ).join(
      dfBranchCatalog.alias(L50),
      trim(col(getAlias(L50, BranchCatalog.branchId))) === trim(col(getAlias(HIR, HierarchyBranch.level50TerritorialId))),
      "left_outer"
    ).join(
      dfBranchCatalog.alias(L60),
      trim(col(getAlias(L60, BranchCatalog.branchId))) === trim(col(getAlias(HIR, HierarchyBranch.level60OperareaId))),
      "left_outer"
    ).select(
      col(getAlias(HIR, HierarchyBranch.branchId)).alias(HierarchyBranch.branchId),
      col(getAlias(L00, BranchCatalog.branchDesc)).alias(BranchCatalog.branchDesc),
      col(getAlias(L00, BranchCatalog.childrenBranchLevelId)).alias(BranchCatalog.childrenBranchLevelId),
      col(getAlias(HIR, HierarchyBranch.level50TerritorialId)).alias(HierarchyBranch.level50TerritorialId),
      col(getAlias(L50, BranchCatalog.branchDesc)).alias(levelTerritDesc),
      col(getAlias(L00, BranchCatalog.addressGeolocationId )).alias(BranchCatalog.addressGeolocationId),
      col(getAlias(HIR, HierarchyBranch.level60OperareaId)).alias(HierarchyBranch.level60OperareaId),
      col(getAlias(L60, BranchCatalog.branchDesc)).alias(levelOperDesc)
    ).distinct()
  }

  /** Method for get Retail Bank Type with a given 
   *
   *  @return a new Column with a given 
   */
  def getRetailBankType(): Column = {
    when(
      trim(
        col(getAlias(BRC, HierarchyBranch.level60OperareaId))
      ).isin(
        List(parameter.local.operareaId2664, parameter.local.operareaId2665):_*
      ), lit(parameter.local.retailBankOutTypes.get(0))
    ).otherwise(parameter.local.retailBankOutTypes.get(1))
  }

  /** Method for get Employess Type with a given 
   *
   *  @return a new Column with a given 
   */
  def getEmployessType(): Column = {
    when(
      col(getAlias(HEP, Employee.personalId)).isNull, lit(parameter.local.employeesOutTypes.get(1))
    ).otherwise(
      lit(parameter.local.employeesOutTypes.get(0))
    )
  }

  /** Method for get Manager Name with a given 
   *
   *  @return a new Column with a given 
   */
  def getManagerName(): Column = {
    concat(
      col(getAlias(PBA, MatrixIcOut.lastName)),
      lit(" "),
      col(getAlias(PBA, MatrixIcOut.secondLastName)),
      lit(", "),
      col(getAlias(PBA, MatrixIcOut.firstName))
    )
  }

  /** Method for transform with a given dataReader
   *
   *  @param dataReader
   *  @return a new DataFrame with a given dataReader
   */
  override def transform(dataReader: DataReader): DataFrame = {
    val dfManager = prepareDfManager(dataReader.get("manager"))
    val dfEmployees = prepareDfEmployees(dataReader.get("employees"))
    val dfEmployeesGrouped = prepareDfEmployeesGrouped(dfEmployees)
    val dfBranchCatalog = prepareDfBranchCatalog(dataReader.get("branchCatalog"))
    val dfHierarchyBranch = prepareDfHierarchyBranch(dataReader.get("hierarchyBranch"))
    val dfBranch = prepareDfBranch(dfBranchCatalog, dfHierarchyBranch)
    val dfDataPerson = dataReader.get("dfDataPerson")
    val dfPeopleBoardAggregate = dataReader.get("peopleBoardAggregate")

    dfDataPerson.alias(DP).join(
      dfManager.alias(MNG),
      col(getAlias(MNG, Manager.externalManagerId)) === col(getAlias(DP, MatrixIcOut.localManagerId)),
      "left_outer"
    ).join(
      dfEmployees.alias(MEP),
      col(getAlias(MEP, Employee.managerEmployeeId)) === col(getAlias(MNG, Employee.managerEmployeeId)),
      "left_outer"
    ).join(
      dfPeopleBoardAggregate.alias(PBA),
      (col(getAlias(PBA, PeopleBoardAggregate.personalType)) === col(getAlias(MEP, Employee.personalType)))
        and (col(getAlias(PBA, PeopleBoardAggregate.personalId)) === col(getAlias(MEP, Employee.personalId))),
      "left_outer"
    ).join(
      dfBranch.alias(BRC),
      col(getAlias(BRC, BranchCatalog.branchId)) === col(getAlias(DP, MatrixIcOut.branchId)),
      "left_outer"
    ).join(
      dfEmployeesGrouped.alias(HEP),
      (col(getAlias(DP, MatrixIcOut.personalType)) === col(getAlias(HEP, Employee.personalType)))
        and (col(getAlias(DP, MatrixIcOut.personalId)) === col(getAlias(HEP, Employee.personalId))),
      "left_outer"
    ).select(
      col(getAlias(DP, MatrixIcOut.personalId)),
      col(getAlias(DP, MatrixIcOut.personalType)),
      getRetailBankType().alias(MatrixIcOut.retailBankType),
      col(getAlias(BRC, BranchCatalog.branchDesc)).alias(MatrixIcOut.branchDesc),
      col(getAlias(BRC, HierarchyBranch.level50TerritorialId)).alias(MatrixIcOut.territorialBranchId),
      col(getAlias(BRC, levelTerritDesc)).alias(MatrixIcOut.territorialBranchDesc),
      getManagerName().alias(MatrixIcOut.managerName),
      col(getAlias(MEP, Employee.workPhoneNumberId)).alias(MatrixIcOut.managerPhoneNumberId),
      getEmployessType().alias(MatrixIcOut.employeeType),
      col(getAlias(HEP, Employee.userEmailDesc)).alias(MatrixIcOut.employeeEmailDesc)
    )
  }
}
