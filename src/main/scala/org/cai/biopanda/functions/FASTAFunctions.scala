package org.cai.biopanda.functions

import org.biojava.nbio.core.alignment.template.SequencePair
import org.biojava.nbio.core.sequence.compound.NucleotideCompound
import org.grapheco.lynx.func.LynxProcedure
import org.grapheco.lynx.types.property.LynxString
import org.grapheco.pandadb.plugin.annotations.UDFCollection
import org.grapheco.pandadb.plugin.typesystem.TypeFunctions

/**
 * @author cai584770
 * @date 2024/6/19 10:05
 * @Version
 */
class FASTAFunctions extends TypeFunctions {

  @LynxProcedure(name = "FASTA.fromFile")
  def fromFile(filePath: LynxString): FASTA = {
    FASTA.fromFile(filePath.value)
  }

  @LynxProcedure(name = "FASTA.export")
  def export(fasta: FASTA, filePath: String): Unit = {
    FASTA.export(fasta, filePath)
  }

  @LynxProcedure(name = "FASTA.getSupplyInformation")
  def getSupplyInformation(fasta: FASTA): Map[String, List[(Any, Any)]] = {
    fasta.supplyInformation.getOrElse(Map.empty)
  }

  @LynxProcedure(name = "FASTA.length")
  def length(fasta: FASTA): Long = {
    val lengthList = fasta.supplyInformation
      .flatMap(_.get("Length"))
      .collect {
        case list: List[(Int, Int)] if list.nonEmpty => list
      }
      .getOrElse(throw new RuntimeException("Length information is missing or invalid"))

    val (sequenceLength, agctSequenceLength) = lengthList.head

    sequenceLength.toLong
  }

  @LynxProcedure(name = "FASTA.getInformation")
  def getInformation(fasta: FASTA): String = {
    fasta.information
  }

  @LynxProcedure(name = "FASTA.getLowerCasePosition")
  def getLowerCasePosition(fasta: FASTA): List[(Int, Int)] = {
    fasta.supplyInformation
      .flatMap(_.get("LowerCasePosition"))
      .collect {
        case list: List[(Int, Int)] if list.nonEmpty => list
      }
      .getOrElse(throw new RuntimeException("Lower Case Position is missing or invalid"))


  }

  @LynxProcedure(name = "FASTA.getNCasePosition")
  def getNCasePosition(fasta: FASTA): List[(Int, Int)] = {
    fasta.supplyInformation
      .flatMap(_.get("NCasePosition"))
      .collect {
        case list: List[(Int, Int)] if list.nonEmpty => list
      }
      .getOrElse(throw new RuntimeException("N Case Position is missing or invalid"))

  }

  @LynxProcedure(name = "FASTA.getNonAGCTCasePosition")
  def getNonAGCTCasePosition(fasta: FASTA): List[(Int, String)] = {
    fasta.supplyInformation
      .flatMap(_.get("OtherCaseList"))
      .collect {
        case list: List[(Int, String)] if list.nonEmpty => list
      }
      .getOrElse(throw new RuntimeException("Other Case is missing or invalid"))


  }


  @LynxProcedure(name = "FASTA.local")
  def local(query: String, fasta: FASTA): SequencePair[org.biojava.nbio.core.sequence.DNASequence, NucleotideCompound] = {

    Align.local(query, fasta)

  }

  @LynxProcedure(name = "FASTA.global")
  def global(query: String, fasta: FASTA): SequencePair[org.biojava.nbio.core.sequence.DNASequence, NucleotideCompound] = {

    Align.global(query, fasta)
  }

}
