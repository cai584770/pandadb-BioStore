package dnaSequence

import align.Align
import biosequence.DNASequence
import org.biojava.nbio.core.alignment.template.SequencePair
import org.biojava.nbio.core.sequence.compound.NucleotideCompound
import org.grapheco.lynx.cypherplus.DefaultFunctions
import org.grapheco.lynx.func.LynxProcedure
import org.grapheco.lynx.types.property.LynxString
import search.{Search, SequencePartitioning}
import serialize.StreamUtils
import store.ReStoreSequence

/**
 * @author cai584770
 * @date 2024/5/21 15:55
 * @Version
 */
class DNASequenceFunction extends DefaultFunctions{

  @LynxProcedure(name = "DNASequence.fromURL")
  override def fromURL(url: LynxString): DNASequence = {
    DNASequence.fromURL(url.value)
  }

  @LynxProcedure(name = "DNASequence.fromFile")
  override def fromFile(filePath: LynxString): DNASequence = {
    DNASequence.fromFile(filePath.value)
  }

  @LynxProcedure(name = "DNASequence.export")
  def export(dnaSequence: DNASequence,filePath:String):Unit={
    DNASequence.export(dnaSequence,filePath)
  }

  @LynxProcedure(name = "DNASequence.EMPTY")
  def empty(): DNASequence = {
    DNASequence.EMPTY
  }

  @LynxProcedure(name = "DNASequence.getSupplyInformation")
  def getSupplyInformation(dnaSequence: DNASequence): Map[String, List[(Any, Any)]] = {
    dnaSequence.supplyInformation
  }

  @LynxProcedure(name = "DNASequence.getSupplyInformation")
  def length(dnaSequence: DNASequence): Long = {
    dnaSequence.length
  }

  @LynxProcedure(name = "DNASequence.getInformation")
  def getInformation(dnaSequence: DNASequence): String = {
    dnaSequence.information
  }

  @LynxProcedure(name = "DNASequence.getLowerCasePosition")
  def getLowerCasePosition(dnaSequence: DNASequence): List[(Int, Int)] = {
    val supplementaryInformation = dnaSequence.supplyInformation

    supplementaryInformation.getOrElse("LowerCasePosition", List.empty) map {
      case (a: Int, b: Int) => (a, b)
      case _ => throw new RuntimeException("Invalid tuple elements")
    }
  }

  @LynxProcedure(name = "DNASequence.getNCasePosition")
  def getNCasePosition(dnaSequence: DNASequence): List[(Int, Int)] = {
    val supplementaryInformation = dnaSequence.supplyInformation

    supplementaryInformation.getOrElse("NCasePosition", List.empty) map {
      case (a: Int, b: Int) => (a, b)
      case _ => throw new RuntimeException("Invalid tuple elements")
    }
  }

  @LynxProcedure(name = "DNASequence.getNonAGCTCasePosition")
  def getNonAGCTCasePosition(dnaSequence: DNASequence): List[(Int, String)] = {
    val supplementaryInformation = dnaSequence.supplyInformation

    supplementaryInformation.getOrElse("OtherCaseList", List.empty) map {
      case (a: Int, b: String) => (a, b)
      case _ => throw new RuntimeException("Invalid tuple elements")
    }

  }

  @LynxProcedure(name = "DNASequence.getAGCTCaseLength")
  def getAGCTCaseLength(dnaSequence: DNASequence): List[(Int, Int)] = {
    val supplementaryInformation = dnaSequence.supplyInformation

    supplementaryInformation.getOrElse("Length", List.empty) map {
      case (a: Int, b: Int) => (a, b)
      case _ => throw new RuntimeException("Invalid tuple elements")
    }

  }

  @LynxProcedure(name = "DNASequence.local")
  def local(query:String,dnaSequence: DNASequence): SequencePair[org.biojava.nbio.core.sequence.DNASequence, NucleotideCompound] = {

    Align.local(query, dnaSequence)

  }

  @LynxProcedure(name = "DNASequence.global")
  def global(dnaSequence: DNASequence, query: String): SequencePair[org.biojava.nbio.core.sequence.DNASequence, NucleotideCompound] = {

    Align.global(query, dnaSequence)
  }

}
