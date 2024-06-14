package utils.format

import biopanda.alignment.record.SAMHeader
import htsjdk.samtools.SAMFileHeader


import java.io.{ByteArrayOutputStream, File}
import scala.collection.JavaConverters.collectionAsScalaIterableConverter

/**
 * @author cai584770
 * @date 2024/6/6 15:32
 * @Version
 */
object SAMandBAM {

  def getHeader(samHeader:SAMFileHeader):SAMHeader={
    val version = samHeader.getVersion
    val sortOrder = samHeader.getSortOrder.name()
    val sequenceDictionary = samHeader.getSequenceDictionary.getSequences.asScala.map(seq =>
      SequenceRecord(seq.getSequenceName, seq.getSequenceLength)
    ).toSeq
    val readGroups = samHeader.getReadGroups.asScala.map(rg =>
      ReadGroupRecord(rg.getReadGroupId, rg.getSample, rg.getLibrary, rg.getPlatform)
    ).toSeq
    val programRecords = samHeader.getProgramRecords.asScala.map(pr =>
      ProgramRecord(pr.getId, pr.getProgramName, pr.getCommandLine, pr.getProgramVersion)
    ).toSeq
    val comments = samHeader.getComments.asScala.toSeq

    SAMHeader(version, sortOrder, sequenceDictionary, readGroups, programRecords, comments)
  }

  /***
   * convert sam file to Byte Stream
   * @param inputSamFile input sam File
   * @return
   */
  def convertSamToBam(inputSamFile: File): Array[Byte] = {
    var samReader: SamReader = null
    var bamWriter: SAMFileWriter = null
    var byteArrayOutputStream: ByteArrayOutputStream = null

    try {
      samReader = SamReaderFactory.makeDefault().open(inputSamFile)
      val samFileHeader = samReader.getFileHeader

      byteArrayOutputStream = new ByteArrayOutputStream()
      bamWriter = new SAMFileWriterFactory().makeBAMWriter(samFileHeader, true, byteArrayOutputStream)

      val samIterator = samReader.iterator()
      while (samIterator.hasNext) {
        bamWriter.addAlignment(samIterator.next())
      }

      byteArrayOutputStream.toByteArray
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
  }



}
