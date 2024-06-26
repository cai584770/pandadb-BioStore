package org.cai.biopanda.sequence

import org.cai.biopanda.opts.BioSequenceOpts
import org.cai.serialize.Serialize.encodeString
import org.grapheco.lynx.types.structural.LynxPropertyKey
import org.grapheco.lynx.types.traits.HasProperty
import org.grapheco.lynx.types.{LynxType, LynxValue}
import org.grapheco.pandadb.plugin.typesystem.AnyType

/**
 * @author cai584770
 * @date 2024/6/10 11:13
 * @Version
 */
class BioSequenceImpl(seq: String) extends BioSequence {

  override def getSequence: String = seq

}

abstract class BioSequence extends AnyType with HasProperty {
  def getSequence: String

  override def toString: String = s"biosequence(length=${getSequence.length}, sequence:" +
    (if (getSequence.length > 97) getSequence.substring(0, 97) + "...)" else getSequence + ")")

  override def equals(obj: Any): Boolean = obj match {
    case that: BioSequence => this.getSequence == that.getSequence
    case _ => false
  }

  override def serialize(): Array[Byte] = encodeString(getSequence)

  override def value: Any = this

  override def lynxType: LynxType = org.cai.biopanda.extensiontype.BioSequenceType.instance

  override def keys: Seq[LynxPropertyKey] = BioSequenceOpts.allPropertyNames.map(LynxPropertyKey)

  override def property(propertyKey: LynxPropertyKey): Option[LynxValue] = BioSequenceOpts.extractProperty(propertyKey.value)


}

