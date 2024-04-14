package store

import fileprocess.FileNormalize.remove
import fileprocess.FileProcess
import org.junit.jupiter.api.Test
import store.ReStoreSequence.from2bit
import store.StoreSequence.to2bit

/**
 * @author cai584770
 * @date 2024/4/8 10:05
 * @Version
 */
class ReStoreSequenceTest {

  val sequence = "NNNNNNNNNNNNNNNNNNNNNNNNATCCCTAAATCTTTAAATCCTACANCCATGAATCCCTAAATACCTAATNCCCTAANCCCGAAAAATCTTTAAATCCTnnnnnAATCATTGTGTATATAATNATAATTTTATCNTTTTTATGNAATTGCTAATCTTTAAATCCTACAATCTTTAAATCCTACTATTGTTGNGTGTAGNTTTTTTAAAAATATCATTTNAGGTCAATACAAATCCTATAATCTTTAAATCCTAC"
  val sequence1 = "aaAAbbBBccCCNNnnGGggTTttNNNNNNNNnnnnnnnGGGGGGGGGGpppppp"
  val TAIR10_chr1 = "D:/GithubRepository/biosequence/src/test/biosequence/data/TAIR10/TAIR10_chr1.fas"
  val TAIR10_chr2 = "D:/GithubRepository/biosequence/src/test/biosequence/data/TAIR10/TAIR10_chr2.fas"
  val TAIR10_chr3 = "D:/GithubRepository/biosequence/src/test/biosequence/data/TAIR10/TAIR10_chr3.fas"
  val TAIR10_chr4 = "D:/GithubRepository/biosequence/src/test/biosequence/data/TAIR10/TAIR10_chr4.fas"
  val TAIR10_chr5 = "D:/GithubRepository/biosequence/src/test/biosequence/data/TAIR10/TAIR10_chr5.fas"

  @Test
  def test1(): Unit = {
    val inputString = sequence
    val (supplyInformation, to2bit0) = to2bit(inputString)
    println(supplyInformation)
    println(to2bit0)

    val sequenceFromByteArray = from2bit(to2bit0,supplyInformation)

    println(sequenceFromByteArray)

    var s1 = remove(sequenceFromByteArray)
    println(s1)

    println(s1.equals(inputString))

  }


  @Test
  def test2(): Unit = {
    var startTime = System.nanoTime()
    val (information, sequence) = FileProcess.getInformationAndSequence(TAIR10_chr1)
    var endTime = System.nanoTime()
    var durationMs = (endTime - startTime) / 1000000.0
    println(s"getInformationAndSequence runtime：$durationMs ms")

    println(sequence.length)

    startTime = System.nanoTime()
    val (supplyInformation, to2bit0) = to2bit(sequence)
    endTime = System.nanoTime()
    durationMs = (endTime - startTime) / 1000000.0
    println(s"to2bit runtime：$durationMs ms")

    startTime = System.nanoTime()
    val sequence1 = from2bit(to2bit0, supplyInformation)
    endTime = System.nanoTime()
    durationMs = (endTime - startTime) / 1000000.0
    println(s"to2bit runtime：$durationMs ms")
    println(remove(sequence1).length)

    println(sequence.equals(remove(sequence1)))

  }

  @Test
  def test3(): Unit = {
    val (information, sequence) = FileProcess.getInformationAndSequence(TAIR10_chr1)

    var startTime = System.nanoTime()
    val (supplyInformation, to2bit0) = to2bit(sequence)
    var endTime = System.nanoTime()
    var durationMs = (endTime - startTime) / 1000000.0
    println(s"getInformationAndSequence runtime：$durationMs ms")

    startTime = System.nanoTime()
    val sequence1 = from2bit(to2bit0, supplyInformation)

    endTime = System.nanoTime()
    durationMs = (endTime - startTime) / 1000000.0
    println(s"to2bit runtime：$durationMs ms")


    println(sequence.equals(remove(sequence1)))

  }


}
