package biopanda.highthroughput.entity

import biopanda.`type`.{QualityType, ShortReadType}
import org.grapheco.lynx.types.LynxType
import org.grapheco.pandadb.plugin.AnyType
import org.grapheco.pandadb.plugin.annotations.ExtensionType

/**
 * @author cai584770
 * @date 2024/6/18 17:37
 * @Version
 */
@ExtensionType
case class Quality(quality: String) extends AnyType {
  override def serialize(): Array[Byte] = quality.getBytes("UTF-8")

  override def deserialize(bytes: Array[Byte]): AnyType = new Quality(new String(bytes,"UTF-8"))

  override def value: Any = quality

  override def lynxType: LynxType = new QualityType
}

