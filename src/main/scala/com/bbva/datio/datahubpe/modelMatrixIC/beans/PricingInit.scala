package com.bbva.datio.datahubpe.modelMatrixIC.beans

import com.bbva.datio.datahubpe.modelMatrixIC.config.Parameter
import com.bbva.datio.datahubpe.modelMatrixIC.utils.caseUtils
import com.typesafe.config.Config
import org.apache.spark.sql.SparkSession

/** Class PricingInit for process data of model Matrix IC.
 *
 *  @constructor create a new PricingInit with parameters: config and spark
 *  @param config
 *  @param spark
 */
class PricingInit(config: Config, spark: SparkSession){

  val parameter: Parameter =  caseUtils.getParameter(config.getConfig("appJob"))
  this.initCheckpointDirectory()

  /** Method for init Checkpoint Directory with a given 
   *
   *  @return a new Unit with a given 
   */
  def initCheckpointDirectory(): Unit = {

  }

}
