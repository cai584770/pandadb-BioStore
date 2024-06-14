package SAM

import biopanda.alignment.SAM
import exception.SAMNoHeadException
import org.junit.Test
import htsjdk.samtools.{BAMFileWriter, SAMFileHeader, SAMFileWriter, SAMFileWriterFactory, SAMFormatException, SAMRecord, SamReader, SamReaderFactory}
import htsjdk.variant.variantcontext.{VariantContext, VariantContextBuilder}
import htsjdk.variant.variantcontext.writer.{Options, VariantContextWriterBuilder}
import htsjdk.variant.vcf.{VCFFileReader, VCFHeader, VCFHeaderLine, VCFHeaderVersion}

import java.io.{BufferedWriter, ByteArrayOutputStream, File, FileWriter, PrintWriter}
import java.nio.file.{Files, Paths}
import java.util
import scala.collection.mutable.ArrayBuffer
import scala.io.Source


/**
 * @author cai584770
 * @date 2024/5/29 17:30
 * @Version
 */
class samtoolsTest {

  @Test
  def test00(): Unit = {
    val inputFile = new File("C:\\Users\\MSI-NB\\Desktop\\project\\files\\SRR10134981_chr1.sam")
    val outputFile = new File("C:\\Users\\MSI-NB\\Desktop\\project\\files\\SRR10134981_chr1_200.sam")

    val source = Source.fromFile(inputFile)
    val writer = new PrintWriter(outputFile)

    try {
      for (line <- source.getLines().take(200)) {
        writer.println(line)
      }
    } finally {
      // 关闭输入文件
      source.close()
      // 关闭输出文件
      writer.close()
    }

    println(s"Finished copying first 200 lines from $inputFile to $outputFile")
  }

  @Test
  def test01(): Unit = {

    val inputSam = new File("C:\\Users\\MSI-NB\\Desktop\\project\\files\\pseudo108_chr1.sam")
    val outputBam = new File("C:\\Users\\MSI-NB\\Desktop\\project\\files\\pseudo108_chr1.bam")


    val samReader = SamReaderFactory.makeDefault().open(inputSam)

    val samHeader = samReader.getFileHeader
    println(samHeader)
    if (samHeader == null) {
      throw new SAMNoHeadException()
    }

    val bamWriter = new SAMFileWriterFactory().makeBAMWriter(
      samReader.getFileHeader,
      true,
      outputBam
    )



    val samIterator = samReader.iterator()
    while (samIterator.hasNext) {
      val samRecord = samIterator.next()
      bamWriter.addAlignment(samRecord)
    }

    samIterator.close()
    samReader.close()
    bamWriter.close()

    println(s"Successfully converted ${inputSam.getName} to ${outputBam.getName}")
  }

  @Test
  def test03(): Unit = {
    val inputSamPath = "C:\\Users\\MSI-NB\\Desktop\\project\\files\\ERR250855_200.sam"
    val outputBamPath = "C:\\Users\\MSI-NB\\Desktop\\project\\files\\ERR250855_200.bam"

    val tempSamPath = "C:/Users/MSI-NB/Desktop/project/files/ERR250855_200_with_header.sam"

    val header =
      """@HD	VN:1.6	SO:coordinate
        |@SQ	SN:1	LN:248956422
        |@SQ	SN:2	LN:242193529
        |@RG	ID:GGCCGGTT	SM:sample1	PL:illumina
        |""".stripMargin

    val inputFile = new File(inputSamPath)
    val tempFile = new File(tempSamPath)
    val writer = new BufferedWriter(new FileWriter(tempFile))

    writer.write(header)

    val source = scala.io.Source.fromFile(inputFile)
    for (line <- source.getLines()) {
      writer.write(line + "\n")
    }
    writer.close()
    source.close()

    // 打开添加头部信息后的 SAM 文件
    val samReader = SamReaderFactory.makeDefault().open(tempFile)
    val samFileHeader = samReader.getFileHeader

    val samFileWriterFactory = new SAMFileWriterFactory()
    val samFileWriter = samFileWriterFactory.makeBAMWriter(samFileHeader, true, new File(outputBamPath))

    val iterator = samReader.iterator()
    while (iterator.hasNext) {
      val samRecord = iterator.next()
      samFileWriter.addAlignment(samRecord)
    }

    samFileWriter.close()
    samReader.close()

    tempFile.delete()

    println("SAM to BAM conversion completed successfully.")
  }


  @Test
  def test04(): Unit = {
    // 输入的 BAM 文件路径
    val inputBamPath = "C:\\Users\\MSI-NB\\Desktop\\project\\files\\pseudo108_chr1.bam"
    // 输出的 SAM 文件路径
    val outputSamPath = "C:\\Users\\MSI-NB\\Desktop\\project\\files\\pseudo108_chr1_converted.sam"

    // 打开输入的 BAM 文件
    val inputBam = new File(inputBamPath)
    val bamReader = SamReaderFactory.makeDefault().open(inputBam)

    // 获取 BAM 文件的头部信息
    val samHeader = bamReader.getFileHeader
    if (samHeader == null) {
      throw new SAMFormatException("BAM file header is missing or invalid.")
    }

    // 创建 SAM 文件写入器
    val samWriter = new SAMFileWriterFactory().makeSAMWriter(
      samHeader,
      true,
      new File(outputSamPath)
    )

    // 读取并写入每一个 SAMRecord
    val bamIterator = bamReader.iterator()
    while (bamIterator.hasNext) {
      val bamRecord = bamIterator.next()
      samWriter.addAlignment(bamRecord)
    }

    // 关闭读写器
    bamIterator.close()
    bamReader.close()
    samWriter.close()

    println(s"Successfully converted ${inputBam.getName} to ${new File(outputSamPath).getName}")

  }

