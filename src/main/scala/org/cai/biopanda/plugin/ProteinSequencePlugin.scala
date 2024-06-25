package org.cai.biopanda.plugin

import org.cai.biopanda.extensiontype.ProteinSequenceType
import org.cai.biopanda.factory.ProteinSequenceFactory
import org.cai.biopanda.functions.ProteinSequenceFunctions
import org.cai.biopanda.sequence.ProteinSequence
import org.grapheco.pandadb.plugin.typesystem.ExtensionTypePlugin

/**
 * @author cai584770
 * @date 2024/6/25 13:01
 * @Version
 */
class ProteinSequencePlugin extends ExtensionTypePlugin{
  override def getName: String = "ProteinSequencePlugin"

  override protected def registerAll(): Unit = {
    registerType(ProteinSequenceType.instance, classOf[ProteinSequence]);
    registerFactory(ProteinSequenceFactory);
    registerFunction(classOf[ProteinSequenceFunctions])
  }
}
