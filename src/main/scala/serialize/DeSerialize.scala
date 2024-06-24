package serialize

import biopanda.sequence.Sequence
import org.grapheco.pandadb.plugin.AnyType

import java.io.{ByteArrayInputStream, ObjectInputStream}
import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets

/**
 * @author cai584770
 * @date 2024/6/12 9:30
 * @Version
 */
object DeSerialize {

  def decodeSequence(encodedData: Array[Byte]): Sequence = {
    new Sequence {
      override val seq: String = new String(encodedData,"UTF-8")
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

  def bytesToSeq[T <: AnyType](buffer: ByteBuffer, length: Int, prototype: T): Seq[T] = {
    val endPosition = buffer.position() + length
    var seq: Seq[T] = Seq()

    while (buffer.position() < endPosition) {
      val itemLength = buffer.getInt
      val itemBytes = new Array[Byte](itemLength)
      buffer.get(itemBytes)
      seq = seq :+ prototype.deserialize(itemBytes).asInstanceOf[T]
    }

    seq
  }

}
