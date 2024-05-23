package search

import file.FileProcess.getInformationAndSequence
import org.junit.Test
import store.StoreSequence.to2bit

import java.util
/**
 * @author cai584770
 * @date 2024/4/26 15:06
 * @Version
 */
class SearchTest {

  val querySeq = "ACGAGTGCGTGTTTTCCCGCCTGGTCCCCAGGCCCCCTTTCCGTCCTCAGGAA" +
    "GACAGAGGAGGAGCCCCTCGGGCTGCAGGTGGTGGGCGTTGCGGCGGCGGCCGGTTAAGGT" +
    "TCCCAGTGCCCGCACCCGGCCCACGGGAGCCCCGGACTGGCGGCGTCACTGTCAGTGTCTT" +
    "CTCAGGAGGCCGCCTGTGTGACTGGATCGTTCGTGTCCCCACAGCACGTTTCTTGGAGTAC" +
    "TCTACGTCTGAGTGTCATTTCTTCAATGGGACGGAGCGGGTGCGGTTCCTGGACAGATACT" +
    "TCCATAACCAGGAGGAGAACGTGCGCTTCGACAGCGACGTGGGGGAGTTCCGGGCGGTGAC" +
    "GGAGCTGGGGCGGCCTGATGCCGAGTACTGGAACAGCCAGAAGGACATCCTGGAAGACGAG" +
    "CGGGCCGCGGTGGACACCTACTGCAGACACAACTACGGGGTTGTGAGAGCTTCACCGTGCA" +
    "GCGGCGAGACGCACTCGT"

  val targetSeq = "GAGGCCGCCTGTGTGACTGGATCGTTCGTGTCCCCACAGCACGTTTCTTGGA"

  @Test
  def test01:Unit={
    val (supplyInformation,queryBytes) = to2bit(querySeq)
    println(util.Arrays.toString(queryBytes))
    val query4BitBytes = reserve4bit(queryBytes)
    println(util.Arrays.toString(query4BitBytes))

    //    queryBytes.foreach { byte =>
//    val binaryString = "%8s".format(Integer.toBinaryString(byte & 0xFF)).replace(' ', '0')
//    println(binaryString)}
//    query4BitBytes.foreach { byte =>
//      val binaryString = "%8s".format(Integer.toBinaryString(byte & 0xFF)).replace(' ', '0')
//      println(binaryString)
//    }
    val combinedBytes: Array[(Byte, Byte)] = queryBytes.zip(query4BitBytes)

    combinedBytes.foreach { case (byte1, byte2) =>
      val binaryString1 = "%8s".format(Integer.toBinaryString(byte1 & 0xFF)).replace(' ', '0')
      val binaryString2 = "%8s".format(Integer.toBinaryString(byte2 & 0xFF)).replace(' ', '0')
      println(binaryString1 + " " + binaryString2)
    }

  }

  @Test
  def test02: Unit = {
    val (querySupplyInformation, queryBytes) = to2bit(querySeq)
    println(util.Arrays.toString(queryBytes))
    val query4BitBytes = reserve4bit(queryBytes)
    println(util.Arrays.toString(query4BitBytes))

    val combinedBytes: Array[(Byte, Byte)] = queryBytes.zip(query4BitBytes)

    combinedBytes.foreach { case (byte1, byte2) =>
      val binaryString1 = "%8s".format(Integer.toBinaryString(byte1 & 0xFF)).replace(' ', '0')
      val binaryString2 = "%8s".format(Integer.toBinaryString(byte2 & 0xFF)).replace(' ', '0')
      println(binaryString1 + " " + binaryString2)
    }

    val (targetSupplyInformation, targetBytes) = to2bit(targetSeq)
    println(util.Arrays.toString(targetBytes))
    val target4BitBytes = reserve4bit(targetBytes)
    println(util.Arrays.toString(target4BitBytes))
    val combinedBytes2: Array[(Byte, Byte)] = targetBytes.zip(target4BitBytes)

    combinedBytes2.foreach { case (byte1, byte2) =>
      val binaryString1 = "%8s".format(Integer.toBinaryString(byte1 & 0xFF)).replace(' ', '0')
      val binaryString2 = "%8s".format(Integer.toBinaryString(byte2 & 0xFF)).replace(' ', '0')
      println(binaryString1 + " " + binaryString2)
    }


  }

  @Test
  def test03: Unit = {
    val queryFile = "D:\\GithubRepository\\biosequence\\src\\test\\bioJAVA\\data\\sampling.fa"
    val targetFile = "D:\\GithubRepository\\biosequence\\src\\test\\bioJAVA\\data\\pseudo108_chr1.fa"

    val (queryInformation, querySequence) = getInformationAndSequence(queryFile)
    val (targetInformation, targetSequence) = getInformationAndSequence(targetFile)

    val targetArray = SequencePartitioning.partition(targetSequence,querySequence.length)
//    println(targetArray.length)
//
//    val lastPartition = targetArray(targetArray.length - 1)
//    println(lastPartition)

    val psa = Search.searchOne(querySequence,targetArray)

    println(psa)

  }

  @Test
  def test: Unit = {
    println(querySeq)
    println(targetSeq)
  }

  def reserve4bit(bytes:Array[Byte]):Array[Byte] = {
    bytes.map(byte => (byte & 0x0F).toByte)
  }



}
