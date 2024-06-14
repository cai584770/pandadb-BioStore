package biopanda.alignment.record

import biopanda.`type`.AlignmentRecordType
import org.grapheco.lynx.types.LynxType
import org.grapheco.pandadb.plugin.AnyType
import org.grapheco.pandadb.plugin.annotations.ExtensionType
import serialize.DeSerialize.decodeAlignmentRecord
import serialize.Serialize.encodeAlignmentRecord


/**
 * @author cai584770
 * @date 2024/6/12 9:37
 * @Version
 */
@ExtensionType
class AlignmentRecord(readName: String, flag: Int, referenceName: String, alignmentStart: Int, mappingQuality: Int, cigar: String, mateReferenceName: String, mateAlignmentStart: Int, inferredInsertSize: Int, readString: String, baseQualityString: String, attributes: Map[String, String]) extends AnyType {

  override def value: Any = readName

  override def lynxType: LynxType = new AlignmentRecordType

  override def serialize() : Array[Byte] = encodeAlignmentRecord(readName, flag, referenceName, alignmentStart, mappingQuality, cigar, mateReferenceName, mateAlignmentStart, inferredInsertSize, readString, baseQualityString, attributes)

  override def deserialize(bytes: Array[Byte]): AnyType = decodeAlignmentRecord(bytes)

}