  @Test
  def test05(): Unit = {
    // 输入的 SAM 文件路径
    val inputSamPath = "C:\\Users\\MSI-NB\\Desktop\\project\\files\\pseudo108_chr1.sam"

    // 打开输入的 SAM 文件
    val inputSam = new File(inputSamPath)
    val samReader = SamReaderFactory.makeDefault().open(inputSam)

    // 获取 SAM 文件的头部信息
    val samHeader = samReader.getFileHeader
    if (samHeader == null) {
      throw new SAMFormatException("SAM file header is missing or invalid.")
    }

    // 打印头部信息
    println("SAM Header:")
    samHeader.getTextHeader.split("\n").foreach(println)

    // 关闭读取器
    samReader.close()
  }

  @Test
  def test06(): Unit = {
    val inputSam = new File("C:\\Users\\MSI-NB\\Desktop\\project\\files\\pseudo108_chr1.sam")

    val sam:SAM = SAM.fromFile(inputSam)

//    println(sam.header)
//    println(sam.data.length)
  }

  @Test
  def test07():Unit={
//    // 输入的 BAM 文件路径
//    val inputBamPath = "C:\\Users\\MSI-NB\\Desktop\\project\\files\\pseudo108_chr1.bam"
//    val inputBam = new File(inputBamPath)
//
//    val bam = BAM.fromFile(inputBam)
//
//    println("BAM Header:")
//    println(bam.header)
//
//    println("Alignment Records (first 10):")
//    bam.records.take(10).foreach(println)

  }

  @Test
  def test08(): Unit = {
    val inputSamPath = "C:\\Users\\MSI-NB\\Desktop\\project\\files\\pseudo108_chr1.sam"
    val inputSam = new File(inputSamPath)
    val samReader = SamReaderFactory.makeDefault().open(inputSam)

    val sam = SAM.fromFile(inputSam)

    println("SAM Header:")
    println(sam.header)

    println("Alignment Records (first 10):")
//    sam.records.take(10).foreach(println)

    samReader.close()
  }

  @Test
  def test09(): Unit = {
    val inputSamPath = "C:\\Users\\MSI-NB\\Desktop\\project\\files\\pseudo108_chr1.sam"

    val byteArrayOutputStream = new ByteArrayOutputStream()
    val samReader = SamReaderFactory.makeDefault().open(Paths.get(inputSamPath).toFile)
    val samFileHeader = samReader.getFileHeader

    val bamWriter = new SAMFileWriterFactory()
      .makeBAMWriter(samFileHeader, true, byteArrayOutputStream)

    val samIterator = samReader.iterator()
    while (samIterator.hasNext) {
      val samRecord = samIterator.next()
      bamWriter.addAlignment(samRecord)
    }

    samReader.close()
    bamWriter.close()

    val bamByteArray = byteArrayOutputStream.toByteArray

    val bamArrayBuffer = ArrayBuffer[Byte]()
    bamByteArray.foreach(b => bamArrayBuffer += b)

    println(s"BAM  ArrayBuffer: ${bamArrayBuffer.size}")

    val filePath = "C:\\Users\\MSI-NB\\Desktop\\project\\files\\pseudo108_chr1.bam"
    val byteArray = Files.readAllBytes(Paths.get(filePath))

    println(s"File: ${byteArray.length}")
    println(util.Arrays.equals(bamByteArray, byteArray))
  }

  @Test
  def test10(): Unit = {
    val inputSamPath = "C:\\Users\\MSI-NB\\Desktop\\project\\files\\pseudo108_chr1.sam"
    val outputSamPath = "D:\\GithubRepository\\BioSequence\\src\\test\\SAM\\data\\pseudo108_chr1.sam"
    val sam = SAM.fromFile(new File(inputSamPath))

    println(f"sam.header.comments:${sam.header.comments}")
    println(f"sam.header.version:${sam.header.version}")
    println(f"sam.header.sortOrder:${sam.header.sortOrder}")
    println(f"sam.header.readGroups:${sam.header.readGroups}")
    println(f"sam.header.programRecords:${sam.header.programRecords}")
    println(f"sam.header.sequenceDictionary:${sam.header.sequenceDictionary}")

    println(f"sam.records.length:${sam.records.length}")

    SAM.exportToFile(sam,new File(outputSamPath))

  }

  @Test
  def test11(): Unit = {

  }

  @Test
  def test12(): Unit = {

  }

  @Test
  def test13(): Unit = {

  }


  @Test
  def test0(): Unit = {

  }
  // 将 BCF 的 VariantContext 转换为 VCF 的 VariantContext
  def convertToVcf(bcfVariantContext: VariantContext): VariantContext = {
    val builder = new VariantContextBuilder(bcfVariantContext)
    builder.unfiltered()
    builder.make()
  }

}
