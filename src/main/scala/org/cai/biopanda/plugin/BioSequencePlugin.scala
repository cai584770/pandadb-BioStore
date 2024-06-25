package org.cai.biopanda.plugin

import org.cai.biopanda.extensiontype.BioSequenceType
import org.cai.biopanda.sequence.BioSequence
import org.grapheco.pandadb.plugin.typesystem.ExtensionTypePlugin
import org.cai.biopanda.factory.BioSequenceFactory
import org.cai.biopanda.functions.BioSequenceFunctions

/**
 * @author cai584770
 * @date 2024/6/25 11:14
 * @Version
 */
class BioSequencePlugin extends ExtensionTypePlugin{
  override def getName: String = "BioSequencePlugin"

  override protected def registerAll(): Unit = {
    registerType(BioSequenceType.instance, classOf[BioSequence])
    registerFactory(BioSequenceFactory)
    registerFunction(classOf[BioSequenceFunctions])
  }
}
