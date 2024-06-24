package serialize

import org.grapheco.pandadb.plugin.AnyType

import java.io.{ByteArrayOutputStream, ObjectOutputStream}
import java.nio.ByteBuffer

/**
 * @author cai584770
 * @date 2024/4/13 14:14
 * @Version
 */
object Serialize {

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
