package serialize

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, ObjectInputStream, ObjectOutputStream}

/**
 * @author cai584770
 * @date 2024/4/13 14:14
 * @Version
 */
object Serialize {
  def serializeMap(map: Map[String, List[(Any, Any)]]): Array[Byte] = {
    val byteArrayOutputStream = new ByteArrayOutputStream()
    val objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)
    objectOutputStream.writeObject(map)
    objectOutputStream.close()
    byteArrayOutputStream.toByteArray
  }

  def deserializeMap(bytes: Array[Byte]): Map[String, List[(Any, Any)]] = {
    val objectInputStream = new ObjectInputStream(new ByteArrayInputStream(bytes))
    val map = objectInputStream.readObject().asInstanceOf[Map[String, List[(Any, Any)]]]
    objectInputStream.close()
    map
  }


}
