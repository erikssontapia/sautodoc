package com.bbva.datio.datahubpe.modelMatrixIC.transformations

import com.bbva.datio.datahubpe.modelMatrixIC.utils.caseUtils.{getLastPartitionDataReader, getParameter, initTempDir}
import com.bbva.datio.datahubpe.utils.processing.data.{DataReader, DataWriter}
import com.bbva.datio.datahubpe.utils.processing.flow.Transformer
import com.typesafe.config.Config
import org.apache.spark.sql.SparkSession

/** Class ModelMatrixIcTransformer for process data of model Matrix IC.
 *
 *  @constructor create a new ModelMatrixIcTransformer with parameters: config and spark
 *  @param config
 *  @param spark
 */
class ModelMatrixIcTransformer(config: Config, spark: SparkSession) extends Transformer[DataReader, DataWriter] {

  /** Method for transform with a given dataReader
   *
   *  @param dataReader
   *  @return a new DataWriter with a given dataReader
   */
  override def transform(dataReader: DataReader): DataWriter = {
    val dataWriter = new DataWriter()

    val lastPartitionDataReader = getLastPartitionDataReader(spark, config)
    val parameter = getParameter(config.getConfig("appJob"))
    initTempDir(parameter, spark)

    val dfModelMatrixIcConsolidation = new ModelMatrixIcConsolidation(parameter).transform(lastPartitionDataReader)

    dataWriter.add("modelMatrixIc", dfModelMatrixIcConsolidation, false)
    dataWriter
  }
}
