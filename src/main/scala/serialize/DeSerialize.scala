package serialize

import biopanda.alignment.BAM.BAMImpl
import biopanda.alignment.{BAM, SAM}
import biopanda.alignment.record.{AlignmentRecord, ProgramRecord, ReadGroupRecord, SAMHeader, SequenceRecord}
import biopanda.highthroughput.FASTQ
import biopanda.highthroughput.entity.{Identifier, Quality, ShortRead}
import biopanda.sequence.Sequence
import org.grapheco.pandadb.plugin.AnyType

import java.io.{ByteArrayInputStream, ObjectInputStream}
import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets

/**
 * @author cai584770
 * @date 2024/6/12 9:30
 * @Version
 */
object DeSerialize {

  def decodeFASTQ(bytes: Array[Byte]): FASTQ = {
    val buffer = ByteBuffer.wrap(bytes)

    val identifierLength = buffer.getInt()
    val identifierBytes = new Array[Byte](identifierLength)
    buffer.get(identifierBytes)
    val identifier = decodeIdentifier(identifierBytes)

    val shortReadLength = buffer.getInt()
    val shortReadBytes = new Array[Byte](shortReadLength)
    buffer.get(shortReadBytes)
    val shortRead = new ShortRead(new String(shortReadBytes,"UTF-8"))

    val qualityLength = buffer.getInt()
    val qualityBytes = new Array[Byte](qualityLength)
    buffer.get(qualityBytes)
    val quality = new Quality(new String(qualityBytes,"UTF-8")
    )

    new FASTQ(identifier, shortRead, quality)
  }

  def decodeIdentifier(bytes: Array[Byte]): Identifier = {
    val buffer = ByteBuffer.wrap(bytes)

    val seqIdLength = buffer.getInt()
    val seqIdBytes = new Array[Byte](seqIdLength)
    buffer.get(seqIdBytes)
    val seqId = new String(seqIdBytes, StandardCharsets.UTF_8)

    val readNum = buffer.getInt()
    val length = buffer.getInt()

    new Identifier(seqId,readNum,length)
  }

  def decodeSequence(encodedData: Array[Byte]): Sequence = {
    new Sequence {
      override val seq: String = new String(encodedData,"UTF-8")
    }
  }

  def decodeBAM(encodedData: Array[Byte]): BAM = {
    val buffer = ByteBuffer.wrap(encodedData)

    val headerLength = buffer.getInt()

    val headerAB = new Array[Byte](headerLength)
    buffer.get(headerAB)

    val header = decodeSAMHeader(headerAB)

    val streamSourceLength = buffer.getInt()

    val streamSource = new Array[Byte](streamSourceLength)
    buffer.get(streamSource)

    new BAMImpl(header,streamSource)
  }


  def decodeSAM(bytes: Array[Byte]): SAM = {
    val buffer = ByteBuffer.wrap(bytes)

    val headerLength = buffer.getInt
    val headerBytes = new Array[Byte](headerLength)
    buffer.get(headerBytes)
    val header = decodeSAMHeader(headerBytes)

    val recordsLength = buffer.getInt
    val record:AlignmentRecord = null
    val records = bytesToSeq(buffer, recordsLength, record)

    new SAM(header, records)
  }

  def decodeSAMHeader(bytes: Array[Byte]):SAMHeader={
    val buffer = ByteBuffer.wrap(bytes)

    val versionLength = buffer.getInt
    val version = bytesToString(buffer, versionLength)

    val sortOrderLength = buffer.getInt
    val sortOrder = bytesToString(buffer, sortOrderLength)

    val sequenceDictionaryLength = buffer.getInt
    val sequenceDictionary = bytesToSeq(buffer, sequenceDictionaryLength, new SequenceRecord("", 0))

    val readGroupsLength = buffer.getInt
    val readGroups = bytesToSeq(buffer, readGroupsLength, new ReadGroupRecord("", "", "", ""))
    val programRecordsLength = buffer.getInt
    val programRecords = bytesToSeq(buffer, programRecordsLength, new ProgramRecord("", "", "", ""))

    val commentsLength = buffer.getInt
    val comments = bytesToSeq(buffer, commentsLength, "").map(_.asInstanceOf[String])

    new SAMHeader(version, sortOrder, sequenceDictionary, readGroups, programRecords, comments)
  }

