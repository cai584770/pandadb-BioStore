package store

import file.FileProcess
import org.junit.jupiter.api.Test
import store.StoreSequence.to2bit

import scala.collection.mutable.ListBuffer

/**
 * @author cai584770
 * @date 2024/4/8 10:05
 * @Version
 */
class StoreSequenceTest {
  val sequence = "NNNNNNNNNNNNNNNNNNNNNNNNATCCCTAAATCTTTAAATCCTACANCCATGAATCCCTAAATACCTAATNCCCTAANCCCGAAAAATCTTTAAATCCTACCCGGTTTCTCTGGTTGAAAATCATTGTGTATATAATNATAATTTTATCNTTTTTATGNAATTGCTAATCTTTAAATCCTACAATCTTTAAATCCTACTATTGTTGNGTGTAGNTTTTTTAAAAATATCATTTNAGGTCAATACAAATCCTATAATCTTTAAATCCTAC"
  val inputpath1 = "D:/GithubRepository/biosequence/src/test/biosequence/data/88_chr1.fa"

  @Test
  def test01(): Unit = {
    val inputString = "acgtGTGGTGUxgTAA"
    val nonAGCTStrings = extractNonAGCT(inputString)
    println("Non-AGCT Substrings: " + nonAGCTStrings.mkString(", "))
  }


  @Test // remove N
  def test02(): Unit = {
    val inputString = "NNNAGFASJLNNNAAHSNNNNSFOANNNNHFASLFNNNNJAAAAAGNNN"
    val (resultString, positions) = removeAndRecordN(inputString)
    println("Result string after removing 'N':")
    println(resultString)
    println("Positions of 'N' characters:")
    positions.foreach { case (start, length) =>
      println(s"Start position: $start, Length: $length")
    }
  }



  @Test
  def test03(): Unit = {
    val inputString = "AGFASJLAAHSSFOAHFASLFJAAAAAG"
    val (modifiedSequenceNonAGCT, positionsNonAGCT) = removeAndRecord(inputString)
    println("Modified Sequence with Non-AGCT Removal: " + modifiedSequenceNonAGCT)
    println("Positions and Substrings of Removed Non-AGCT Sequences: " + positionsNonAGCT)
  }

  @Test
  def test4(): Unit = {
    val inputString = "AGfasjlnnnnaAHSsNNNFOAHfaslfNNNjAaaaAG"
    val (sequence, lowercase) = findConsecutiveLowerCasePositions(inputString)
    val (seq, ncase) = removeAndRecordN(sequence)
    val (s, noncase) = removeAndRecord(seq)
    val binary = convertToBinary(s)
    val binaryArray = convertToBinaryArray(s)
    println(sequence)
    println(lowercase)
    println(seq)
    println(ncase)
    println(s)
    println(noncase)
    println(binary)
    println(binaryArray)
  }


  val storeSequence = "CCGG TTTC TCTG GTTG AAAA TCAT TGTG TATA TAAT NATA ATTT TATC NTTT TTAT GNAA TTGC TTAT TGTT GNGT GTAGN TTTT TTAA AAAT ATCA TTTN AGGT CAAT ACAA ATCCT AT"

  val sequence1 = "CTGG TTGA AAAT CATT GTGT AT" // 存在一个 不包含N
  val sequence2 = "ATCA TTGT GTAT ATAA TNAT AATT TTAT CNTT TTTA TGNA ATT" // 存在1个 包含N
  val sequence3 = "TTTTA" // 存在多个
  val sequence4 = "TTGTGTATTGAAAATCA" // 部分存在

