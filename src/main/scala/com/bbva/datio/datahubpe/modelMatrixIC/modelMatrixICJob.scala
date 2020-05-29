package com.bbva.datio.datahubpe.modelMatrixIC


import com.bbva.datio.datahubpe.modelMatrixIC.transformations.ModelMatrixIcTransformer
import com.bbva.datio.datahubpe.utils.processing.data.{DataReader, DataWriter}
import com.bbva.datio.datahubpe.utils.processing.flow.{FlowInitSpark, Transformer}
import com.typesafe.config.Config


object modelMatrixICJob extends FlowInitSpark {

  /** Method for get Transformer with a given config
   *
   *  @param config
   *  @return a new Transformer[DataReader, DataWriter] with a given config
   */
  override def getTransformer(config: Config): Transformer[DataReader, DataWriter] = new ModelMatrixIcTransformer(config,spark)

}
