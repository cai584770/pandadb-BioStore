package functions

import align.Align
import genesequence.RNASequence
import org.biojava.nbio.core.alignment.template.SequencePair
import org.biojava.nbio.core.sequence.compound.NucleotideCompound
import org.grapheco.lynx.cypherplus.DefaultFunctions
import org.grapheco.lynx.func.LynxProcedure
import org.grapheco.lynx.types.property.LynxString

/**
 * @author cai584770
 * @date 2024/5/21 16:19
 * @Version
 */
class RNASequenceFunction extends DefaultFunctions{

  @LynxProcedure(name = "RNASequence.fromURL")
  override def fromURL(url: LynxString): RNASequence = {
    RNASequence.fromURL(url.value)
  }

  @LynxProcedure(name = "RNASequence.fromFile")
  override def fromFile(filePath: LynxString): RNASequence = {
    RNASequence.fromFile(filePath.value)
  }

  @LynxProcedure(name = "RNASequence.export")
  def export(rnaSequence: RNASequence,filePath:String):Unit={
    RNASequence.export(rnaSequence,filePath)
  }

  @LynxProcedure(name = "RNASequence.EMPTY")
  def empty(): RNASequence = {
    RNASequence.EMPTY
  }

  @LynxProcedure(name = "RNASequence.getSupplyInformation")
  def getSupplyInformation(rnaSequence: RNASequence): Map[String, List[(Any, Any)]] = {
    rnaSequence.supplyInformation
  }

  @LynxProcedure(name = "RNASequence.getSupplyInformation")
  def length(rnaSequence: RNASequence): Long = {
    rnaSequence.length
  }

  @LynxProcedure(name = "RNASequence.getInformation")
  def getInformation(rnaSequence: RNASequence): String = {
    rnaSequence.information
  }

  @LynxProcedure(name = "RNASequence.getLowerCasePosition")
  def getLowerCasePosition(rnaSequence: RNASequence): List[(Int, Int)] = {
    val supplementaryInformation = rnaSequence.supplyInformation

    supplementaryInformation.getOrElse("LowerCasePosition", List.empty) map {
      case (a: Int, b: Int) => (a, b)
      case _ => throw new RuntimeException("Invalid tuple elements")
    }
  }

  @LynxProcedure(name = "RNASequence.getNCasePosition")
  def getNCasePosition(rnaSequence: RNASequence): List[(Int, Int)] = {
    val supplementaryInformation = rnaSequence.supplyInformation

    supplementaryInformation.getOrElse("NCasePosition", List.empty) map {
      case (a: Int, b: Int) => (a, b)
      case _ => throw new RuntimeException("Invalid tuple elements")
    }
  }

  @LynxProcedure(name = "RNASequence.getNonAGCTCasePosition")
  def getNonAGCTCasePosition(rnaSequence: RNASequence): List[(Int, String)] = {
    val supplementaryInformation = rnaSequence.supplyInformation

    supplementaryInformation.getOrElse("OtherCaseList", List.empty) map {
      case (a: Int, b: String) => (a, b)
      case _ => throw new RuntimeException("Invalid tuple elements")
    }

  }

  @LynxProcedure(name = "RNASequence.getAGCTCaseLength")
  def getAGCTCaseLength(rnaSequence: RNASequence): List[(Int, Int)] = {
    val supplementaryInformation = rnaSequence.supplyInformation

    supplementaryInformation.getOrElse("Length", List.empty) map {
      case (a: Int, b: Int) => (a, b)
      case _ => throw new RuntimeException("Invalid tuple elements")
    }

  }


}

