package file

import functions.BioSequence
import file.FileUtils.writeBytesToFile
import org.junit.Test
import store.StoreSequence

import java.io.{FileOutputStream, IOException, OutputStreamWriter}

/**
 * @author cai584770
 * @date 2024/4/23 11:01
 * @Version
 */
class CompressRateTest {

  val inputFilePath = "D:\\GithubRepository\\biosequence\\src\\test\\biosequence\\data\\hg38_chr1.fa"
  val outputFilePath = "D:\\GithubRepository\\biosequence\\src\\test\\fileprocess\\data\\hg38_chr1.ab"


  @Test
  def test01():Unit= {
    val bioSequence = BioSequence.fromFile(inputFilePath)
    writeBytesToFile(bioSequence.toBytes(),outputFilePath)

    val  le = bioSequence.length
    println(le)

  }

  @Test
  def test02(): Unit = {
    val byteArray = Array[Byte](72, 101, 108, 108, 111, 32, 87, 111, 114, 108, 100)
    val fileName = "D:\\GithubRepository\\biosequence\\src\\test\\fileprocess\\data\\test.txt"

    val outputStream = new FileOutputStream(fileName)
    val writer = new OutputStreamWriter(outputStream,"gbk")

    try {
      writer.write(new String(byteArray.map(_.toChar)))
    } finally {
      writer.close()
      outputStream.close()
    }

  }

  @Test
  def test03(): Unit = {
    val filepath = inputFilePath
    val bioSequence = BioSequence.fromFile(filepath)
    val (information, sequence) = FileProcess.getInformationAndSequence(filepath)
    val (supplyInformation, streamSource) = StoreSequence.to2bit(sequence)
    val fileName = "D:\\GithubRepository\\biosequence\\src\\test\\fileprocess\\data\\test1.ab"

    val byteArray = streamSource

    try {
      val fos = new FileOutputStream(fileName)
      fos.write(byteArray)
      fos.close()
    } catch {
      case e: IOException => e.printStackTrace()
    }
  }

  @Test
  def test04(): Unit = {
    val byteArray = Array[Byte](72, 101, 108, 108, 111, 32, 87, 111, 114, 108, 100)
    val fileName = "D:\\GithubRepository\\biosequence\\src\\test\\fileprocess\\data\\test2.ab"

    try {
      val fos = new FileOutputStream(fileName)
      fos.write(byteArray)
      fos.close()
    } catch {
      case e: IOException => e.printStackTrace()
    }
  }

}
