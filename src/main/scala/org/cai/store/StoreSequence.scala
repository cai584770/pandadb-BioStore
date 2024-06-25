package store

import biopanda.sequence.BioSequenceType.{BioSequenceType, DNA}
import biopanda.sequence.BioSequenceType
import org.cai.exception.BioSequenceTypeException
import utils.file.FileNormalize

import scala.collection.mutable.ListBuffer

/**
 * @author cai584770
 * @date 2024/4/2 10:12
 * @Version
 */
object StoreSequence {

  /** *
   *
   * @param data sequence
   * @return Map -> "LowerCasePosition" -> lowerCaseList,
   *         "NCasePosition" -> nCaseList,
   *         "OtherCaseList" -> otherCaseList
   *         Array[Byte] -> AGCT binary array
   */
  def to2bit(data: String,bioSequenceType: BioSequenceType = BioSequenceType.DNA): (Map[String, List[(Any, Any)]], Array[Byte]) = {
    val sequence = FileNormalize.remove(data)
    val sequenceLength = sequence.length

    val (noLowerCaseSequence, lowerCaseList) = findConsecutiveLowerCasePositions(sequence)
    val (noNSequence, nCaseList) = removeAndRecordN(noLowerCaseSequence)
    val (agctSequence, otherCaseList) = removeAndRecord(noNSequence)

    val agctSequenceLength = agctSequence.length
    val lengthList = List((sequenceLength,agctSequenceLength))

    val sequence2bit =  convertToBinaryArray(agctSequence, bioSequenceType)

    val supplementaryInformation = Map(
      "LowerCasePosition" -> lowerCaseList,
      "NCasePosition" -> nCaseList,
      "OtherCaseList" -> otherCaseList,
      "Length" -> lengthList
    )

    (supplementaryInformation, sequence2bit)
  }

  /** *
   *
   * @param s sequence
   * @return AGCT to binary array, A->00, G->01, C->10, T->11
   */
  def convertToBinaryArray(s: String, bioSequenceType: BioSequenceType): Array[Byte] = {
    val conversionMap = bioSequenceType match {
      case DNA => Map('A' -> "00", 'G' -> "01", 'C' -> "10", 'T' -> "11")
      case BioSequenceType.RNA => Map('A' -> "00", 'G' -> "01", 'C' -> "10", 'U' -> "11")
      case _ => throw new BioSequenceTypeException
    }

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

  /** *
   *
   * @param s sequence
   * @return binary string
   */
  def convertToBinary(s: String): String = {
    val conversionMap = Map('A' -> "00", 'G' -> "01", 'C' -> "10", 'T' -> "11")
    val result = new StringBuilder()

    for (char <- s) {
      result.append(conversionMap.getOrElse(char, ""))
    }

    result.toString
  }

  /** *
   *
   * @param s non-lowercase seuqnce
   * @return non-N sequecne
   */
  def removeAndRecordN(s: String): (String, List[(Int, Int)]) = {
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
        positions += ((start - prePosition - length, end - start))
        prePosition = start
        length = end - start

      } else {
        result.append(s(end))
      }
      end += 1
    }

    (result.toString, positions.toList)
  }


  /** *
   *
   * @param s non-N & non-lowercase sequence
   * @return only AGCT string
   */
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

  /** *
   *
   * @param s sequence
   * @return non-lowercase sequence & lowercase sub-sequence start position and length
   */
  def findConsecutiveLowerCasePositions(s: String): (String, List[(Int, Int)]) = {
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



}
