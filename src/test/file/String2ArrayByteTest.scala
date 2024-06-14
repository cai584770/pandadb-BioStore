package file

import org.junit.Test
import store.StoreSequence.{findConsecutiveLowerCasePositions, removeAndRecord, removeAndRecordN, to2bit}
import utils.file.{FileNormalize, FileProcess}

import java.io.{BufferedReader, BufferedWriter, File, FileReader, FileWriter}
import java.util
import scala.collection.mutable.ArrayBuffer

/**
 * @author cai584770
 * @date 2024/4/24 16:50
 * @Version
 */
class String2ArrayByteTest {

//  A 00
//  G 01
//  C 10
//  T 11
  val str = "AATGTCGTTCCTTTTTCATCATCTTAGCTATATCTACAGA"
  val agct = "AGCT"

  @Test
  def test01:Unit={
    val ab = agct.getBytes()
    println(util.Arrays.toString(ab))
    println(ab.length)

    val t1 = System.nanoTime()
    val strAB = string2AB(str)
    println(System.nanoTime()-t1)
//    println(util.Arrays.toString(strAB))

    val t2 = System.nanoTime()
    val dictAB = dict2AB(str)
    println(System.nanoTime() - t2)
//    println(util.Arrays.toString(dictAB))


//    for (b <- ab) {
//      System.out.print(Integer.toBinaryString(b & 0xFF) + "\n")
//    }

  }