  @Test // lowercase position & length
  def test5(): Unit = {
    val inputString = sequence1
    val (supplyInformation, to2bit0) = to2bit(inputString)
    println(supplyInformation)
    println(to2bit0)
    val offset1InputString = 'A' + inputString
    val (supplyInformation1, to2bit1) = to2bit(offset1InputString)
    println(supplyInformation1)
    println(to2bit1)
    val offset1InputString2 = 'A' + offset1InputString
    val (supplyInformation2, to2bit2) = to2bit(offset1InputString2)
    println(supplyInformation2)
    println(to2bit2)
    val offset1InputString3 = 'A' + offset1InputString2
    val (supplyInformation3, to2bit3) = to2bit(offset1InputString3)
    println(supplyInformation3)
    println(to2bit3)
  }

  @Test // lowercase position & length
  def test6(): Unit = {
    val inputString = "AGfasjlnnnnaAHSsAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAANNNFOAHfaslfNNNjAaaaAG"
    val (supplyInformation, to2bit0) = to2bit(inputString)
    println(supplyInformation)
    println(to2bit0)
  }

  @Test // lowercase position & length
  def test7(): Unit = {
    val inputString = sequence
    val (supplyInformation, to2bit0) = to2bit(inputString)
    println(supplyInformation)
    println(to2bit0)
  }

  val TAIR10_chr1="D:/GithubRepository/biosequence/src/test/biosequence/data/TAIR10/TAIR10_chr1.fas"
  val TAIR10_chr2="D:/GithubRepository/biosequence/src/test/biosequence/data/TAIR10/TAIR10_chr2.fas"
  val TAIR10_chr3="D:/GithubRepository/biosequence/src/test/biosequence/data/TAIR10/TAIR10_chr3.fas"
  val TAIR10_chr4="D:/GithubRepository/biosequence/src/test/biosequence/data/TAIR10/TAIR10_chr4.fas"
  val TAIR10_chr5="D:/GithubRepository/biosequence/src/test/biosequence/data/TAIR10/TAIR10_chr5.fas"
  val inputpath2 = "D:/GithubRepository/biosequence/src/test/biosequence/data/chr2.fa"

  @Test
  def test8(): Unit = {
    var startTime = System.nanoTime()

    val (information, sequence) = FileProcess.getInformationAndSequence(inputpath2)

    var endTime = System.nanoTime()
    var durationMs = (endTime - startTime) / 1000000.0

    println(s"getInformationAndSequence runtime：$durationMs ms")

    startTime = System.nanoTime()
    val (s1,lowercase) = findConsecutiveLowerCasePositions1(sequence)
    endTime = System.nanoTime()
    durationMs = (endTime - startTime) / 1000000.0

    println(s"getInformationAndSequence runtime：$durationMs ms")
    println(s"s1 length:${s1.length}")
    println(lowercase)

    startTime = System.nanoTime()
    val (s2, ncase) = removeAndRecordN5(s1)
    endTime = System.nanoTime()
    durationMs = (endTime - startTime) / 1000000.0

    println(s"getInformationAndSequence runtime：$durationMs ms")
    println(ncase)

    startTime = System.nanoTime()
    val (s3, othercase) = removeAndRecord(s2)
    endTime = System.nanoTime()
    durationMs = (endTime - startTime) / 1000000.0

    println(s"getInformationAndSequence runtime：$durationMs ms")
    println(othercase)

    startTime = System.nanoTime()
    val s3arraybyte = convertToBinaryArray(s3)
    endTime = System.nanoTime()
    durationMs = (endTime - startTime) / 1000000.0

    println(s"getInformationAndSequence runtime：$durationMs ms")
    println(s3arraybyte.length)

  }

  @Test // lowercase position & length
  def test9(): Unit = {
    var startTime = System.nanoTime()

    val (information, sequence) = FileProcess.getInformationAndSequence(inputpath1)

    var endTime = System.nanoTime()
    var durationMs = (endTime - startTime) / 1000000.0

    println(s"getInformationAndSequence runtime：$durationMs ms")

    startTime = System.nanoTime()
    val (supplyInformation, to2bit0) = to2bit(sequence)
    endTime = System.nanoTime()
    durationMs = (endTime - startTime) / 1000000.0

    println(s"getInformationAndSequence runtime：$durationMs ms")


  }

