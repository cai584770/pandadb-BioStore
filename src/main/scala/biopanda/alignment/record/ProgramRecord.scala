package biopanda.alignment.record

import biopanda.`type`.ProgramRecordType
import org.grapheco.lynx.types.LynxType
import org.grapheco.pandadb.plugin.AnyType
import org.grapheco.pandadb.plugin.annotations.ExtensionType
import serialize.DeSerialize.decodeProgramRecord
import serialize.Serialize.encodeProgramRecord

/**
 * @author cai584770
 * @date 2024/6/12 9:37
 * @Version
 */
@ExtensionType
class ProgramRecord(id: String, programName: String, commandLine: String, version: String) extends AnyType {

  override def serialize(): Array[Byte] = encodeProgramRecord(id, programName, commandLine, version)

  override def deserialize(bytes: Array[Byte]): AnyType = decodeProgramRecord(bytes)

  override def value: Any = programName

  override def lynxType: LynxType = new ProgramRecordType
}