  def dict2AB(data:String):Array[Byte]={
    val remainder = data.length % 4
    val paddingNeeded = if (remainder != 0) 4 - remainder else 0
    val stringBuilder = new StringBuilder(data)
    stringBuilder.append("A" * paddingNeeded)

    val resultBuffer = new ArrayBuffer[Byte]
    stringBuilder.grouped(4).foreach { group =>
      group.toString match {
        case "AAAA" => resultBuffer += 0
        case "AAAG" => resultBuffer += 1
        case "AAAC" => resultBuffer += 2
        case "AAAT" => resultBuffer += 3
        case "AAGA" => resultBuffer += 4
        case "AAGG" => resultBuffer += 5
        case "AAGC" => resultBuffer += 6
        case "AAGT" => resultBuffer += 7
        case "AACA" => resultBuffer += 8
        case "AACG" => resultBuffer += 9
        case "AACC" => resultBuffer += 10
        case "AACT" => resultBuffer += 11
        case "AATA" => resultBuffer += 12
        case "AATG" => resultBuffer += 13
        case "AATC" => resultBuffer += 14
        case "AATT" => resultBuffer += 15
        case "AGAA" => resultBuffer += 16
        case "AGAG" => resultBuffer += 17
        case "AGAC" => resultBuffer += 18
        case "AGAT" => resultBuffer += 19
        case "AGGA" => resultBuffer += 20
        case "AGGG" => resultBuffer += 21
        case "AGGC" => resultBuffer += 22
        case "AGGT" => resultBuffer += 23
        case "AGCA" => resultBuffer += 24
        case "AGCG" => resultBuffer += 25
        case "AGCC" => resultBuffer += 26
        case "AGCT" => resultBuffer += 27
        case "AGTA" => resultBuffer += 28
        case "AGTG" => resultBuffer += 29
        case "AGTC" => resultBuffer += 30
        case "AGTT" => resultBuffer += 31
        case "ACAA" => resultBuffer += 32
        case "ACAG" => resultBuffer += 33
        case "ACAC" => resultBuffer += 34
        case "ACAT" => resultBuffer += 35
        case "ACGA" => resultBuffer += 36
        case "ACGG" => resultBuffer += 37
        case "ACGC" => resultBuffer += 38
        case "ACGT" => resultBuffer += 39
        case "ACCA" => resultBuffer += 40
        case "ACCG" => resultBuffer += 41
        case "ACCC" => resultBuffer += 42
        case "ACCT" => resultBuffer += 43
        case "ACTA" => resultBuffer += 44
        case "ACTG" => resultBuffer += 45
        case "ACTC" => resultBuffer += 46
        case "ACTT" => resultBuffer += 47
        case "ATAA" => resultBuffer += 48
        case "ATAG" => resultBuffer += 49
        case "ATAC" => resultBuffer += 50
        case "ATAT" => resultBuffer += 51
        case "ATGA" => resultBuffer += 52
        case "ATGG" => resultBuffer += 53
        case "ATGC" => resultBuffer += 54
        case "ATGT" => resultBuffer += 55
        case "ATCA" => resultBuffer += 56
        case "ATCG" => resultBuffer += 57
        case "ATCC" => resultBuffer += 58
        case "ATCT" => resultBuffer += 59
        case "ATTA" => resultBuffer += 60
        case "ATTG" => resultBuffer += 61
        case "ATTC" => resultBuffer += 62
        case "ATTT" => resultBuffer += 63
        case "GAAA" => resultBuffer += 64
        case "GAAG" => resultBuffer += 65
        case "GAAC" => resultBuffer += 66
        case "GAAT" => resultBuffer += 67
        case "GAGA" => resultBuffer += 68
        case "GAGG" => resultBuffer += 69
        case "GAGC" => resultBuffer += 70
        case "GAGT" => resultBuffer += 71
        case "GACA" => resultBuffer += 72
        case "GACG" => resultBuffer += 73
        case "GACC" => resultBuffer += 74
        case "GACT" => resultBuffer += 75
        case "GATA" => resultBuffer += 76
        case "GATG" => resultBuffer += 77
        case "GATC" => resultBuffer += 78
        case "GATT" => resultBuffer += 79
        case "GGAA" => resultBuffer += 80
        case "GGAG" => resultBuffer += 81
        case "GGAC" => resultBuffer += 82
        case "GGAT" => resultBuffer += 83
        case "GGGA" => resultBuffer += 84
        case "GGGG" => resultBuffer += 85
        case "GGGC" => resultBuffer += 86
        case "GGGT" => resultBuffer += 87
        case "GGCA" => resultBuffer += 88
        case "GGCG" => resultBuffer += 89
        case "GGCC" => resultBuffer += 90
        case "GGCT" => resultBuffer += 91
        case "GGTA" => resultBuffer += 92
        case "GGTG" => resultBuffer += 93
        case "GGTC" => resultBuffer += 94
        case "GGTT" => resultBuffer += 95
        case "GCAA" => resultBuffer += 96
        case "GCAG" => resultBuffer += 97
        case "GCAC" => resultBuffer += 98
        case "GCAT" => resultBuffer += 99
        case "GCGA" => resultBuffer += 100
        case "GCGG" => resultBuffer += 101
        case "GCGC" => resultBuffer += 102
        case "GCGT" => resultBuffer += 103
        case "GCCA" => resultBuffer += 104
        case "GCCG" => resultBuffer += 105
        case "GCCC" => resultBuffer += 106
        case "GCCT" => resultBuffer += 107
        case "GCTA" => resultBuffer += 108
        case "GCTG" => resultBuffer += 109
        case "GCTC" => resultBuffer += 110
        case "GCTT" => resultBuffer += 111
        case "GTAA" => resultBuffer += 112
        case "GTAG" => resultBuffer += 113
        case "GTAC" => resultBuffer += 114
        case "GTAT" => resultBuffer += 115
        case "GTGA" => resultBuffer += 116
        case "GTGG" => resultBuffer += 117
        case "GTGC" => resultBuffer += 118
        case "GTGT" => resultBuffer += 119
        case "GTCA" => resultBuffer += 120
        case "GTCG" => resultBuffer += 121
        case "GTCC" => resultBuffer += 122
        case "GTCT" => resultBuffer += 123
        case "GTTA" => resultBuffer += 124
        case "GTTG" => resultBuffer += 125
        case "GTTC" => resultBuffer += 126
        case "GTTT" => resultBuffer += 127
        case "CAAA" => resultBuffer += -128
        case "CAAG" => resultBuffer += -127
        case "CAAC" => resultBuffer += -126
        case "CAAT" => resultBuffer += -125
        case "CAGA" => resultBuffer += -124
        case "CAGG" => resultBuffer += -123
        case "CAGC" => resultBuffer += -122
        case "CAGT" => resultBuffer += -121
        case "CACA" => resultBuffer += -120
        case "CACG" => resultBuffer += -119
        case "CACC" => resultBuffer += -118
        case "CACT" => resultBuffer += -117
        case "CATA" => resultBuffer += -116
        case "CATG" => resultBuffer += -115
        case "CATC" => resultBuffer += -114
        case "CATT" => resultBuffer += -113
        case "CGAA" => resultBuffer += -112
        case "CGAG" => resultBuffer += -111
        case "CGAC" => resultBuffer += -110
        case "CGAT" => resultBuffer += -109
        case "CGGA" => resultBuffer += -108
        case "CGGG" => resultBuffer += -107
        case "CGGC" => resultBuffer += -106
        case "CGGT" => resultBuffer += -105
        case "CGCA" => resultBuffer += -104
        case "CGCG" => resultBuffer += -103
        case "CGCC" => resultBuffer += -102
        case "CGCT" => resultBuffer += -101
        case "CGTA" => resultBuffer += -100
        case "CGTG" => resultBuffer += -99
        case "CGTC" => resultBuffer += -98
        case "CGTT" => resultBuffer += -97
        case "CCAA" => resultBuffer += -96
        case "CCAG" => resultBuffer += -95
        case "CCAC" => resultBuffer += -94
        case "CCAT" => resultBuffer += -93
        case "CCGA" => resultBuffer += -92
        case "CCGG" => resultBuffer += -91
        case "CCGC" => resultBuffer += -90
        case "CCGT" => resultBuffer += -89
        case "CCCA" => resultBuffer += -88
        case "CCCG" => resultBuffer += -87
        case "CCCC" => resultBuffer += -86
        case "CCCT" => resultBuffer += -85
        case "CCTA" => resultBuffer += -84
        case "CCTG" => resultBuffer += -83
        case "CCTC" => resultBuffer += -82
        case "CCTT" => resultBuffer += -81
        case "CTAA" => resultBuffer += -80
        case "CTAG" => resultBuffer += -79
        case "CTAC" => resultBuffer += -78
        case "CTAT" => resultBuffer += -77
        case "CTGA" => resultBuffer += -76
        case "CTGG" => resultBuffer += -75
        case "CTGC" => resultBuffer += -74
        case "CTGT" => resultBuffer += -73
        case "CTCA" => resultBuffer += -72
        case "CTCG" => resultBuffer += -71
        case "CTCC" => resultBuffer += -70
        case "CTCT" => resultBuffer += -69
        case "CTTA" => resultBuffer += -68
        case "CTTG" => resultBuffer += -67
        case "CTTC" => resultBuffer += -66
        case "CTTT" => resultBuffer += -65
        case "TAAA" => resultBuffer += -64
        case "TAAG" => resultBuffer += -63
        case "TAAC" => resultBuffer += -62
        case "TAAT" => resultBuffer += -61
        case "TAGA" => resultBuffer += -60
        case "TAGG" => resultBuffer += -59
        case "TAGC" => resultBuffer += -58
        case "TAGT" => resultBuffer += -57
        case "TACA" => resultBuffer += -56
        case "TACG" => resultBuffer += -55
        case "TACC" => resultBuffer += -54
        case "TACT" => resultBuffer += -53
        case "TATA" => resultBuffer += -52
        case "TATG" => resultBuffer += -51
        case "TATC" => resultBuffer += -50
        case "TATT" => resultBuffer += -49
        case "TGAA" => resultBuffer += -48
        case "TGAG" => resultBuffer += -47
        case "TGAC" => resultBuffer += -46
        case "TGAT" => resultBuffer += -45
        case "TGGA" => resultBuffer += -44
        case "TGGG" => resultBuffer += -43
        case "TGGC" => resultBuffer += -42
        case "TGGT" => resultBuffer += -41
        case "TGCA" => resultBuffer += -40
        case "TGCG" => resultBuffer += -39
        case "TGCC" => resultBuffer += -38
        case "TGCT" => resultBuffer += -37
        case "TGTA" => resultBuffer += -36
        case "TGTG" => resultBuffer += -35
        case "TGTC" => resultBuffer += -34
        case "TGTT" => resultBuffer += -33
        case "TCAA" => resultBuffer += -32
        case "TCAG" => resultBuffer += -31
        case "TCAC" => resultBuffer += -30
        case "TCAT" => resultBuffer += -29
        case "TCGA" => resultBuffer += -28
        case "TCGG" => resultBuffer += -27
        case "TCGC" => resultBuffer += -26
        case "TCGT" => resultBuffer += -25
        case "TCCA" => resultBuffer += -24
        case "TCCG" => resultBuffer += -23
        case "TCCC" => resultBuffer += -22
        case "TCCT" => resultBuffer += -21
        case "TCTA" => resultBuffer += -20
        case "TCTG" => resultBuffer += -19
        case "TCTC" => resultBuffer += -18
        case "TCTT" => resultBuffer += -17
        case "TTAA" => resultBuffer += -16
        case "TTAG" => resultBuffer += -15
        case "TTAC" => resultBuffer += -14
        case "TTAT" => resultBuffer += -13
        case "TTGA" => resultBuffer += -12
        case "TTGG" => resultBuffer += -11
        case "TTGC" => resultBuffer += -10
        case "TTGT" => resultBuffer += -9
        case "TTCA" => resultBuffer += -8
        case "TTCG" => resultBuffer += -7
        case "TTCC" => resultBuffer += -6
        case "TTCT" => resultBuffer += -5
        case "TTTA" => resultBuffer += -4
        case "TTTG" => resultBuffer += -3
        case "TTTC" => resultBuffer += -2
        case "TTTT" => resultBuffer += -1
        case _ => println("error")
      }
    }
    resultBuffer.toArray

  }


