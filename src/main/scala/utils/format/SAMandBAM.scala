package utils.format

import biopanda.alignment.record.SAMHeader.SAMHeaderImpl
import biopanda.alignment.record.{ProgramRecord, ReadGroupRecord, SAMHeader, SequenceRecord}
import htsjdk.samtools.{SAMFileHeader, SAMFileWriter, SAMFileWriterFactory, SAMSequenceRecord, SamReader, SamReaderFactory}

import java.io.{ByteArrayOutputStream, File}
import scala.collection.JavaConverters.collectionAsScalaIterableConverter

/**
 * @author cai584770
 * @date 2024/6/6 15:32
 * @Version
 */
object SAMandBAM {

  /***
   * getSAMHeader from SAMFileHeader
   * @param samHeader
   * @return
   */
  def getHeader(samHeader:SAMFileHeader):SAMHeader={
    val version = samHeader.getVersion
    val sortOrder = samHeader.getSortOrder.name()
    val sequenceDictionary = samHeader.getSequenceDictionary.getSequences.asScala.map(seq =>
      new SequenceRecord(seq.getSequenceName, seq.getSequenceLength)
    ).toSeq
    val readGroups = samHeader.getReadGroups.asScala.map(rg =>
      new ReadGroupRecord(rg.getReadGroupId, rg.getSample, rg.getLibrary, rg.getPlatform)
    ).toSeq
    val programRecords = samHeader.getProgramRecords.asScala.map(pr =>
      new ProgramRecord(pr.getId, pr.getProgramName, pr.getCommandLine, pr.getProgramVersion)
    ).toSeq
    val comments = samHeader.getComments.asScala.toSeq

     new SAMHeaderImpl(version, sortOrder, sequenceDictionary, readGroups, programRecords, comments)
  }

//  def fromSAMHeader(samHeader: SAMHeader): SAMFileHeader = {
//    val header = new SAMFileHeader()
//
//    header.setSortOrder(SAMFileHeader.SortOrder.valueOf(samHeader.sortOrder))
//
//    val sequenceRecords = samHeader.sequences.map { seq =>
//      new SAMSequenceRecord(seq.name, seq.length)
//    }.asJava
//    header.setSequenceDictionary(new SAMSequenceDictionary(sequenceRecords))
//
//    val readGroupRecords = samHeader.readGroups.map { rg =>
//      val readGroup = new SAMReadGroupRecord(rg.id)
//      readGroup.setSample(rg.sample)
//      readGroup.setLibrary(rg.library)
//      readGroup.setPlatform(rg.platform)
//      readGroup
//    }.asJava
//    header.setReadGroups(readGroupRecords)
//
//    val programRecords = samHeader.programRecords.map { pr =>
//      val programRecord = new SAMProgramRecord(pr.id)
//      programRecord.setProgramName(pr.programName)
//      programRecord.setCommandLine(pr.commandLine)
//      programRecord.setProgramVersion(pr.programVersion)
//      programRecord
//    }.asJava
//    header.setProgramRecords(programRecords)
//
//    val comments = samHeader.comments.asJava
//    header.setComments(comments)
//
//    val versionField = classOf[SAMFileHeader].getDeclaredField("version")
//    versionField.setAccessible(true)
//    versionField.set(header, samHeader.version)
//
//    header
//  }


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
