package biopanda.alignment

import biopanda.`type`.BAMType
import biopanda.alignment.record.SAMHeader
import htsjdk.samtools._
import org.grapheco.lynx.types.{LynxType, LynxValue}
import org.grapheco.pandadb.plugin.AnyType
import org.grapheco.pandadb.plugin.annotations.ExtensionType
import serialize.DeSerialize.decodeBAM
import serialize.Serialize.encodeBAM
import utils.format.SAMandBAM

import java.io.{ByteArrayOutputStream, File, FileOutputStream}
import scala.collection.JavaConverters.asScalaIteratorConverter


/**
 * @author cai584770
 * @date 2024/5/30 16:13
 * @Version
 */
@ExtensionType
trait BAM extends AnyType {
  val header: SAMHeader
  val streamSource: Array[Byte]

  override def serialize(): Array[Byte] = encodeBAM(header, streamSource)

  override def deserialize(bytes: Array[Byte]): BAM = decodeBAM(bytes)

  override def value: Any = header.toString

  override def lynxType: LynxType = new BAMType

}

object BAM {
  class BAMImpl(val header: SAMHeader,val streamSource: Array[Byte]) extends BAM{}

  def fromSAMFile(file: File): BAM = {
    var samReader: SamReader = null
    var bamWriter: SAMFileWriter = null
    var byteArrayOutputStream: ByteArrayOutputStream = null
    var samFileHeader: SAMFileHeader = null
    var len = 0L

    try {
      samReader = SamReaderFactory.makeDefault().open(file)
      samFileHeader = samReader.getFileHeader

      byteArrayOutputStream = new ByteArrayOutputStream()
      bamWriter = new SAMFileWriterFactory().makeBAMWriter(samFileHeader, true, byteArrayOutputStream)

      val samIterator = samReader.iterator()
      while (samIterator.hasNext) {
        len += 1
        bamWriter.addAlignment(samIterator.next())
      }
    } finally {
      if (bamWriter != null) {
        bamWriter.close()
      }
      if (samReader != null) {
        samReader.close()
      }
      if (byteArrayOutputStream != null) {
        byteArrayOutputStream.close()
      }
    }

    new BAMImpl(SAMandBAM.getHeader(samFileHeader), byteArrayOutputStream.toByteArray)
  }

  def exportToSAM(bam: BAM, outputSAMFile: File): Unit = {
    var bamReader: SamReader = null
    var samWriter: SAMFileWriter = null

    try {
      bamReader = bam.header
      val samFileHeader = bamReader.getFileHeader

      samWriter = new SAMFileWriterFactory().makeSAMWriter(samFileHeader, true, outputSAMFile)

      val bamIterator = bamReader.iterator()
      while (bamIterator.hasNext) {
        samWriter.addAlignment(bamIterator.next())
      }
    } finally {
      if (samWriter != null) {
        samWriter.close()
      }
      if (bamReader != null) {
        bamReader.close()
      }
    }
  }


  def fromBAMFile(filePath:String):BAM={
    val f = new File(filePath)
    val samReader = SamReaderFactory.makeDefault().open(f)
    val samHeader = SAMandBAM.getHeader(samReader.getFileHeader)
    val baos = new ByteArrayOutputStream()
    val samWriter = new SAMFileWriterFactory().makeBAMWriter(samReader.getFileHeader, true, baos)
    samReader.iterator().asScala.foreach(samWriter.addAlignment)
    samWriter.close()
    val streamSource = baos.toByteArray

    new BAMImpl(samHeader, streamSource)
  }

  def exportBAMToFile(bam: BAM, outputFile: File): Unit = {
    val fileOutputStream = new FileOutputStream(outputFile)
    try {
      fileOutputStream.write(bam.streamSource)
    } finally {
      fileOutputStream.close()
    }
  }

  def exportBAMToSAM(bam: BAM): SAM = {
    val samReader = SamReaderFactory.makeDefault().open(new ByteArrayInputStream(bam.streamSource))
    val header = samReader.getFileHeader
    val records = samReader.iterator().asScala.toSeq
    new SAM(header, records)
  }

  def exportBAMToSAMFile(bam: BAM, outputFile: File): Unit = {
    var samWriter: SAMFileWriter = null
    try {
      val samReader = SamReaderFactory.makeDefault().open(new ByteArrayInputStream(bam.streamSource))
      samWriter = new SAMFileWriterFactory().makeSAMWriter(samReader.getFileHeader, true, outputFile)
      val samIterator = samReader.iterator()
      while (samIterator.hasNext) {
        samWriter.addAlignment(samIterator.next())
      }
    } finally {
      if (samWriter != null) samWriter.close()
    }
  }

}
