package serialize

import biopanda.alignment.record.{AlignmentRecord, ProgramRecord, ReadGroupRecord, SAMHeader, SequenceRecord}
import biopanda.highthroughput.entity.{Identifier, Quality, ShortRead}
import org.grapheco.lynx.cypherplus.blob.InputStreamSource
import org.grapheco.pandadb.plugin.AnyType

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, ObjectInputStream, ObjectOutputStream}
import java.nio.ByteBuffer

/**
 * @author cai584770
 * @date 2024/4/13 14:14
 * @Version
 */
object Serialize {

  def encodeFASTQ(identifier: Identifier, shortRead: ShortRead, quality: Quality): Array[Byte] = {
    val identifierAB = identifier.serialize()
    val shortReadAB = shortRead.serialize()
    val qualityAB = quality.serialize()

    val identifierABLength = identifierAB.length
    val shortReadABLength = shortReadAB.length
    val qualityABLength = qualityAB.length

    val totalLength = 4 + identifierABLength+4 +shortReadABLength+4+qualityABLength
    val buffer = ByteBuffer.allocate(totalLength)

    buffer.putInt(identifierABLength)
    buffer.put(identifier.serialize())
    buffer.putInt(shortReadABLength)
    buffer.put(shortRead.serialize())
    buffer.putInt(qualityABLength)
    buffer.put(quality.serialize())

    buffer.array()

  }

  def encodeIdentifier(seqId: String, readNum: Int, length: Int): Array[Byte] = {
    val seqIdBytes = seqId.getBytes("UTF-8")
    val seqIdLength = seqIdBytes.length
    val totalLength = 4 + seqIdLength + 4 + 4
    val buffer = ByteBuffer.allocate(totalLength)

    buffer.putInt(seqIdLength)
    buffer.put(seqIdBytes)
    buffer.putInt(readNum)
    buffer.putInt(length)

    buffer.array()
  }

  def encodeSequence(sequence:String):Array[Byte]={
    sequence.getBytes("UTF-8")
  }

  def encodeBAM(header: SAMHeader,streamSource: Array[Byte]):Array[Byte]={
    val headerAB = header.serialize()

    val totalLength = 4 + headerAB.length + 4 + streamSource.length
    val buffer = ByteBuffer.allocate(totalLength)

    buffer.putInt(headerAB.length)
    buffer.put(headerAB)
    buffer.putInt(streamSource.length)
    buffer.put(streamSource)

    buffer.array()
  }

  def encodeSAM(header: SAMHeader,records: Seq[AlignmentRecord]): Array[Byte] = {
    val headerAB = header.serialize()
    val alignmentRecordAB = seqToBytes(records)

    val totalLength = 4 + headerAB.length + 4 + alignmentRecordAB.length
    val buffer = ByteBuffer.allocate(totalLength)

    buffer.putInt(headerAB.length)
    buffer.put(headerAB)
    buffer.putInt(alignmentRecordAB.length)
    buffer.put(alignmentRecordAB)

    buffer.array()
  }

  def encodeSAMHeader(version: String, sortOrder: String, sequenceDictionary: Seq[SequenceRecord], readGroups: Seq[ReadGroupRecord], programRecords: Seq[ProgramRecord], comments: Seq[String]): Array[Byte] = {
    val versionBytes = version.getBytes("UTF-8")
    val sortOrderBytes = sortOrder.getBytes("UTF-8")
    val sequenceDictionaryBytes = seqToBytes(sequenceDictionary)
    val readGroupsBytes = seqToBytes(readGroups)
    val programRecordsBytes = seqToBytes(programRecords)
    val commentsBytes = seqToBytes(comments)

    val totalLength = 4 + versionBytes.length +
      4 + sortOrderBytes.length +
      4 + sequenceDictionaryBytes.length +
      4 + readGroupsBytes.length +
      4 + programRecordsBytes.length +
      4 + commentsBytes.length

    val buffer = ByteBuffer.allocate(totalLength)

    buffer.putInt(versionBytes.length)
    buffer.put(versionBytes)
    buffer.putInt(sortOrderBytes.length)
    buffer.put(sortOrderBytes)
    buffer.putInt(sequenceDictionaryBytes.length)
    buffer.put(sequenceDictionaryBytes)
    buffer.putInt(readGroupsBytes.length)
    buffer.put(readGroupsBytes)
    buffer.putInt(programRecordsBytes.length)
    buffer.put(programRecordsBytes)
    buffer.putInt(commentsBytes.length)
    buffer.put(commentsBytes)

    buffer.array()
  }