  @Test
  def test10(): Unit = {

    val (s1, lowercase) = findConsecutiveLowerCasePositions1(sequence)
    println(s1)
    println(lowercase)
    val (s2, ncase) = removeAndRecordN4(s1)
    println(s2)
    println(ncase)
    val (s3, othercase) = removeAndRecord(s2)
    println(s3)
    println(othercase)
  }

  def removeAndRecordN5(s: String): (String, List[(Int, Int)]) = {
    val positions = ListBuffer[(Int, Int)]()
    val result = new StringBuilder()

    var start = 0
    var end = 0
    var prePosition = 0

    var length = 0
    while (end < s.length) {
      if (s(end) == 'N') {
        start = end
        while (end < s.length && s(end) == 'N') {
          end += 1
        }
        if (end < s.length) {
          result.append(s(end))
        }
        positions += ((start - prePosition-length, end - start))
        prePosition = start
        length = end - start

      } else {
        result.append(s(end))
      }
      end += 1
    }

    (result.toString, positions.toList)
  }


  def removeAndRecordN4(s: String): (String, List[(Int, Int)]) = {
    val positions = ListBuffer[(Int, Int)]()
    var start = 0
    var end = 0
    val result = new StringBuilder()

    var position = 0
    while (end < s.length) {
      if (s(end) == 'N') {
        while (end < s.length && s(end) == 'N') {
          end += 1
        }

        positions += ((start - position, end - start))
        position = end
      } else {
        result.append(s(end))
        start = end + 1
      }
      end += 1
    }

    (result.toString, positions.toList)
  }

  def removeAndRecordN3(s: String): (String, List[(Int, Int)]) = {
    val positions = ListBuffer[(Int, Int)]()
    var start = 0
    var end = 0
    val result = new StringBuilder()

    while (end < s.length) {
      if (s(end) == 'N') {
        while (end < s.length && s(end) == 'N') {
          end += 1
        }
        positions += ((start, end - start))
      } else {
        result.append(s(end))
        start = end + 1
      }
      end += 1
    }

    (result.toString, positions.toList)
  }


  def removeAndRecordN2(s: String): (String, List[(Int, Int)]) = {
    val result = new StringBuilder(s)
    val positions = ListBuffer[(Int, Int)]()
    var pos = 0
    var index = 0

    while (index < result.length) {
      if (result(index) == 'N') {
        val start = index
        var length = 1
        index += 1
        while (index < result.length && result(index) == 'N') {
          length += 1
          index += 1
        }
        result.delete(start - pos, index - pos)
        positions += ((start - pos, length))
        pos += length
      } else {
        index += 1
        pos += 1 // 优化：跳过不需要处理的字符时，也更新 pos
      }
    }

    (result.toString, positions.toList)
  }

  def removeAndRecordN1(s: String): (String, List[(Int, Int)]) = {
    val result = new StringBuilder(s)
    val positions = ListBuffer[(Int, Int)]()
    var pos = 0
    var index = 0

    while (index < result.length) {
      if (result(index) == 'N') {
        val start = index
        var length = 1
        index += 1
        while (index < result.length && result(index) == 'N') {
          length += 1
          index += 1
        }
        result.delete(start - pos, index - pos)
        positions += ((start - pos, length))
        pos += length
      } else {
        index += 1
      }
    }

    (result.toString, positions.toList)
  }

  def findConsecutiveLowerCasePositions1(s: String): (String, List[(Int, Int)]) = {
    val result = ListBuffer[(Int, Int)]()
    val sequenceBuilder = new StringBuilder()
    var startIndex = -1
    var length = 0
    var realStartPos = 0

    for ((c, index) <- s.zipWithIndex) {
      if (c.isLower) {
        sequenceBuilder += c.toUpper
        if (startIndex == -1) {
          startIndex = index
        }
        length += 1
      } else {
        sequenceBuilder += c
        if (startIndex != -1) {
          result += ((startIndex - realStartPos, length))
          realStartPos = startIndex + length
          startIndex = -1
          length = 0
        }
      }
    }

    if (startIndex != -1) {
      result += ((startIndex - realStartPos, length))
    }

    (sequenceBuilder.toString(), result.toList)
  }











