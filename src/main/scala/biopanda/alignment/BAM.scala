package biopanda.alignment

import biopanda.`type`.BAMType
import biopanda.alignment.record.SAMHeader
import htsjdk.samtools._
import org.grapheco.lynx.cypherplus.blob.{BytesInputStreamSource, InputStreamSource}
import org.grapheco.lynx.types.{LynxType, LynxValue}
import org.grapheco.pandadb.plugin.AnyType
import org.grapheco.pandadb.plugin.annotations.ExtensionType
import serialize.DeSerialize.decodeBAM
import serialize.Serialize.encodeBAM
import utils.format.SAMandBAM

import java.io.{ByteArrayOutputStream, File}


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

  def subProperty(property: String): LynxValue = {
    property match {
      case "header" => LynxValue(header)
      case _ => throw new Exception("No secondary attributes exist")
    }

  }


}

object BAM {
  private class BAMImpl(val header: SAMHeader, val streamSource: InputStreamSource)
    extends BAM {}

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

    new BAMImpl(SAMandBAM.getHeader(samFileHeader)),byteArrayOutputStream.toByteArray)
  }

  //  def toSam(bam: BAM): SAM = {
  //    SAM(bam.header, bam.records)
  //  }

}
