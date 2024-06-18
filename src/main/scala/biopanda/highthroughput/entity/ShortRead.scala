package biopanda.highthroughput.entity

import biopanda.`type`.ShortReadType
import org.grapheco.lynx.types.LynxType
import org.grapheco.pandadb.plugin.AnyType
import org.grapheco.pandadb.plugin.annotations.ExtensionType

/**
 * @author cai584770
 * @date 2024/6/18 17:38
 * @Version
 */
@ExtensionType
case class ShortRead(read: String) extends AnyType {
  override def serialize(): Array[Byte] = read.getBytes("UTF-8")

  override def deserialize(bytes: Array[Byte]): AnyType = new ShortRead(new String(bytes,"UTF-8"))

  override def value: Any = read

  override def lynxType: LynxType = new ShortReadType
}