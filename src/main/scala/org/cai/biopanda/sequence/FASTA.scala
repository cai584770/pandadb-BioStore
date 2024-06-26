package org.cai.biopanda.sequence

import org.cai.biopanda.extensiontype.FASTAType
import org.cai.biopanda.opts.FASTAOpts
import org.cai.biopanda.sequence.BioSequenceEnum.{BioSequenceType, DNA}
import org.cai.serialize.Serialize.encodeFASTA
import org.cai.store.ReStoreSequence.from2bit
import org.grapheco.lynx.types.structural.LynxPropertyKey
import org.grapheco.lynx.types.traits.HasProperty
import org.grapheco.lynx.types.{LynxType, LynxValue}
import org.grapheco.pandadb.plugin.typesystem.AnyType

/**
 * @author cai584770
 * @date 2024/6/10 16:10
 * @Version
 */
class FASTAImpl(val information: String, val supplyInformation: Option[Map[String, List[(Any, Any)]]] = None, val streamSource: Array[Byte], val bioSequenceType: BioSequenceType = DNA) extends FASTA {
  override def getInformation: String = information

  override def getSupplyInformation: Option[Map[String, List[(Any, Any)]]] = supplyInformation

  override def toBytes: Array[Byte] = streamSource

  override def getBioSequenceType: BioSequenceType = bioSequenceType
}

abstract class FASTA extends AnyType with HasProperty {
  def getInformation: String

  def getSupplyInformation: Option[Map[String, List[(Any, Any)]]]

  def toBytes: Array[Byte]

  def getBioSequenceType: BioSequenceType

  override def equals(obj: Any): Boolean = obj match {
    case that: FASTA => this.getInformation == that.getInformation
    case _ => false
  }

  override def value: Any = this

  override def keys: Seq[LynxPropertyKey] = FASTAOpts.allPropertyNames.map(LynxPropertyKey)

  override def property(propertyKey: LynxPropertyKey): Option[LynxValue] = FASTAOpts.extractProperty(propertyKey.value)

  override def serialize: Array[Byte] = encodeFASTA(getInformation, getSupplyInformation, toBytes, getBioSequenceType)

  override def lynxType: LynxType = FASTAType.instance

  def subProperty(property: String): LynxValue = {
    property match {
      case "information" => LynxValue(getInformation)
      case "supplyInformation" => LynxValue(getSupplyInformation)
      case "length" => LynxValue(getSupplyInformation.flatMap { map =>
        map.get("length").flatMap {
          case (firstValue, _) :: _ => Some(firstValue)
          case Nil => None
        }
      }.getOrElse("FASTA File Sequence Length:0"))

      case _ => throw new Exception("No secondary attributes exist")
    }
  }

  def getSequence: String = from2bit(toBytes, getSupplyInformation.getOrElse(Map.empty),getBioSequenceType)

  override def toString: String = s"FASTA(information=${getInformation},type=${getBioSequenceType}),length=${
    getSupplyInformation.flatMap { map =>
      map.get("length").flatMap {
        case (firstValue, _) :: _ => Some(firstValue)
        case Nil => None
      }
    }.getOrElse("0")
  }"


}

