package com.bbva.datio.datahubpe.modelMatrixIC.utils

import com.bbva.datio.datahubpe.modelMatrixIC.config.Parameter
import com.bbva.datio.datahubpe.modelMatrixIC.utils.caseUtils.getParameter
import com.bbva.datio.datahubpe.utils.commons.HdfsUtil
import com.bbva.datio.datahubpe.utils.processing.data.DataReader
import com.bbva.datio.datahubpe.utils.processing.flow.Reader
import com.bbva.datio.datahubpe.utils.processing.flow.impl.InputKeyConfigReader
import com.datio.kirby.CheckFlow
import com.datio.kirby.config.InputFactory
import com.typesafe.config.{Config, ConfigFactory}
import org.apache.spark.sql.SparkSession


/** Class PricingConcreteDataReader for process data of model Matrix IC.
 *
 *  @constructor create a new PricingConcreteDataReader with parameters: spark and config
 *  @param spark
 *  @param config
 */
class PricingConcreteDataReader(spark: SparkSession, config: Config) extends Reader[DataReader]
                                                                        with InputFactory
                                                                        with CheckFlow {
  private val dataReader: DataReader = new DataReader()
  private val LastProcessDate: String = "last_process_date"
  private val lastProcessToday: String = "last_today"

  /** Method for read with a given 
   *
   *  @return a new DataReader with a given 
   */
  override def read(): DataReader = {

    val parameter: Parameter = getParameter(config.getConfig("appJob"))

    val inputConfigReader = new InputKeyConfigReader(config)
    val keys = inputConfigReader.getKeys()

    keys.foreach(key => {
      val configInput = parseConfig(config.getConfig(inputConfigReader.path + "." + key), parameter)

      val df = readDataFrame(readInput(configInput))(spark)
      dataReader.add(key, df)
    })

    dataReader
  }

  /** Method for parse Config with a given configInput and parameter
   *
   *  @param configInput
   *  @param parameter
   *  @return a new Config with a given configInput and parameter
   */
  def parseConfig(configInput: Config, parameter: Parameter): Config ={
    val defaultPartitionName = "cutoff_date"
    val path: String = configInput.getStringList("paths").get(0)
    var partition: String = ""

    if(configInput.hasPath(defaultPartitionName)){
      partition = configInput.getString(defaultPartitionName)
    }

    if (partition == this.LastProcessDate) {
      partition = HdfsUtil.getLastPartition(spark, path, defaultPartitionName, parameter.processDate)
      reBuildConfig(partition, defaultPartitionName, path, configInput)
    } else if(partition == this.lastProcessToday){
      partition = HdfsUtil.getLastPartition(spark, path, defaultPartitionName, parameter.processToday)
      reBuildConfig(partition, defaultPartitionName, path, configInput)
    } else{
      configInput
    }

  }

  /** Method for re Build Config with a given partition and defaultPartitionName and path and configInput
   *
   *  @param partition
   *  @param defaultPartitionName
   *  @param path
   *  @param configInput
   *  @return a new Config with a given partition and defaultPartitionName and path and configInput
   */
  def reBuildConfig(partition: String, defaultPartitionName: String, path: String, configInput: Config): Config = {
    val partitionValue = defaultPartitionName + "=" + partition
    val partitionPath = defaultPartitionName + "\"=\"" + partition
    var tempConfig = ConfigFactory.parseString(partitionValue).withFallback(configInput)
    tempConfig = ConfigFactory.parseString("paths=[" + path + "/" + partitionPath + "]").withFallback(tempConfig)
    tempConfig
  }

}
