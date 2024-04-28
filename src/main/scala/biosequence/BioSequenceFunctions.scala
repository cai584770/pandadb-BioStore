package biosequence

import org.biojava.nbio.core.alignment.template.SequencePair
import org.biojava.nbio.core.sequence.DNASequence
import org.biojava.nbio.core.sequence.compound.NucleotideCompound
import org.grapheco.lynx.cypherplus.{Blob, DefaultFunctions}
import org.grapheco.lynx.func.LynxProcedure
import org.grapheco.lynx.types.property.LynxString
import search.{Search, SequencePartitioning}
import store.ReStoreSequence

/**
 * @author cai584770
 * @date 2024/4/8 15:32
 * @Version
 */
class BioSequenceFunctions extends DefaultFunctions{

  @LynxProcedure(name = "BioSequence.fromURL")
  override def fromURL(url: LynxString): BioSequence = {
    BioSequence.fromURL(url.value)
  }

  @LynxProcedure(name = "BioSequence.fromFile")
  override def fromFile(filePath: LynxString): BioSequence = {
    BioSequence.fromFile(filePath.value)
  }

  @LynxProcedure(name = "BioSequence.exportSequence")
  def exportBioSequence(bioSequence: BioSequence,filePath:String):Unit={
    BioSequence.exportSequence(bioSequence,filePath)
  }

  @LynxProcedure(name = "BioSequence.EMPTY")
  def empty(): BioSequence = {
    BioSequence.EMPTY
  }

  @LynxProcedure(name = "BioSequence.getSupplyInformation")
  def getSupplyInformation(bioSequence: BioSequence): Map[String, List[(Any, Any)]] = {
    bioSequence.supplyInformation
  }

  @LynxProcedure(name = "BioSequence.getSupplyInformation")
  def length(bioSequence: BioSequence): Long = {
    bioSequence.length
  }

  @LynxProcedure(name = "BioSequence.getInformation")
  def getInformation(bioSequence: BioSequence): String = {
    bioSequence.information
  }

  @LynxProcedure(name = "BioSequence.getLowerCasePosition")
  def getLowerCasePosition(bioSequence: BioSequence): List[(Int, Int)] = {
    val supplementaryInformation = bioSequence.supplyInformation

    supplementaryInformation.getOrElse("LowerCasePosition", List.empty) map {
      case (a: Int, b: Int) => (a, b)
      case _ => throw new RuntimeException("Invalid tuple elements")
    }
  }

  @LynxProcedure(name = "BioSequence.getNCasePosition")
  def getNCasePosition(bioSequence: BioSequence): List[(Int, Int)] = {
    val supplementaryInformation = bioSequence.supplyInformation

    supplementaryInformation.getOrElse("NCasePosition", List.empty) map {
      case (a: Int, b: Int) => (a, b)
      case _ => throw new RuntimeException("Invalid tuple elements")
    }
  }

  @LynxProcedure(name = "BioSequence.getNonAGCTCasePosition")
  def getNonAGCTCasePosition(bioSequence: BioSequence): List[(Int, String)] = {
    val supplementaryInformation = bioSequence.supplyInformation

    supplementaryInformation.getOrElse("OtherCaseList", List.empty) map {
      case (a: Int, b: String) => (a, b)
      case _ => throw new RuntimeException("Invalid tuple elements")
    }

  }

  @LynxProcedure(name = "BioSequence.getAGCTCaseLength", description ="the first value is sequence length, the second value is AGCT sequence length")
  def getAGCTCaseLength(bioSequence: BioSequence): List[(Int, Int)] = {
    val supplementaryInformation = bioSequence.supplyInformation

    supplementaryInformation.getOrElse("Length", List.empty) map {
      case (a: Int, b: Int) => (a, b)
      case _ => throw new RuntimeException("Invalid tuple elements")
    }

  }

  @LynxProcedure(name = "BioSequence.searchOne")
  def searchOne(bioSequence: BioSequence,query:String): SequencePair[DNASequence, NucleotideCompound] = {
    val supplyInformation = bioSequence.supplyInformation
    val sequenceArrayByte = bioSequence.streamSource.offerStream(inputStream => StreamUtils.inputStreamToByteArray(inputStream))

    val sequence = ReStoreSequence.from2bit(sequenceArrayByte, supplyInformation)
    val targetArray = SequencePartitioning.partition(sequence,query.length)

    val psa = Search.searchOne(query, targetArray)

    psa
  }

  @LynxProcedure(name = "BioSequence.searchAll")
  def searchAll(bioSequence: BioSequence, query: String): List[SequencePair[DNASequence, NucleotideCompound]] = {
    val supplyInformation = bioSequence.supplyInformation
    val sequenceArrayByte = bioSequence.streamSource.offerStream(inputStream => StreamUtils.inputStreamToByteArray(inputStream))

    val sequence = ReStoreSequence.from2bit(sequenceArrayByte, supplyInformation)
    val targetArray = SequencePartitioning.partition(sequence, query.length)

    val psa = Search.searchAll(query, targetArray)

    psa
  }

}
