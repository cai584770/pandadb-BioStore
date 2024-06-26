package org.cai.biopanda.functions

import org.cai.biopanda.factory.RNASequenceFactory
import org.cai.biopanda.sequence.BioSequenceEnum.BioSequenceType
import org.cai.biopanda.sequence.{FASTA, RNASequence}
import org.grapheco.lynx.func.{LynxProcedure, LynxProcedureArgument}
import org.grapheco.lynx.types.composite.LynxList
import org.grapheco.lynx.types.property.LynxString
import org.grapheco.pandadb.plugin.typesystem.TypeFunctions

/**
 * @author cai584770
 * @date 2024/5/21 16:19
 * @Version
 */
class RNASequenceFunctions extends TypeFunctions {

  @LynxProcedure(name = "RNASequence.fromFile")
  def fromFile(@LynxProcedureArgument(name = "filePath") path: LynxString): RNASequence = {
    RNASequenceFactory.fromFile(path.value)
  }

  @LynxProcedure(name = "RNASequence.fromBytes")
  def fromBytes(@LynxProcedureArgument(name = "bytes") byteList: LynxList): RNASequence = {
    val bytes: Array[Byte] = byteList.value.asInstanceOf[List[Byte]].toArray
    RNASequenceFactory.fromBytes(bytes)
  }

  @LynxProcedure(name = "RNASequence.fromString")
  def fromString(@LynxProcedureArgument(name = "string") lynxString: LynxString): RNASequence = {
    RNASequenceFactory.fromString(lynxString.value)
  }

  @LynxProcedure(name = "RNASequence.fromFASTA")
  def fromFASTA(@LynxProcedureArgument(name = "fasta") fasta: FASTA): RNASequence = {
    RNASequenceFactory.fromFASTA(fasta)
  }

  @LynxProcedure(name = "RNASequence.length")
  def length(@LynxProcedureArgument(name = "RNASequence") rnaSequence: RNASequence): Long = {
    rnaSequence.getSequence.length
  }

  @LynxProcedure(name = "RNASequence.bioType")
  def bioType(@LynxProcedureArgument(name = "RNASequence") rnaSequence: RNASequence): BioSequenceType = {
    rnaSequence.getBioSequenceType
  }

}

