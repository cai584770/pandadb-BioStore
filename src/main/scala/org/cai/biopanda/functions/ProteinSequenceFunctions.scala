package org.cai.biopanda.functions

import org.grapheco.lynx.cypherplus.DefaultFunctions
import org.grapheco.lynx.func.LynxProcedure
import org.grapheco.lynx.types.property.LynxString
import org.grapheco.pandadb.plugin.annotations.UDFCollection
import org.grapheco.pandadb.plugin.typesystem.TypeFunctions

/**
 * @author cai584770
 * @date 2024/5/23 9:35
 * @Version
 */
class ProteinSequenceFunctions extends TypeFunctions {


  @LynxProcedure(name = "ProteinSequence.fromFile")
  def fromFile(filePath: LynxString): ProteinSequence = {
    ProteinSequence.fromFile(filePath.value)
  }


  @LynxProcedure(name = "ProteinSequence.length")
  def length(proteinSequence: ProteinSequence): Long = {
    proteinSequence.seq.length
  }
}
