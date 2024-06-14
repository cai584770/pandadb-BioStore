package biopanda.alignment.record

import biopanda.`type`.SAMHeaderType
import org.grapheco.lynx.types.LynxType
import org.grapheco.pandadb.plugin.AnyType
import org.grapheco.pandadb.plugin.annotations.ExtensionType
import serialize.Serialize.{encodeSAMHeader, seqToBytes, stringToBytes}
import serialize.DeSerialize.{bytesToSeq, bytesToString, decodeSAMHeader}

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, ObjectInputStream, ObjectOutputStream}
import java.nio.ByteBuffer

/**
 * @author cai584770
 * @date 2024/6/12 9:34
 * @Version
 */
@ExtensionType
class SAMHeader(version: String, sortOrder: String, sequenceDictionary: Seq[SequenceRecord], readGroups: Seq[ReadGroupRecord], programRecords: Seq[ProgramRecord], comments: Seq[String]) extends AnyType {
  override def serialize(): Array[Byte] = encodeSAMHeader(version, sortOrder, sequenceDictionary, readGroups, programRecords, comments)

  override def deserialize(bytes: Array[Byte]): SAMHeader = decodeSAMHeader(bytes)

  override def value: Any = version

  override def lynxType: LynxType = new SAMHeaderType
}
