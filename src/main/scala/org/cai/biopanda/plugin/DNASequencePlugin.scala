package org.cai.biopanda.plugin

import org.cai.biopanda.extensiontype.DNASequenceType
import org.cai.biopanda.factory.DNASequenceFactory
import org.cai.biopanda.functions.DNASequenceFunctions
import org.cai.biopanda.sequence.DNASequence
import org.grapheco.pandadb.plugin.typesystem.ExtensionTypePlugin


/**
 * @author cai584770
 * @date 2024/6/25 12:58
 * @Version
 */
class DNASequencePlugin extends ExtensionTypePlugin{

  override def getName: String = "BlobPlugin"

  override protected def registerAll(): Unit = {
    registerType(DNASequenceType.instance, classOf[DNASequence]);
    registerFactory(DNASequenceFactory)
    registerFunction(classOf[DNASequenceFunctions])
  }

}