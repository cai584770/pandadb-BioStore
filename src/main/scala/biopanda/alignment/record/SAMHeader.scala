package biopanda.alignment.record

import biopanda.`type`.SAMHeaderType
import org.grapheco.lynx.types.LynxType
import org.grapheco.pandadb.plugin.AnyType
import org.grapheco.pandadb.plugin.annotations.ExtensionType
import serialize.DeSerialize.decodeSAMHeader
import serialize.Serialize.encodeSAMHeader


/**
 * @author cai584770
 * @date 2024/6/12 9:34
 * @Version
 */
@ExtensionType
trait SAMHeader extends AnyType {
  val version: String
  val sortOrder: String
  val sequenceDictionary: Seq[SequenceRecord]
  val readGroups: Seq[ReadGroupRecord]
  val programRecords: Seq[ProgramRecord]
  val comments: Seq[String]

  override def serialize(): Array[Byte] = encodeSAMHeader(version, sortOrder, sequenceDictionary, readGroups, programRecords, comments)

  override def deserialize(bytes: Array[Byte]): SAMHeader = decodeSAMHeader(bytes)

  override def value: Any = version

  override def lynxType: LynxType = new SAMHeaderType



}

object SAMHeader{
  class SAMHeaderImpl(val version: String,val sortOrder: String,val sequenceDictionary: Seq[SequenceRecord], val readGroups: Seq[ReadGroupRecord],val programRecords: Seq[ProgramRecord],val comments: Seq[String]) extends SAMHeader{}

}