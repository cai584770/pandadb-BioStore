package store


import biopanda.sequence.BioSequenceType
import biopanda.sequence.BioSequenceType.BioSequenceType
import exception.BioSequenceTypeException
import utils.file.FileNormalize

/**
 * @author cai584770
 * @date 2024/4/8 9:31
 * @Version
 */
object ReStoreSequence {

  /***
   *
   * @param binaryArray sequence binary array
   * @param supplementaryInformation sequence supple information: lower case start & length, N case start & length ,other case start & substring
   * @return sequence
   */
  def from2bit(binaryArray: Array[Byte], supplementaryInformation: Map[String, List[(Any, Any)]], bioSequenceType: BioSequenceType = BioSequenceType.DNA): String = {
    val lowerCaseList: List[(Int, Int)] = supplementaryInformation.getOrElse("LowerCasePosition", List.empty) map {
      case (a: Int, b: Int) => (a, b)
      case _ => throw new RuntimeException("Invalid tuple elements")
    }

    val nCaseList: List[(Int, Int)] = supplementaryInformation.getOrElse("NCasePosition", List.empty) map {
      case (a: Int, b: Int) => (a, b)
      case _ => throw new RuntimeException("Invalid tuple elements")
    }

    val otherCaseList: List[(Int, String)] = supplementaryInformation.getOrElse("OtherCaseList", List.empty) map {
      case (a: Int, b: String) => (a, b)
      case _ => throw new RuntimeException("Invalid tuple elements")
    }

    val lengthList: List[(Int, Int)] = supplementaryInformation.getOrElse("Length", List.empty) map {
      case (a: Int, b: Int) => (a, b)
      case _ => throw new RuntimeException("Invalid tuple elements")
    }

    val binaryString = convertFromBinaryArray(binaryArray, bioSequenceType)
    println(f"length:${lengthList.head._2}")
    val agctSequence = binaryString.substring(0, lengthList.head._2)

    val haveNoCaseSequence = insertNonACGT(agctSequence, otherCaseList)
    val haveNCaseSequence = insertN(haveNoCaseSequence,nCaseList)
    val haveLowerCaseSequence = restoreConsecutiveLowerCase(haveNCaseSequence,lowerCaseList)

    FileNormalize.insertNewlines(haveLowerCaseSequence,79)
  }



  /***
   *
   * @param bytes array[byte] to string
   * @return AGCT sequence
   */
  def convertFromBinaryArray(bytes: Array[Byte],bioSequenceType:BioSequenceType): String = {
    val conversionMap = bioSequenceType match {
      case BioSequenceType.DNA => Map("00" -> 'A', "01" -> 'G', "10" -> 'C', "11" -> 'T')
      case BioSequenceType.RNA => Map("00" -> 'A', "01" -> 'G', "10" -> 'C', "11" -> 'U')
      case _ => throw new BioSequenceTypeException
    }
    val binaryStringBuilder = new StringBuilder()

    for (byte <- bytes) {
      for (i <- 7 to 0 by -1) {
        val bit = (byte >> i) & 1
        binaryStringBuilder.append(bit)
      }
    }

    val binaryString = binaryStringBuilder.toString()

    val resultStringBuilder = new StringBuilder()
    var index = 0
    while (index < binaryString.length) {
      val codon = binaryString.substring(index, index + 2)
      val char = conversionMap.getOrElse(codon, '_')
      resultStringBuilder.append(char)
      index += 2
    }

    resultStringBuilder.toString()
  }

  /***
   *
   * @param bytes Array[Byte] to string
   * @return AGCT sequence=>01 sequence
   */
  def convertFromBinary(bytes: Array[Byte]): String = {
    val binaryStringBuilder = new StringBuilder()

    for (byte <- bytes) {
      for (i <- 7 to 0 by -1) {
        val bit = (byte >> i) & 1
        binaryStringBuilder.append(bit)
      }
    }

    binaryStringBuilder.toString()

  }

  /** *
   *
   * @param original  AGCT sequence
   * @param positions N case start position & length
   * @return
   */
  def insertN(original: String, positions: List[(Int, Int)]): String = {
    val result = new StringBuilder()
    var position = 0
    var substring = ""
    for ((start, length) <- positions) {
      result.append(original.substring(position,position+start))
      result.append("N"*length)

      position += start
    }

    if (position<original.length)
      result.append(original.substring(position))

    result.toString
  }



  /***
   *
   * @param original AGCTN sequence
   * @param positions non-AGCTN case start & substring
   * @return
   */
  def insertNonACGT(original: String, positions: List[(Int, String)]): String = {
    val result = new StringBuilder(original)
    var position = 0
    for ((start, substring) <- positions) {
      position += start
      result.insert(position, substring)
      position +=  substring.length
    }
    result.toString
  }

  /** *
   *
   * @param sequence  upper sequence
   * @param positions lower case start & length
   * @return DNAsequence
   */
  def restoreConsecutiveLowerCase(sequence: String, positions: List[(Int, Int)]): String = {
    val chars = sequence.toCharArray
    var position = 0

    for ((start, length) <- positions) {
      position += start
      val end = position + length
      while (position < end) {
        chars(position) = chars(position).toLower
        position += 1
      }
    }

    new String(chars)
  }
}
