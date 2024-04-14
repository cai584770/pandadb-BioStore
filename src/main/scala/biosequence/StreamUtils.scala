package biosequence

import org.grapheco.lynx.cypherplus.blob.InputStreamSource

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, InputStream, ObjectOutputStream}
import java.nio.ByteBuffer

/**
 * @author cai584770
 * @date 2024/4/9 10:36
 * @Version
 */
object StreamUtils {

  /***
   * input stream to array byte
   * @param inputStream input stream
   * @return
   */
  def inputStreamToByteArray(inputStream: InputStream): Array[Byte] = {
    val buffer = new Array[Byte](1024)
    val outputStream = new ByteArrayOutputStream()


    var bytesRead = inputStream.read(buffer)

    while (bytesRead != -1) {
      outputStream.write(buffer, 0, bytesRead)
      bytesRead = inputStream.read(buffer)
    }

    outputStream.toByteArray
  }

  def int2BytesArray(intValue:Int):Array[Byte]={
    val byteBuffer: ByteBuffer = ByteBuffer.allocate(4)
    byteBuffer.putInt(intValue)
    byteBuffer.array()
  }


}