  def getBinaryString(data:String):String={
     data.flatMap {
      case 'A' => "00"
      case 'G' => "01"
      case 'C' => "10"
      case 'T' => "11"
      case _ => ""
    }
  }

  def string2AB(data:String):Array[Byte]={
    val binaryString = data.flatMap {
      case 'A' => "00"
      case 'G' => "01"
      case 'C' => "10"
      case 'T' => "11"
      case _ => ""
    }

    binaryString.grouped(8).toArray.map { group =>
      Integer.parseInt(group, 2).toByte
    }

  }


  def agctMapping():Map[String, Byte]={
    val bases = "AGCT"
    var count: Byte = 0
    var mapping: Map[String, Byte] = Map[String, Byte]()
    for (s1 <- bases) {
      for (s2 <- bases) {
        for (s3 <- bases) {
          for (s4 <- bases) {

            var str = "" + s1 + s2 + s3 + s4
            val binaryString = str.flatMap {
              case 'A' => "00"
              case 'G' => "01"
              case 'C' => "10"
              case 'T' => "11"
              case _ => ""
            }
            var ab = binaryString.grouped(8).toArray
            println(ab)

          }
        }
      }
    }
    mapping
  }



  @Test
  def test02: Unit = {
    val  filepath = "D:\\GithubRepository\\biosequence\\src\\test\\biosequence\\data\\88_chr1.fa"
    val (information,originalSequence) = FileProcess.getInformationAndSequence(filepath)



//    val t1 = System.nanoTime()
    val (information1,ab1) = to2bit(originalSequence)
//    println(System.nanoTime() - t1)
    println(ab1.length)
    println(ab1.last)
    val sequence = FileNormalize.remove(originalSequence)
    val (noLowerCaseSequence, lowerCaseList) = findConsecutiveLowerCasePositions(sequence)
    val (noNSequence, nCaseList) = removeAndRecordN(noLowerCaseSequence)
    val (agctSequence, otherCaseList) = removeAndRecord(noNSequence)
    val t2 = System.nanoTime()
    val dictAB = dict2AB(agctSequence)
    println(System.nanoTime() - t2)
    println(dictAB.length)
    println(dictAB.last)

    val result = countByteFreq(dictAB)
    val sortedResult = result.toSeq.sortBy(-_._2)
    println(sortedResult)

  }

