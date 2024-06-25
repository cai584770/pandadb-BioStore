package org.cai.biopanda.plugin

import org.cai.biopanda.extensiontype.RNASequenceType
import org.cai.biopanda.factory.RNASequenceFactory
import org.cai.biopanda.functions.RNASequenceFunctions
import org.cai.biopanda.sequence.RNASequence
import org.grapheco.pandadb.plugin.typesystem.ExtensionTypePlugin

/**
 * @author cai584770
 * @date 2024/6/25 13:00
 * @Version
 */
class RNASequencePlugin extends ExtensionTypePlugin{
  override def getName: String = "RNASequencePlugin"

  override protected def registerAll(): Unit = {
    registerType(RNASequenceType.instance, classOf[RNASequence]);
    registerFactory(RNASequenceFactory);
    registerFunction(classOf[RNASequenceFunctions])
  }
}