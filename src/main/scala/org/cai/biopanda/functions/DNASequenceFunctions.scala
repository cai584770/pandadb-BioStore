package org.cai.biopanda.functions

import org.cai.biopanda.factory.DNASequenceFactory
import org.cai.biopanda.sequence.BioSequenceEnum.BioSequenceType
import org.cai.biopanda.sequence.{DNASequence, FASTA}
import org.grapheco.lynx.func.{LynxProcedure, LynxProcedureArgument}
import org.grapheco.lynx.types.composite.LynxList
import org.grapheco.lynx.types.property.LynxString
import org.grapheco.pandadb.plugin.typesystem.TypeFunctions

/**
 * @author cai584770
 * @date 2024/5/21 15:55
 * @Version
 */
class DNASequenceFunctions extends TypeFunctions{

  @LynxProcedure(name = "DNASequence.fromFile")
  def fromFile(@LynxProcedureArgument(name="filePath") path: LynxString): DNASequence = {
    DNASequenceFactory.fromFile(path.value)
  }

  @LynxProcedure(name = "DNASequence.fromBytes")
  def fromBytes(@LynxProcedureArgument(name="bytes") byteList: LynxList): DNASequence = {
    val bytes: Array[Byte] = byteList.value.asInstanceOf[List[Byte]].toArray
    DNASequenceFactory.fromBytes(bytes)
  }

  @LynxProcedure(name = "DNASequence.fromString")
  def fromString(@LynxProcedureArgument(name="string") lynxString: LynxString): DNASequence = {
    DNASequenceFactory.fromString(lynxString.value)
  }

  @LynxProcedure(name = "DNASequence.fromFASTA")
  def fromFASTA(@LynxProcedureArgument(name="fasta") fasta: FASTA): DNASequence = {
    DNASequenceFactory.fromFASTA(fasta)
  }

  @LynxProcedure(name = "DNASequence.length")
  def length(@LynxProcedureArgument(name="dnaSequence") dnaSequence: DNASequence): Long = {
    dnaSequence.getSequence.length
  }

  @LynxProcedure(name = "DNASequence.bioType")
  def bioType(@LynxProcedureArgument(name="dnaSequence") dnaSequence: DNASequence): BioSequenceType = {
    dnaSequence.getBioSequenceType
  }

}
