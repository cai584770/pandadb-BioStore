package org.cai.serialize

import org.cai.biopanda.sequence.BioSequenceEnum.{DNA, Protein, RNA}
import org.cai.biopanda.sequence.{BioSequence, FASTA, FASTAImpl}
import org.cai.exception.BioSequenceTypeException
import org.grapheco.pandadb.plugin.typesystem.AnyType

import java.io.{ByteArrayInputStream, ObjectInputStream}
import java.nio.ByteBuffer

/**
 * @author cai584770
 * @date 2024/6/12 9:30
 * @Version
 */
object DeSerialize {

  def decodeFASTA(bytes: Array[Byte]): FASTA = {
    val sequenceType: Byte = bytes(0)
    val informationLength = ByteBuffer.wrap(bytes.slice(1, 5)).getInt
    val supplyInformationLength = ByteBuffer.wrap(bytes.slice(5, 9)).getInt
    val streamSourceLength = ByteBuffer.wrap(bytes.slice(9, 17)).getLong

    val information = new String(bytes.slice(17, 17 + informationLength), "UTF-8")
    val supplyInformation = deserializeMap(bytes.slice(17 + informationLength, 17 + informationLength + supplyInformationLength))
    val streamSource = bytes.slice(17 + informationLength + supplyInformationLength, bytes.length)

    new FASTAImpl(information,Some(supplyInformation),streamSource, sequenceType match {
      case 0 => DNA
      case 1 => RNA
      case 2 => Protein
      case _ => throw new BioSequenceTypeException
    })
  }

  def decodeBioSequence(bytes: Array[Byte]): BioSequence = {
    new BioSequence() {
      override def getSequence: String = new String(bytes, "UTF-8")
    }
  }

  def deserializeMap(bytes: Array[Byte]): Map[String, List[(Any, Any)]] = {
    val objectInputStream = new ObjectInputStream(new ByteArrayInputStream(bytes))
    val map = objectInputStream.readObject().asInstanceOf[Map[String, List[(Any, Any)]]]
    objectInputStream.close()
    map
  }

  def bytesToString(buffer: ByteBuffer, length: Int): String = {
    val bytes = new Array[Byte](length)
    buffer.get(bytes)
    new String(bytes, "UTF-8")
  }


}
