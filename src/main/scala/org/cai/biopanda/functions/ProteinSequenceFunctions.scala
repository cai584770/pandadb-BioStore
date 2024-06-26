package org.cai.biopanda.functions

import org.cai.biopanda.factory.ProteinSequenceFactory
import org.cai.biopanda.sequence.BioSequenceEnum.BioSequenceType
import org.cai.biopanda.sequence.{FASTA, ProteinSequence}
import org.grapheco.lynx.func.{LynxProcedure, LynxProcedureArgument}
import org.grapheco.lynx.types.composite.LynxList
import org.grapheco.lynx.types.property.LynxString
import org.grapheco.pandadb.plugin.typesystem.TypeFunctions

/**
 * @author cai584770
 * @date 2024/5/23 9:35
 * @Version
 */
class ProteinSequenceFunctions extends TypeFunctions {

  @LynxProcedure(name = "ProteinSequence.fromFile")
  def fromFile(@LynxProcedureArgument(name = "filePath") path: LynxString): ProteinSequence = {
    ProteinSequenceFactory.fromFile(path.value)
  }

  @LynxProcedure(name = "ProteinSequence.fromBytes")
  def fromBytes(@LynxProcedureArgument(name = "bytes") byteList: LynxList): ProteinSequence = {
    val bytes: Array[Byte] = byteList.value.asInstanceOf[List[Byte]].toArray
    ProteinSequenceFactory.fromBytes(bytes)
  }

  @LynxProcedure(name = "ProteinSequence.fromString")
  def fromString(@LynxProcedureArgument(name = "string") lynxString: LynxString): ProteinSequence = {
    ProteinSequenceFactory.fromString(lynxString.value)
  }

  @LynxProcedure(name = "ProteinSequence.fromFASTA")
  def fromFASTA(@LynxProcedureArgument(name = "fasta") fasta: FASTA): ProteinSequence = {
    ProteinSequenceFactory.fromFASTA(fasta)
  }

  @LynxProcedure(name = "ProteinSequence.length")
  def length(@LynxProcedureArgument(name = "ProteinSequence") proteinSequence: ProteinSequence): Long = {
    proteinSequence.getSequence.length
  }

  @LynxProcedure(name = "ProteinSequence.bioType")
  def bioType(@LynxProcedureArgument(name = "ProteinSequence") proteinSequence: ProteinSequence): BioSequenceType = {
    proteinSequence.getBioSequenceType
  }

}
