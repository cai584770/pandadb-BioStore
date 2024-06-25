package org.cai.biopanda.functions

import org.grapheco.lynx.func.LynxProcedure
import org.grapheco.lynx.types.property.LynxString
import org.grapheco.pandadb.plugin.annotations.UDFCollection
import org.grapheco.pandadb.plugin.typesystem.TypeFunctions

/**
 * @author cai584770
 * @date 2024/5/21 16:19
 * @Version
 */
class RNASequenceFunctions extends TypeFunctions {

  @LynxProcedure(name = "RNASequence.fromFile")
  def fromFile(filePath: LynxString): RNASequence = {
    RNASequence.fromFile(filePath.value)
  }


  @LynxProcedure(name = "RNASequence.getSupplyInformation")
  def length(rnaSequence: RNASequence): Long = {
    rnaSequence.seq.length
  }


}

