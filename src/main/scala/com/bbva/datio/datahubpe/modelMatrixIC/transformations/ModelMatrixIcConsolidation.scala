package com.bbva.datio.datahubpe.modelMatrixIC.transformations


import com.bbva.datio.datahubpe.modelMatrixIC.config.Parameter
import com.bbva.datio.datahubpe.modelMatrixIC.transformations.basicsDataProcess.BasicDataProcessConsolidation
import com.bbva.datio.datahubpe.modelMatrixIC.transformations.contactabilityProcess.ContactabilityProcessConsolidation
import com.bbva.datio.datahubpe.modelMatrixIC.transformations.productProcess.ProductProcessConsolidation
import com.bbva.datio.datahubpe.modelMatrixIC.transformations.rccProcess.RccProcessConsolidation
import com.bbva.datio.datahubpe.utils.processing.data.DataReader
import com.bbva.datio.datahubpe.utils.processing.flow.Transformer
import org.apache.spark.sql.DataFrame

/** Class ModelMatrixIcConsolidation for process data of model Matrix IC.
 *
 *  @constructor create a new ModelMatrixIcConsolidation with parameters: parameter
 *  @param parameter
 */
class ModelMatrixIcConsolidation(parameter: Parameter) extends Transformer[DataReader, DataFrame]{


  /** Method for transform with a given dataReader
   *
   *  @param dataReader
   *  @return a new DataFrame with a given dataReader
   */
  override def transform(dataReader: DataReader): DataFrame = {

    val dfBasicDataProcessConsolidation = new BasicDataProcessConsolidation(parameter).transform(dataReader)
    dfBasicDataProcessConsolidation.checkpoint(true)
    dataReader.add("dfBasicDataProcessConsolidation", dfBasicDataProcessConsolidation)

    val dfProductProcessConsolidation = new ProductProcessConsolidation(parameter).transform(dataReader)
    dfProductProcessConsolidation.checkpoint(true)
    dataReader.add("dfProductProcessConsolidation", dfProductProcessConsolidation)

    val dfContactabilityProcessConsolidation = new ContactabilityProcessConsolidation(parameter).transform(dataReader)
    dfContactabilityProcessConsolidation.checkpoint(true)
    dataReader.add("dfContactabilityProcessConsolidation", dfContactabilityProcessConsolidation)

    val dfRccProcessConsolidation = new RccProcessConsolidation(parameter).transform(dataReader)
    dfRccProcessConsolidation.checkpoint(true)
    dataReader.add("dfRccProcessConsolidation", dfRccProcessConsolidation)

    val dfMatrixCampaignConsolidation = new MatrixCampaignConsolidation(parameter).transform(dataReader)

    dfMatrixCampaignConsolidation
  }
}