  def decodeAlignmentRecord(bytes: Array[Byte]): AlignmentRecord = {
    val buffer = ByteBuffer.wrap(bytes)

    def bytesToString(length: Int): String = {
      val bytes = new Array[Byte](length)
      buffer.get(bytes)
      new String(bytes, "UTF-8")
    }

    val readNameLength = buffer.getInt
    val readName = bytesToString(readNameLength)

    val flag = buffer.getInt

    val referenceNameLength = buffer.getInt
    val referenceName = bytesToString(referenceNameLength)

    val alignmentStart = buffer.getInt
    val mappingQuality = buffer.getInt

    val cigarLength = buffer.getInt
    val cigar = bytesToString(cigarLength)

    val mateReferenceNameLength = buffer.getInt
    val mateReferenceName = bytesToString(mateReferenceNameLength)

    val mateAlignmentStart = buffer.getInt
    val inferredInsertSize = buffer.getInt

    val readStringLength = buffer.getInt
    val readString = bytesToString(readStringLength)

    val baseQualityStringLength = buffer.getInt
    val baseQualityString = bytesToString(baseQualityStringLength)

    val attributesSize = buffer.getInt
    val attributes = (1 to attributesSize).map { _ =>
      val keyLength = buffer.getInt
      val key = bytesToString(keyLength)

      val valueLength = buffer.getInt
      val value = bytesToString(valueLength)

      key -> value
    }.toMap

    new AlignmentRecord(readName, flag, referenceName, alignmentStart, mappingQuality, cigar, mateReferenceName, mateAlignmentStart, inferredInsertSize, readString, baseQualityString, attributes)
  }

  def decodeProgramRecord(bytes: Array[Byte]): ProgramRecord = {
    val buffer = ByteBuffer.wrap(bytes)

    val idLength = buffer.getInt
    val idBytes = new Array[Byte](idLength)
    buffer.get(idBytes)
    val id = new String(idBytes, "UTF-8")

    val programNameLength = buffer.getInt
    val programNameBytes = new Array[Byte](programNameLength)
    buffer.get(programNameBytes)
    val programName = new String(programNameBytes, "UTF-8")

    val commandLineLength = buffer.getInt
    val commandLineBytes = new Array[Byte](commandLineLength)
    buffer.get(commandLineBytes)
    val commandLine = new String(commandLineBytes, "UTF-8")

    val versionLength = buffer.getInt
    val versionBytes = new Array[Byte](versionLength)
    buffer.get(versionBytes)
    val version = new String(versionBytes, "UTF-8")

    new ProgramRecord(id, programName, commandLine, version)
  }

  def decodeReadGroupRecord(bytes: Array[Byte]): ReadGroupRecord = {
    val buffer = ByteBuffer.wrap(bytes)

    val idLength = buffer.getInt
    val idBytes = new Array[Byte](idLength)
    buffer.get(idBytes)
    val id = new String(idBytes, "UTF-8")

    val sampleLength = buffer.getInt
    val sampleBytes = new Array[Byte](sampleLength)
    buffer.get(sampleBytes)
    val sample = new String(sampleBytes, "UTF-8")

    val libraryLength = buffer.getInt
    val libraryBytes = new Array[Byte](libraryLength)
    buffer.get(libraryBytes)
    val library = new String(libraryBytes, "UTF-8")

    val platformLength = buffer.getInt
    val platformBytes = new Array[Byte](platformLength)
    buffer.get(platformBytes)
    val platform = new String(platformBytes, "UTF-8")

    new ReadGroupRecord(id, sample, library, platform)
  }

  def decodeSequenceRecord(bytes: Array[Byte]): SequenceRecord = {
    val buffer = ByteBuffer.wrap(bytes)

    val nameLength = buffer.getInt
    val nameBytes = new Array[Byte](nameLength)
    buffer.get(nameBytes)

    val name = new String(nameBytes, "UTF-8")
    val length = buffer.getInt

    new SequenceRecord(name, length)
  }


  def deserializeMap(bytes: Array[Byte]): Map[String, List[(Any, Any)]] = {
    val objectInputStream = new ObjectInputStream(new ByteArrayInputStream(bytes))
    val map = objectInputStream.readObject().asInstanceOf[Map[String, List[(Any, Any)]]]
    objectInputStream.close()
    map
  }

  def bytesToString(buffer: ByteBuffer, length: Int): String = {
    val bytes = new Array[Byte](length)
    buffer.get(bytes)
    new String(bytes, "UTF-8")
  }

  def bytesToSeq[T <: AnyType](buffer: ByteBuffer, length: Int, prototype: T): Seq[T] = {
    val endPosition = buffer.position() + length
    var seq: Seq[T] = Seq()

    while (buffer.position() < endPosition) {
      val itemLength = buffer.getInt
      val itemBytes = new Array[Byte](itemLength)
      buffer.get(itemBytes)
      seq = seq :+ prototype.deserialize(itemBytes).asInstanceOf[T]
    }

    seq
  }

}
