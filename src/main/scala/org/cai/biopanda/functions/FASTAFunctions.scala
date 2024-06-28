package org.cai.biopanda.functions

import org.biojava.nbio.core.alignment.template.SequencePair
import org.biojava.nbio.core.sequence.compound.NucleotideCompound
import org.cai.align.Align
import org.cai.biopanda.factory.FASTAFactory
import org.cai.biopanda.sequence.FASTA
import org.grapheco.lynx.func.{LynxProcedure, LynxProcedureArgument}
import org.grapheco.lynx.types.property.LynxString
import org.grapheco.pandadb.plugin.typesystem.TypeFunctions

/**
 * @author cai584770
 * @date 2024/6/19 10:05
 * @Version
 */
class FASTAFunctions extends TypeFunctions {

  @LynxProcedure(name = "FASTA.fromFile")
  def fromFile(@LynxProcedureArgument(name = "filePath") filePath: LynxString): FASTA = {
    FASTAFactory.fromFile(filePath.value)
  }

  @LynxProcedure(name = "FASTA.export")
  def export(@LynxProcedureArgument(name = "fasta") fasta: FASTA, @LynxProcedureArgument(name = "filePath") filePath: LynxString): Unit = {
    FASTAFactory.export(fasta, filePath.value)
  }

  @LynxProcedure(name = "FASTA.getSupplyInformation")
  def getSupplyInformation(@LynxProcedureArgument(name = "fasta") fasta: FASTA): Map[String, List[(Any, Any)]] = {
    fasta.getSupplyInformation.getOrElse(Map.empty)
  }

  @LynxProcedure(name = "FASTA.length")
  def length(@LynxProcedureArgument(name = "fasta") fasta: FASTA): Long = {
    val lengthList = fasta.getSupplyInformation
      .flatMap(_.get("Length"))
      .collect {
        case list: List[(Int, Int)] if list.nonEmpty => list
      }
      .getOrElse(throw new RuntimeException("Length information is missing or invalid"))

    val (sequenceLength, agctSequenceLength) = lengthList.head

    sequenceLength.toLong
  }

  @LynxProcedure(name = "FASTA.getInformation")
  def getInformation(@LynxProcedureArgument(name = "fasta") fasta: FASTA): String = {
    fasta.getInformation
  }

  @LynxProcedure(name = "FASTA.getLowerCasePosition")
  def getLowerCasePosition(@LynxProcedureArgument(name = "fasta") fasta: FASTA): List[(Int, Int)] = {
    fasta.getSupplyInformation
      .flatMap(_.get("LowerCasePosition"))
      .collect {
        case list: List[(Int, Int)] if list.nonEmpty => list
      }
      .getOrElse(throw new RuntimeException("Lower Case Position is missing or invalid"))


  }

  @LynxProcedure(name = "FASTA.getNCasePosition")
  def getNCasePosition(@LynxProcedureArgument(name = "fasta") fasta: FASTA): List[(Int, Int)] = {
    fasta.getSupplyInformation
      .flatMap(_.get("NCasePosition"))
      .collect {
        case list: List[(Int, Int)] if list.nonEmpty => list
      }
      .getOrElse(throw new RuntimeException("N Case Position is missing or invalid"))

  }

  @LynxProcedure(name = "FASTA.getNonAGCTCasePosition")
  def getNonAGCTCasePosition(@LynxProcedureArgument(name = "fasta") fasta: FASTA): List[(Int, String)] = {
    fasta.getSupplyInformation
      .flatMap(_.get("OtherCaseList"))
      .collect {
        case list: List[(Int, String)] if list.nonEmpty => list
      }
      .getOrElse(throw new RuntimeException("Other Case is missing or invalid"))


  }


  @LynxProcedure(name = "FASTA.local")
  def local(@LynxProcedureArgument(name = "query") query: String, @LynxProcedureArgument(name = "fasta") fasta: FASTA): SequencePair[org.biojava.nbio.core.sequence.DNASequence, NucleotideCompound] = {

    Align.local(query, fasta)

  }

  @LynxProcedure(name = "FASTA.global")
  def global(@LynxProcedureArgument(name = "query") query: String, @LynxProcedureArgument(name = "fasta") fasta: FASTA): SequencePair[org.biojava.nbio.core.sequence.DNASequence, NucleotideCompound] = {

    Align.global(query, fasta)
  }

}
