package biopanda.alignment.record

import biopanda.`type`.ReadGroupRecordType
import org.grapheco.lynx.types.LynxType
import org.grapheco.pandadb.plugin.AnyType
import org.grapheco.pandadb.plugin.annotations.ExtensionType
import serialize.DeSerialize.decodeReadGroupRecord
import serialize.Serialize.encodeReadGroupRecord

/**
 * @author cai584770
 * @date 2024/6/12 9:36
 * @Version
 */
@ExtensionType
class ReadGroupRecord(id: String, sample: String, library: String, platform: String
                     ) extends AnyType {

  override def serialize(): Array[Byte] = encodeReadGroupRecord(id, sample, library, platform)

  override def deserialize(bytes: Array[Byte]): AnyType = decodeReadGroupRecord(bytes)

  override def value: Any = sample

  override def lynxType: LynxType = new ReadGroupRecordType
}
