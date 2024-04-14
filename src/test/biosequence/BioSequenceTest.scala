package biosequence

import org.junit.jupiter.api.Test

import java.io.{File, FileInputStream}

/**
 * @author cai584770
 * @date 2024/4/9 9:37
 * @Version
 */
class BioSequenceTest {
  val inputpath = "D:/GithubRepository/biosequence/src/test/biosequence/data/chr1.fa"
  val inputpath1 = "D:/GithubRepository/biosequence/src/test/biosequence/data/88_chr1.fa"
  val inputpath2 = "D:/GithubRepository/biosequence/src/test/biosequence/data/chr2.fa"

  val TAIR10_chr1 = "D:/GithubRepository/biosequence/src/test/biosequence/data/TAIR10/TAIR10_chr1.fas"
  val TAIR10_chr2 = "D:/GithubRepository/biosequence/src/test/biosequence/data/TAIR10/TAIR10_chr2.fas"
  val TAIR10_chr3 = "D:/GithubRepository/biosequence/src/test/biosequence/data/TAIR10/TAIR10_chr3.fas"
  val TAIR10_chr4 = "D:/GithubRepository/biosequence/src/test/biosequence/data/TAIR10/TAIR10_chr4.fas"
  val TAIR10_chr5 = "D:/GithubRepository/biosequence/src/test/biosequence/data/TAIR10/TAIR10_chr5.fas"
  @Test
  def test01():Unit={
    var startTime = System.nanoTime()
    val bioSequence = BioSequence.fromFile(inputpath1)
    var endTime = System.nanoTime()
    var durationMs = (endTime - startTime) / 1000000.0
    println(s"import runtime：$durationMs ms")

    val nCaseList: List[(Int, Int)] = bioSequence.supplyInformation.getOrElse("NCasePosition", List.empty) map {
      case (a: Int, b: Int) => (a, b)
      case _ => throw new RuntimeException("Invalid tuple elements")
    }
    println(bioSequence.toString())
    println(bioSequence.length)
    println(bioSequence.information)
    println(bioSequence.subProperty("information"))
    println(nCaseList.take(10))

    startTime = System.nanoTime()
    BioSequence.exportSequence(bioSequence,"D:/GithubRepository/biosequence/src/test/biosequence/data/inputpath1_.fa")
    endTime = System.nanoTime()
    durationMs = (endTime - startTime) / 1000000.0
    println(s"export runtime：$durationMs ms")

  }

  @Test
  def test02(): Unit = {
    val emptyBiosequence = BioSequence.EMPTY
    println(emptyBiosequence)

  }


  @Test
  def test03(): Unit = {

    val bioSequence = BioSequence.fromFile(inputpath1)

    BioSequence.exportSequence(bioSequence,"D:\\GithubRepository\\biosequence\\src\\test\\biosequence\\data/chr2.fa")

  }

  @Test
  def test04(): Unit = {

    val bioSequence = BioSequence.fromFile(inputpath1)
//    println(bioSequence.toString())
//    println(bioSequence.length)
//    println(bioSequence.information)
//    println(bioSequence.subProperty("information"))


    val bio = bioSequence.toBytes()

    val bioS = BioSequence.fromBytes(bio)
    println("---")
//    println(bioS.toString())
//    println(bioS.length)
//    println(bioS.information)
//    println(bioS.subProperty("information"))

    println("---")
    println(bioSequence.equals(bioS))
    println("___")
    BioSequence.exportSequence(bioS,"D:/GithubRepository/biosequence/src/test/biosequence/data/inputpath1_1.fa")
  }

  @Test
  def test05(): Unit = {

    val file1 = new File("D:/GithubRepository/biosequence/src/test/biosequence/data/inputpath1_.fa")
    val file2 = new File("D:/GithubRepository/biosequence/src/test/biosequence/data/inputpath1_1.fa")

    val firstDifferenceIndexOpt = findFirstDifference(file1, file2)
    firstDifferenceIndexOpt match {
      case Some(index) => println(s"The content of the files is different. First difference found at index $index.")
      case None => println("The content of the files is the same.")
    }
  }

  def findFirstDifference(file1: File, file2: File): Option[Long] = {
    val stream1 = new FileInputStream(file1)
    val stream2 = new FileInputStream(file2)

    try {
      Iterator.continually((stream1.read(), stream2.read())).zipWithIndex.find {
        case ((byte1, byte2), index) => byte1 != byte2 || byte1 == -1 || byte2 == -1
      }.map { case ((_, _), index) => index }
    } finally {
      stream1.close()
      stream2.close()
    }
  }

}