  def convertToBinaryArray(s: String): Array[Byte] = {
    val conversionMap = Map('A' -> "00", 'G' -> "01", 'C' -> "10", 'T' -> "11")
    val binaryStringBuilder = new StringBuilder()

    for (char <- s) {
      binaryStringBuilder.append(conversionMap.getOrElse(char, ""))
    }

    val binaryString = binaryStringBuilder.toString()
    val binaryArray = new Array[Byte](binaryString.length / 8 + (if (binaryString.length % 8 != 0) 1 else 0))

    for (i <- 0 until binaryArray.length) {
      var byteValue = 0
      for (j <- 0 until 8) {
        val charIndex = i * 8 + j
        if (charIndex < binaryString.length && binaryString(charIndex) == '1') {
          byteValue |= (1 << (7 - j))
        }
      }
      binaryArray(i) = byteValue.toByte
    }

    binaryArray
  }

  def convertToBinary(s: String): String = {
    val conversionMap = Map('A' -> "00", 'G' -> "01", 'C' -> "10", 'T' -> "11")
    val result = new StringBuilder()

    for (char <- s) {
      result.append(conversionMap.getOrElse(char, ""))
    }

    result.toString
  }

  def removeAndRecord(s: String): (String, List[(Int, String)]) = {
    val result = new StringBuilder(s)
    val positions = scala.collection.mutable.ListBuffer[(Int, String)]()
    var pos = 0

    var index = 0
    while (index < result.length) {
      val currentChar = result(index)
      if (!List('A', 'G', 'C', 'T').contains(currentChar)) {
        val start = index
        var subString = currentChar.toString
        result.deleteCharAt(index)
        while (index < result.length && !List('A', 'G', 'C', 'T').contains(result(index))) {
          subString += result(index)
          result.deleteCharAt(index)
        }
        positions += ((start - pos, subString))
        pos = start
      } else {
        index += 1
      }
    }

    (result.toString, positions.toList)
  }

  def removeAndRecordN(s: String): (String, List[(Int, Int)]) = {
    val result = new StringBuilder(s)
    val positions = scala.collection.mutable.ListBuffer[(Int, Int)]()
    var pos = 0

    var index = 0
    while (index < result.length) {
      if (result(index) == 'N') {
        val start = index
        var length = 1
        result.deleteCharAt(index)
        while (index < result.length && result(index) == 'N') {
          result.deleteCharAt(index)
          length += 1
        }
        positions += ((start - pos, length))
        pos = start
      } else {
        index += 1
      }
    }

    (result.toString, positions.toList)
  }
  def findConsecutiveLowerCasePositions(s: String): (String, List[(Int, Int)]) = {
    val result = scala.collection.mutable.ListBuffer[(Int, Int)]()
    var startIndex = -1
    var length = 0
    var realStartPos = 0
    var sequence = ""

    for ((c, index) <- s.zipWithIndex) {
      if (c.isLower) {
        sequence += c.toUpper
        if (startIndex == -1) {
          startIndex = index
        }
        length += 1
      } else {
        sequence += c
        if (startIndex != -1) {
          result += ((startIndex - realStartPos, length))
          realStartPos = startIndex + length
          startIndex = -1
          length = 0
        }
      }
    }

    if (startIndex != -1) {
      result += ((startIndex - realStartPos, length))
    }

    (sequence, result.toList)
  }

  def extractNonAGCT(str: String): List[String] = {
    val pattern = "[^AGCT]+".r
    pattern.findAllMatchIn(str).toList.map(_.toString)
  }

}
