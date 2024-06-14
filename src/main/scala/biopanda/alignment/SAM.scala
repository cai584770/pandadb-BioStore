package biopanda.alignment

import biopanda.`type`.SAMType
import biopanda.alignment
import biopanda.alignment.record.{AlignmentRecord, SAMHeader}
import htsjdk.samtools.SBIIndex.Header
import htsjdk.samtools.SamReaderFactory
import org.grapheco.lynx.types.LynxType
import org.grapheco.pandadb.plugin.AnyType
import org.grapheco.pandadb.plugin.annotations.ExtensionType
import serialize.DeSerialize.{bytesToSeq, bytesToString, decodeSAM}
import serialize.Serialize.{encodeSAM, seqToBytes}
import utils.format.SAMandBAM

import java.io.{File, PrintWriter}
import java.nio.ByteBuffer
import scala.collection.JavaConverters._

/**
 * @author cai584770
 * @date 2024/5/29 16:14
 * @Version
 */
@ExtensionType
class SAM(header: SAMHeader,records: Seq[AlignmentRecord]) extends AnyType{
  override def serialize(): Array[Byte] = encodeSAM(header,records)

  override def deserialize(bytes: Array[Byte]): SAM = decodeSAM(bytes)

  override def value: Any = header

  override def lynxType: LynxType = new SAMType
}

object SAM {
  def fromFile(file: File): SAM = {
    val samReader = SamReaderFactory.makeDefault().open(file)
    val samHeader = SAMandBAM.getHeader(samReader.getFileHeader)

    val records = samReader.iterator().asScala.map { samRecord =>
      AlignmentRecord(
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

    alignment.SAM(samHeader, records)

  }

  def exportToFile(sam: SAM, file: File): Unit = {
    val writer = new PrintWriter(file)

    try {
      writer.println(s"@HD\tVN:${sam.header.version}\tSO:${sam.header.sortOrder}")

      sam.header.sequenceDictionary.foreach { seq =>
        writer.println(s"@SQ\tSN:${seq.name}\tLN:${seq.length}")
      }

      sam.header.readGroups.foreach { rg =>
        writer.println(s"@RG\tID:${rg.id}\tSM:${rg.sample}\tLB:${rg.library}\tPL:${rg.platform}")
      }

      sam.header.programRecords.foreach { pr =>
        writer.println(s"@PG\tID:${pr.id}\tPN:${pr.programName}\tVN:${pr.version}\tCL:${pr.commandLine}")
      }

      sam.header.comments.foreach { comment =>
        writer.println(s"@CO\t${comment}")
      }

      sam.records.foreach { record =>
        val attributesStr = record.attributes.map { case (tag, value) => s"$tag:$value" }.mkString("\t")
        writer.println(s"${record.readName}\t${record.flag}\t${record.referenceName}\t${record.alignmentStart}\t${record.mappingQuality}\t${record.cigar}\t${record.mateReferenceName}\t${record.mateAlignmentStart}\t${record.inferredInsertSize}\t${record.readString}\t${record.baseQualityString}\t$attributesStr")
      }
    } finally {
      writer.close()
    }
  }



}