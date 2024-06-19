package biopanda.alignment

import biopanda.`type`.SAMType
import biopanda.alignment.record.{AlignmentRecord, SAMHeader}
import htsjdk.samtools.SamReaderFactory
import org.grapheco.lynx.types.LynxType
import org.grapheco.pandadb.plugin.AnyType
import org.grapheco.pandadb.plugin.annotations.ExtensionType
import serialize.DeSerialize.decodeSAM
import serialize.Serialize.encodeSAM
import utils.format.SAMandBAM

import java.io.{ByteArrayInputStream, File, FileOutputStream}
import scala.collection.JavaConverters._

/**
 * @author cai584770
 * @date 2024/5/29 16:14
 * @Version
 */
@ExtensionType
class SAM(header: SAMHeader, records: Seq[AlignmentRecord]) extends AnyType {
  override def serialize(): Array[Byte] = encodeSAM(header, records)

  override def deserialize(bytes: Array[Byte]): SAM = decodeSAM(bytes)

  override def value: Any = header

  override def lynxType: LynxType = new SAMType

}

object SAM {

  def fromFile(file: File): SAM = {
    val samReader = SamReaderFactory.makeDefault().open(file)
    val samHeader = SAMandBAM.getHeader(samReader.getFileHeader)

    val records = samReader.iterator().asScala.map { samRecord =>
      new AlignmentRecord(
        readName = samRecord.getReadName,
        flag = samRecord.getFlags,
        referenceName = samRecord.getReferenceName,
        alignmentStart = samRecord.getAlignmentStart,
        mappingQuality = samRecord.getMappingQuality,
        cigar = samRecord.getCigarString,
        mateReferenceName = samRecord.getMateReferenceName,
        mateAlignmentStart = samRecord.getMateAlignmentStart,
        inferredInsertSize = samRecord.getInferredInsertSize,
        readString = samRecord.getReadString,
        baseQualityString = samRecord.getBaseQualityString,
        attributes = samRecord.getAttributes.asScala.map(attr => attr.tag -> attr.value.toString).toMap
      )
    }.toSeq

    new SAM(samHeader, records)
  }




}