package org.cai.biopanda.plugin

import org.cai.biopanda.extensiontype.FASTAType
import org.cai.biopanda.factory.FASTAFactory
import org.cai.biopanda.functions.FASTAFunctions
import org.cai.biopanda.sequence.FASTA
import org.grapheco.pandadb.plugin.typesystem.ExtensionTypePlugin

/**
 * @author cai584770
 * @date 2024/6/25 13:01
 * @Version
 */
class FASTAPlugin extends ExtensionTypePlugin{
  override def getName: String = "FASTAPlugin"

  override protected def registerAll(): Unit = {
    registerType(FASTAType.instance, classOf[FASTA])
    registerFactory(FASTAFactory)
    registerFunction(classOf[FASTAFunctions])
  }
}
