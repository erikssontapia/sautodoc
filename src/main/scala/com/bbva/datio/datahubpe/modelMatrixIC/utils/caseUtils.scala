package com.bbva.datio.datahubpe.modelMatrixIC.utils

import java.sql.Date
import java.text.SimpleDateFormat
import java.util.Calendar

import com.bbva.datio.datahubpe.modelMatrixIC.config.Parameter
import com.bbva.datio.datahubpe.utils.processing.data.DataReader
import com.typesafe.config.{Config, ConfigRenderOptions}
import com.google.gson.Gson
import org.apache.spark.sql.SparkSession


object caseUtils {

  /** Method for get Parameter with a given config
   *
   *  @param config
   *  @return a new Parameter with a given config
   */
  def getParameter(config: Config): Parameter = {

    val parameterJson: String = config.root().render(ConfigRenderOptions.concise())
    new Gson().fromJson(parameterJson, classOf[Parameter])
  }

  /** Method for get Alias with a given alias and columnName
   *
   *  @param alias
   *  @param columnName
   *  @return a new String with a given alias and columnName
   */
  def getAlias(alias: String,  columnName: String): String = {
    alias + "." + columnName
  }

  /** Method for get All Colums Alias with a given alias
   *
   *  @param alias
   *  @return a new String with a given alias
   */
  def getAllColumsAlias(alias: String): String = {
    alias + ".*"
  }

  /** Method for get Last Partition Data Reader with a given spark and config
   *
   *  @param spark
   *  @param config
   *  @return a new DataReader with a given spark and config
   */
  def getLastPartitionDataReader(spark: SparkSession, config: Config): DataReader ={
    new PricingConcreteDataReader(spark, config).read()
  }
  // root() : Gets the Config as a tree of ConfigObject.
  // render() : Renders the config value as a HOCON string.
  // ConfigRenderOptions.concise() : Returns concise render options (no whitespace or comments).

  /** Method for null To Empty String with a given value
   *
   *  @param value
   *  @return a new String with a given value
   */
  def nullToEmptyString( value : String) :String = { if (value == null) "" else value }

  /** Method for get Prev Month Last Date with a given processPeriod
   *
   *  @param processPeriod
   *  @return a new Date with a given processPeriod
   */
  def getPrevMonthLastDate(processPeriod: String): Date = {
    var calendar = Calendar.getInstance()
    calendar.setTime(new SimpleDateFormat("yyyyMM").parse(processPeriod))
    calendar.add(Calendar.MONTH, -1)
    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
    new Date(calendar.getTimeInMillis)
  }

  /** Method for init Temp Dir with a given parameter and spark
   *
   *  @param parameter
   *  @param spark
   *  @return a new Unit with a given parameter and spark
   */
  def initTempDir(parameter: Parameter, spark: SparkSession): Unit = {
    spark.sparkContext.setCheckpointDir(parameter.tempDir)
  }
}