  def countByteFreq(data: Array[Byte]): Map[Byte, Int] = {
    val result = Map[Byte, Int]().withDefaultValue(0)

    data.groupBy(identity).mapValues(_.length)
  }


  @Test
  def test03: Unit = {
    val file = new File("mappingtable.txt")
    val bw = new BufferedWriter(new FileWriter(file))

    for (i <- 0 to 255) {
      val binaryChar = i.toByte
      bw.write(binaryChar)
      bw.newLine()
    }

    bw.close()

  }

  @Test
  def test04: Unit = {
    val file = new File("mappingtable.txt")
    val br = new BufferedReader(new FileReader(file))

    var line: String = null

    while ( {
      line = br.readLine(); line != null
    }) {
      val byteValue = line
      println(byteValue.getClass)
    }

    br.close()

  }

  @Test
  def test05: Unit = {
//    case "CAGA" => resultBuffer += 0x84.toByte
    val bases = "AGCT"
    var count: Byte = 0
    val by1:Byte = 1
    for (s1 <- bases){
      for(s2 <- bases){
        for (s3 <- bases) {
          for (s4 <- bases) {
            println("case"+" \""+s1+s2+s3+s4+"\" => resultBuffer += " + count)
            if (count == 127) {
              count = -128

            }else{
              count = (count + 1).toByte
            }
          }
        }
      }


    }


  }

