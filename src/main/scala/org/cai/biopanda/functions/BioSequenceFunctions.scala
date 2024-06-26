package org.cai.biopanda.functions

import org.cai.biopanda.factory.BioSequenceFactory
import org.cai.biopanda.sequence.{BioSequence, FASTA}
import org.grapheco.lynx.func.{LynxProcedure, LynxProcedureArgument}
import org.grapheco.lynx.types.composite.LynxList
import org.grapheco.lynx.types.property.LynxString
import org.grapheco.pandadb.plugin.typesystem.TypeFunctions

/**
 * @author cai584770
 * @date 2024/6/25 17:56
 * @Version
 */
class BioSequenceFunctions extends TypeFunctions{

  @LynxProcedure(name = "BioSequence.fromFile")
  def fromFile(@LynxProcedureArgument(name = "filePath") path: LynxString): BioSequence = {
    BioSequenceFactory.fromFile(path.value)
  }

  @LynxProcedure(name = "BioSequence.fromBytes")
  def fromBytes(@LynxProcedureArgument(name = "bytes") byteList: LynxList): BioSequence = {
    val bytes: Array[Byte] = byteList.value.asInstanceOf[List[Byte]].toArray
    BioSequenceFactory.fromBytes(bytes)
  }

  @LynxProcedure(name = "BioSequence.fromString")
  def fromString(@LynxProcedureArgument(name = "string") lynxString: LynxString): BioSequence = {
    BioSequenceFactory.fromString(lynxString.value)
  }

  @LynxProcedure(name = "BioSequence.fromFASTA")
  def fromFASTA(@LynxProcedureArgument(name = "fasta") fasta: FASTA): BioSequence = {
    BioSequenceFactory.fromFASTA(fasta)
  }

  @LynxProcedure(name = "BioSequence.length")
  def length(@LynxProcedureArgument(name = "BioSequence") bioSequence: BioSequence): Long = {
    bioSequence.getSequence.length
  }

}
