package biopanda.alignment.record

import biopanda.`type`.SequenceRecordType
import org.grapheco.lynx.types.LynxType
import org.grapheco.pandadb.plugin.AnyType
import org.grapheco.pandadb.plugin.annotations.ExtensionType
import serialize.DeSerialize.decodeSequenceRecord
import serialize.Serialize.encodeSequenceRecord


/**
 * @author cai584770
 * @date 2024/6/12 9:35
 * @Version
 */
@ExtensionType
class SequenceRecord(name: String,length: Int) extends AnyType {

  override def serialize(): Array[Byte] = encodeSequenceRecord(name,length)

  override def deserialize(bytes: Array[Byte]): AnyType = decodeSequenceRecord(bytes)

  override def value: Any = name

  override def lynxType: LynxType = new SequenceRecordType


}
