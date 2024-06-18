package biopanda.highthroughput.entity

import biopanda.`type`.IdentifierType
import org.grapheco.lynx.types.LynxType
import org.grapheco.pandadb.plugin.AnyType
import org.grapheco.pandadb.plugin.annotations.ExtensionType
import serialize.DeSerialize.decodeIdentifier
import serialize.Serialize.encodeIdentifier

/**
 * @author cai584770
 * @date 2024/6/18 17:36
 * @Version
 */
@ExtensionType
case class Identifier(seqId: String, readNum: Int, length: Int) extends AnyType {
  override def serialize(): Array[Byte] = encodeIdentifier(seqId, readNum, length)

  override def deserialize(bytes: Array[Byte]): AnyType = decodeIdentifier(bytes)

  override def value: Any = seqId

  override def lynxType: LynxType = new IdentifierType
}