  def encodeAlignmentRecord(readName: String, flag: Int, referenceName: String, alignmentStart: Int, mappingQuality: Int, cigar: String, mateReferenceName: String, mateAlignmentStart: Int, inferredInsertSize: Int, readString: String, baseQualityString: String, attributes: Map[String, String]): Array[Byte] = {
    val attributesBytes = attributes.flatMap { case (k, v) =>
      val (keyLength, keyBytes) = Serialize.stringToBytes(k)
      val (valueLength, valueBytes) = Serialize.stringToBytes(v)
      Array.concat(
        ByteBuffer.allocate(4).putInt(keyLength).array(),
        keyBytes,
        ByteBuffer.allocate(4).putInt(valueLength).array(),
        valueBytes
      )
    }.toArray

    val (readNameLength, readNameBytes) = stringToBytes(readName)
    val (referenceNameLength, referenceNameBytes) = stringToBytes(referenceName)
    val (cigarLength, cigarBytes) = stringToBytes(cigar)
    val (mateReferenceNameLength, mateReferenceNameBytes) = stringToBytes(mateReferenceName)
    val (readStringLength, readStringBytes) = stringToBytes(readString)
    val (baseQualityStringLength, baseQualityStringBytes) = stringToBytes(baseQualityString)

    val totalLength = 4 + readNameBytes.length +
      4 + referenceNameBytes.length +
      4 + cigarBytes.length +
      4 + mateReferenceNameBytes.length +
      4 + readStringBytes.length +
      4 + baseQualityStringBytes.length +
      4 * 5 +
      4 + attributesBytes.length

    val buffer = ByteBuffer.allocate(totalLength)

    buffer.putInt(readNameLength)
    buffer.put(readNameBytes)
    buffer.putInt(flag)
    buffer.putInt(referenceNameLength)
    buffer.put(referenceNameBytes)
    buffer.putInt(alignmentStart)
    buffer.putInt(mappingQuality)
    buffer.putInt(cigarLength)
    buffer.put(cigarBytes)
    buffer.putInt(mateReferenceNameLength)
    buffer.put(mateReferenceNameBytes)
    buffer.putInt(mateAlignmentStart)
    buffer.putInt(inferredInsertSize)
    buffer.putInt(readStringLength)
    buffer.put(readStringBytes)
    buffer.putInt(baseQualityStringLength)
    buffer.put(baseQualityStringBytes)

    buffer.putInt(attributes.size)
    buffer.put(attributesBytes)

    buffer.array()
  }

  def encodeProgramRecord(id: String,programName: String,commandLine: String,version: String): Array[Byte] = {
    val idBytes = id.getBytes("UTF-8")
    val idLength = idBytes.length

    val programNameBytes = programName.getBytes("UTF-8")
    val programNameLength = programNameBytes.length

    val commandLineBytes = commandLine.getBytes("UTF-8")
    val commandLineLength = commandLineBytes.length

    val versionBytes = version.getBytes("UTF-8")
    val versionLength = versionBytes.length

    val buffer = ByteBuffer.allocate(4 + idLength + 4 + programNameLength + 4 + commandLineLength + 4 + versionLength)

    buffer.putInt(idLength)
    buffer.put(idBytes)
    buffer.putInt(programNameLength)
    buffer.put(programNameBytes)
    buffer.putInt(commandLineLength)
    buffer.put(commandLineBytes)
    buffer.putInt(versionLength)
    buffer.put(versionBytes)

    buffer.array()
  }


  def encodeReadGroupRecord(id: String, sample: String, library: String, platform: String): Array[Byte] = {
    val idBytes = id.getBytes("UTF-8")
    val idLength = idBytes.length

    val sampleBytes = sample.getBytes("UTF-8")
    val sampleLength = sampleBytes.length

    val libraryBytes = library.getBytes("UTF-8")
    val libraryLength = libraryBytes.length

    val platformBytes = platform.getBytes("UTF-8")
    val platformLength = platformBytes.length

    val buffer = ByteBuffer.allocate(4 + idLength + 4 + sampleLength + 4 + libraryLength + 4 + platformLength)

    buffer.putInt(idLength)
    buffer.put(idBytes)
    buffer.putInt(sampleLength)
    buffer.put(sampleBytes)
    buffer.putInt(libraryLength)
    buffer.put(libraryBytes)
    buffer.putInt(platformLength)
    buffer.put(platformBytes)

    buffer.array()
  }

  def encodeSequenceRecord(name: String,length: Int): Array[Byte] = {
    val nameBytes = name.getBytes("UTF-8")
    val nameLength = nameBytes.length

    val buffer = ByteBuffer.allocate(4 + nameLength + 4)
    buffer.putInt(nameLength)
    buffer.put(nameBytes)
    buffer.putInt(length)

    buffer.array()
  }

  def mapToBytes(map: Map[String, List[(Any, Any)]]): Array[Byte] = {
    val byteArrayOutputStream = new ByteArrayOutputStream()
    val objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)
    objectOutputStream.writeObject(map)
    objectOutputStream.close()
    byteArrayOutputStream.toByteArray
  }

  def stringToBytes(str: String): (Int, Array[Byte]) = {
    val bytes = str.getBytes("UTF-8")
    (bytes.length, bytes)
  }


  def seqToBytes[T <: AnyType](seq: Seq[T]): Array[Byte] = {
    seq.flatMap { item =>
      val itemBytes = item.serialize()
      val itemLength = itemBytes.length
      ByteBuffer.allocate(4).putInt(itemLength).array() ++ itemBytes
    }.toArray
  }

  def seqToBytes(seq: Seq[String]): Array[Byte] = {
    seq.flatMap { item =>
      val (itemLength,itemBytes) = stringToBytes(item)
      ByteBuffer.allocate(4).putInt(itemLength).array() ++ itemBytes
    }.toArray
  }

}
