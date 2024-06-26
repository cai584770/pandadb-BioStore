package org.cai.serialize

import org.cai.biopanda.sequence.BioSequenceEnum.{BioSequenceType, DNA, Protein, RNA}
import org.cai.serialize.StreamUtils.{int2BytesArray, long2ByteArray}
import org.grapheco.pandadb.plugin.typesystem.AnyType

import java.io.{ByteArrayOutputStream, ObjectOutputStream}
import java.nio.ByteBuffer

/**
 * @author cai584770
 * @date 2024/4/13 14:14
 * @Version
 */
object Serialize {

  def encodeFASTA(information: String, supplyInformation: Option[Map[String, List[(Any, Any)]]] = None, streamSource: Array[Byte], bioSequenceType: BioSequenceType = DNA): Array[Byte] = {
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

  def encodeString(sequence: String): Array[Byte] = {
    sequence.getBytes("UTF-8")
  }

  def encodeSequence(sequence:String):Array[Byte]={
    sequence.getBytes("UTF-8")
  }

  def mapToBytes(map: Map[String, List[(Any, Any)]]): Array[Byte] = {
    val byteArrayOutputStream = new ByteArrayOutputStream()
    val objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)
    objectOutputStream.writeObject(map)
    objectOutputStream.close()
    byteArrayOutputStream.toByteArray
  }

  def stringToBytes(str: String): (Int, Array[Byte]) = {
    val bytes = str.getBytes("UTF-8")
    (bytes.length, bytes)
  }

  def seqToBytes[T <: AnyType](seq: Seq[T]): Array[Byte] = {
    seq.flatMap { item =>
      val itemBytes = item.serialize()
      val itemLength = itemBytes.length
      ByteBuffer.allocate(4).putInt(itemLength).array() ++ itemBytes
    }.toArray
  }


}
