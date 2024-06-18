package biopanda.sequence

import biopanda.`type`.FASTAType
import biopanda.sequence.BioSequenceType.{BioSequenceType, DNA, Protein, RNA}
import exception.BioSequenceTypeException
import org.grapheco.lynx.types.{LynxType, LynxValue}
import org.grapheco.pandadb.plugin.AnyType
import org.grapheco.pandadb.plugin.annotations.ExtensionType
import serialize.DeSerialize.deserializeMap
import serialize.Serialize.mapToBytes
import serialize.StreamUtils.{int2BytesArray, long2ByteArray}
import store.{ReStoreSequence, StoreSequence}
import utils.file.FileProcess

import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets

/**
 * @author cai584770
 * @date 2024/6/10 16:10
 * @Version
 */

@ExtensionType
trait FASTA extends AnyType {
  val information: String
  val supplyInformation: Option[Map[String, List[(Any, Any)]]]
  val streamSource: Array[Byte]
  val bioSequenceType: BioSequenceType

  override def value: String = {
    bioSequenceType match {
      case DNA | RNA => ReStoreSequence.from2bit(streamSource, supplyInformation.getOrElse(Map.empty), bioSequenceType)
      case Protein => new String(streamSource, "UTF-8")

      case _ => throw new BioSequenceTypeException
    }

  }

  override def serialize: Array[Byte] = {
    val sequenceType: Byte = bioSequenceType match {
      case DNA => 0
      case RNA => 1
      case Protein => 2
    }

    val informationBytes = information.getBytes("UTF-8")
    val informationLengthBytes = int2BytesArray(informationBytes.length)
    val supplyInformationBytes = mapToBytes(supplyInformation.orNull)
    val supplyInformationLengthBytes = int2BytesArray(supplyInformationBytes.length)
    val streamBytes = streamSource
    val streamLengthBytes = long2ByteArray(streamBytes.length)


    Array.concat(Array(sequenceType), informationLengthBytes, supplyInformationLengthBytes, streamLengthBytes, informationBytes, supplyInformationBytes, streamBytes)
  }

  override def lynxType: LynxType = new FASTAType

  override def deserialize(bytes: Array[Byte]): AnyType = FASTA.fromBytes(bytes)

  def subProperty(property: String): LynxValue = {
    property match {
      case "information" => LynxValue(information)
      case "supplyInformation" => LynxValue(supplyInformation)
      case "length" => LynxValue(supplyInformation.flatMap { map =>
        map.get("length").flatMap {
          case (firstValue, _) :: _ => Some(firstValue)
          case Nil => None
        }
      }.getOrElse("FASTA File Sequence Length:0"))

      case _ => throw new Exception("No secondary attributes exist")
    }
  }

  override def toString: String = s"FASTA(information=${information},sequence length=${
    supplyInformation.flatMap { map =>
      map.get("length").flatMap {
        case (firstValue, _) :: _ => Some(firstValue)
        case Nil => None
      }
    }.getOrElse("0")
  },type=${bioSequenceType})"


}

object FASTA {

  private class FASTAImpl(val information: String, val supplyInformation: Option[Map[String, List[(Any, Any)]]], val streamSource: Array[Byte], val bioSequenceType: BioSequenceType) extends FASTA {
  }

  def fromFile(filePath: String, bioSequenceType: BioSequenceType = BioSequenceType.DNA): FASTA = {
    val (information, sequence) = FileProcess.getInformationAndSequence(filePath)

    bioSequenceType match {
      case DNA | RNA =>
        val (supplyInformation, streamSource) = StoreSequence.to2bit(sequence, bioSequenceType)
        new FASTAImpl(information.substring(1), Some(supplyInformation), streamSource, bioSequenceType)
      case Protein =>
        new FASTAImpl(information.substring(1), None, sequence.getBytes(StandardCharsets.UTF_8), bioSequenceType)
      case _ => throw new BioSequenceTypeException
    }

  }

  def fromBytes(bytes: Array[Byte]): FASTA = {
    val sequenceType: Byte = bytes(0)
    val informationLength = ByteBuffer.wrap(bytes.slice(1, 5)).getInt
    val supplyInformationLength = ByteBuffer.wrap(bytes.slice(5, 9)).getInt
    val streamSourceLength = ByteBuffer.wrap(bytes.slice(9, 17)).getLong

    val information = new String(bytes.slice(17, 17 + informationLength), "UTF-8")
    val supplyInformation = deserializeMap(bytes.slice(17 + informationLength, 17 + informationLength + supplyInformationLength))
    val streamSource = bytes.slice(17 + informationLength + supplyInformationLength, bytes.length)

    new FASTAImpl(information, Some(supplyInformation), streamSource, sequenceType match {
      case 0 => DNA
      case 1 => RNA
      case 2 => Protein
      case _ => throw new BioSequenceTypeException
    })

  }


}