package org.cai.serialize

import java.io.{ByteArrayOutputStream, InputStream}
import java.nio.ByteBuffer

/**
 * @author cai584770
 * @date 2024/4/9 10:36
 * @Version
 */
object StreamUtils {



  /** *
   * input stream to array byte
   *
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

  def int2BytesArray(intValue: Int): Array[Byte] = {
    val byteBuffer: ByteBuffer = ByteBuffer.allocate(4)
    byteBuffer.putInt(intValue)
    byteBuffer.array()
  }

  def long2ByteArray(value: Long): Array[Byte] = {
    Array(
      (value >> 56).toByte,
      (value >> 48).toByte,
      (value >> 40).toByte,
      (value >> 32).toByte,
      (value >> 24).toByte,
      (value >> 16).toByte,
      (value >> 8).toByte,
      value.toByte
    )
  }



}