  @Test
  def test06: Unit = {
    val bases = "AGCT"
    var count: Byte = 0
    var mapping: Map[String, Byte] = Map[String, Byte]()
    for (s1 <- bases) {
      for (s2 <- bases) {
        for (s3 <- bases) {
          for (s4 <- bases) {
            println("case" + " \"" + s1 + s2 + s3 + s4 + "\" => resultBuffer += " + count)
            mapping += ("" + s1 + s2 + s3 + s4 -> count)
            if (count == 127) {
              count = -128

            } else {
              count = (count + 1).toByte
            }
          }
        }
      }
    }
    mapping.foreach { case (key, value) =>
      println(s"Key: $key, Value: $value")
    }


  }

  @Test
  def test07: Unit = {


  }


  def dict2AB1(data: String): Array[Byte] = {
    val resultBuffer = new ArrayBuffer[Byte]

    val charToByte = Map(
      "AAAA" -> 0x00.toByte, "AAAG" -> 0x01.toByte, "AAAC" -> 0x02.toByte, "AAAT" -> 0x03.toByte,
      "AAGA" -> 0x04.toByte, "AAGG" -> 0x05.toByte, "AAGC" -> 0x06.toByte, "AAGT" -> 0x07.toByte,
      "AACA" -> 0x08.toByte, "AACG" -> 0x09.toByte, "AACC" -> 0x0A.toByte, "AACT" -> 0x0B.toByte,
      "AATA" -> 0x0C.toByte, "AATG" -> 0x0D.toByte, "AATC" -> 0x0E.toByte, "AATT" -> 0x0F.toByte,
      "AGAA" -> 0x10.toByte, "AGAG" -> 0x11.toByte, "AGAC" -> 0x12.toByte, "AGAT" -> 0x13.toByte,
      "AGGA" -> 0x14.toByte, "AGGG" -> 0x15.toByte, "AGGC" -> 0x16.toByte, "AGGT" -> 0x17.toByte,
      "AGCA" -> 0x18.toByte, "AGCG" -> 0x19.toByte, "AGCC" -> 0x1A.toByte, "AGCT" -> 0x1B.toByte,
      "AGTA" -> 0x1C.toByte, "AGTG" -> 0x1D.toByte, "AGTC" -> 0x1E.toByte, "AGTT" -> 0x1F.toByte,
      "ACAA" -> 0x20.toByte, "ACAG" -> 0x21.toByte, "ACAC" -> 0x22.toByte, "ACAT" -> 0x23.toByte,
      "ACGA" -> 0x24.toByte, "ACGG" -> 0x25.toByte, "ACGC" -> 0x26.toByte, "ACGT" -> 0x27.toByte,
      "ACCA" -> 0x28.toByte, "ACCG" -> 0x29.toByte, "ACCC" -> 0x2A.toByte, "ACCT" -> 0x2B.toByte,
      "ACTA" -> 0x2C.toByte, "ACTG" -> 0x2D.toByte, "ACTC" -> 0x2E.toByte, "ACTT" -> 0x2F.toByte,
      "ATAA" -> 0x30.toByte, "ATAG" -> 0x31.toByte, "ATAC" -> 0x32.toByte, "ATAT" -> 0x33.toByte,
      "ATGA" -> 0x34.toByte, "ATGG" -> 0x35.toByte, "ATGC" -> 0x36.toByte, "ATGT" -> 0x37.toByte,
      "ATCA" -> 0x38.toByte, "ATCG" -> 0x39.toByte, "ATCC" -> 0x3A.toByte, "ATCT" -> 0x3B.toByte,
      "ATTA" -> 0x3C.toByte, "ATTG" -> 0x3D.toByte, "ATTC" -> 0x3E.toByte, "ATTT" -> 0x3F.toByte,
      "GAAA" -> 0x40.toByte, "GAAG" -> 0x41.toByte, "GAAC" -> 0x42.toByte, "GAAT" -> 0x43.toByte,
      "GAGA" -> 0x44.toByte, "GAGG" -> 0x45.toByte, "GAGC" -> 0x46.toByte, "GAGT" -> 0x47.toByte,
      "GACA" -> 0x48.toByte, "GACG" -> 0x49.toByte, "GACC" -> 0x4A.toByte, "GACT" -> 0x4B.toByte,
      "GATA" -> 0x4C.toByte, "GATG" -> 0x4D.toByte, "GATC" -> 0x4E.toByte, "GATT" -> 0x4F.toByte,
      "GGAA" -> 0x50.toByte, "GGAG" -> 0x51.toByte, "GGAC" -> 0x52.toByte, "GGAT" -> 0x53.toByte,
      "GGGA" -> 0x54.toByte, "GGGG" -> 0x55.toByte, "GGGC" -> 0x56.toByte, "GGGT" -> 0x57.toByte,
      "GGCA" -> 0x58.toByte, "GGCG" -> 0x59.toByte, "GGCC" -> 0x5A.toByte, "GGCT" -> 0x5B.toByte,
      "GGTA" -> 0x5C.toByte, "GGTG" -> 0x5D.toByte, "GGTC" -> 0x5E.toByte, "GGTT" -> 0x5F.toByte,
      "GCAA" -> 0x60.toByte, "GCAG" -> 0x61.toByte, "GCAC" -> 0x62.toByte, "GCAT" -> 0x63.toByte,
      "GCGA" -> 0x64.toByte, "GCGG" -> 0x65.toByte, "GCGC" -> 0x66.toByte, "GCGT" -> 0x67.toByte,
      "GCCA" -> 0x68.toByte, "GCCG" -> 0x69.toByte, "GCCC" -> 0x6A.toByte, "GCCT" -> 0x6B.toByte,
      "GCTA" -> 0x6C.toByte, "GCTG" -> 0x6D.toByte, "GCTC" -> 0x6E.toByte, "GCTT" -> 0x6F.toByte,
      "GTAA" -> 0x70.toByte, "GTAG" -> 0x71.toByte, "GTAC" -> 0x72.toByte, "GTAT" -> 0x73.toByte,
      "GTGA" -> 0x74.toByte, "GTGG" -> 0x75.toByte, "GTGC" -> 0x76.toByte, "GTGT" -> 0x77.toByte,
      "GTCA" -> 0x78.toByte, "GTCG" -> 0x79.toByte, "GTCC" -> 0x7A.toByte, "GTCT" -> 0x7B.toByte,
      "GTTA" -> 0x7C.toByte, "GTTG" -> 0x7D.toByte, "GTTC" -> 0x7E.toByte, "GTTT" -> 0x7F.toByte,
      "CAAA" -> 0x80.toByte, "CAAG" -> 0x81.toByte, "CAAC" -> 0x82.toByte, "CAAT" -> 0x83.toByte,
      "CAGA" -> 0x84.toByte, "CAGG" -> 0x85.toByte, "CAGC" -> 0x86.toByte, "CAGT" -> 0x87.toByte,
      "CACA" -> 0x88.toByte, "CACG" -> 0x89.toByte, "CACC" -> 0x8A.toByte, "CACT" -> 0x8B.toByte,
      "CATA" -> 0x8C.toByte, "CATG" -> 0x8D.toByte, "CATC" -> 0x8E.toByte, "CATT" -> 0x8F.toByte,
      "CGAA" -> 0x90.toByte, "CGAG" -> 0x91.toByte, "CGAC" -> 0x92.toByte, "CGAT" -> 0x93.toByte,
      "CGGA" -> 0x94.toByte, "CGGG" -> 0x95.toByte, "CGGC" -> 0x96.toByte, "CGGT" -> 0x97.toByte,
      "CGCA" -> 0x98.toByte, "CGCG" -> 0x99.toByte, "CGCC" -> 0x9A.toByte, "CGCT" -> 0x9B.toByte,
      "CGTA" -> 0x9C.toByte, "CGTG" -> 0x9D.toByte, "CGTC" -> 0x9E.toByte, "CGTT" -> 0x9F.toByte,
      "CCAA" -> 0xA0.toByte, "CCAG" -> 0xA1.toByte, "CCAC" -> 0xA2.toByte, "CCAT" -> 0xA3.toByte,
      "CCGA" -> 0xA4.toByte, "CCGG" -> 0xA5.toByte, "CCGC" -> 0xA6.toByte, "CCGT" -> 0xA7.toByte,
      "CCCA" -> 0xA8.toByte, "CCCG" -> 0xA9.toByte, "CCCC" -> 0xAA.toByte, "CCCT" -> 0xAB.toByte,
      "CCTA" -> 0xAC.toByte, "CCTG" -> 0xAD.toByte, "CCTC" -> 0xAE.toByte, "CCTT" -> 0xAF.toByte,
      "CTAA" -> 0xB0.toByte, "CTAG" -> 0xB1.toByte, "CTAC" -> 0xB2.toByte, "CTAT" -> 0xB3.toByte,
      "CTGA" -> 0xB4.toByte, "CTGG" -> 0xB5.toByte, "CTGC" -> 0xB6.toByte, "CTGT" -> 0xB7.toByte,
      "CTCA" -> 0xB8.toByte, "CTCG" -> 0xB9.toByte, "CTCC" -> 0xBA.toByte, "CTCT" -> 0xBB.toByte,
      "CTTA" -> 0xBC.toByte, "CTTG" -> 0xBD.toByte, "CTTC" -> 0xBE.toByte, "CTTT" -> 0xBF.toByte,
      "TAAA" -> 0xC0.toByte, "TAAG" -> 0xC1.toByte, "TAAC" -> 0xC2.toByte, "TAAT" -> 0xC3.toByte,
      "TAGA" -> 0xC4.toByte, "TAGG" -> 0xC5.toByte, "TAGC" -> 0xC6.toByte, "TAGT" -> 0xC7.toByte,
      "TACA" -> 0xC8.toByte, "TACG" -> 0xC9.toByte, "TACC" -> 0xCA.toByte, "TACT" -> 0xCB.toByte,
      "TATA" -> 0xCC.toByte, "TATG" -> 0xCD.toByte, "TATC" -> 0xCE.toByte, "TATT" -> 0xCF.toByte,
      "TGAA" -> 0xD0.toByte, "TGAG" -> 0xD1.toByte, "TGAC" -> 0xD2.toByte, "TGAT" -> 0xD3.toByte,
      "TGGA" -> 0xD4.toByte, "TGGG" -> 0xD5.toByte, "TGGC" -> 0xD6.toByte, "TGGT" -> 0xD7.toByte,
      "TGCA" -> 0xD8.toByte, "TGCG" -> 0xD9.toByte, "TGCC" -> 0xDA.toByte, "TGCT" -> 0xDB.toByte,
      "TGTA" -> 0xDC.toByte, "TGTG" -> 0xDD.toByte, "TGTC" -> 0xDE.toByte, "TGTT" -> 0xDF.toByte,
      "TCAA" -> 0xE0.toByte, "TCAG" -> 0xE1.toByte, "TCAC" -> 0xE2.toByte, "TCAT" -> 0xE3.toByte,
      "TCGA" -> 0xE4.toByte, "TCGG" -> 0xE5.toByte, "TCGC" -> 0xE6.toByte, "TCGT" -> 0xE7.toByte,
      "TCCA" -> 0xE8.toByte, "TCCG" -> 0xE9.toByte, "TCCC" -> 0xEA.toByte, "TCCT" -> 0xEB.toByte,
      "TCTA" -> 0xEC.toByte, "TCTG" -> 0xED.toByte, "TCTC" -> 0xEE.toByte, "TCTT" -> 0xEF.toByte,
      "TTAA" -> 0xF0.toByte, "TTAG" -> 0xF1.toByte, "TTAC" -> 0xF2.toByte, "TTAT" -> 0xF3.toByte,
      "TTGA" -> 0xF4.toByte, "TTGG" -> 0xF5.toByte, "TTGC" -> 0xF6.toByte, "TTGT" -> 0xF7.toByte,
      "TTCA" -> 0xF8.toByte, "TTCG" -> 0xF9.toByte, "TTCC" -> 0xFA.toByte, "TTCT" -> 0xFB.toByte,
      "TTTA" -> 0xFC.toByte, "TTTG" -> 0xFD.toByte, "TTTC" -> 0xFE.toByte, "TTTT" -> 0xFF.toByte
    )

    data.grouped(4).foreach { group =>
      val byteValue = charToByte.getOrElse(group, {
        println("error")
        0x00.toByte
      })
      resultBuffer += byteValue
    }

    resultBuffer.toArray
  }
}
