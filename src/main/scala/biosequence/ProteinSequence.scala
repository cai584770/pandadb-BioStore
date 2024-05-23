package biosequence

import fileprocess.FileProcess
import org.grapheco.lynx.cypherplus.Blob.fromBytes
import org.grapheco.lynx.cypherplus.{BlobExpand, MimeType, MimeTypeFactory}
import org.grapheco.lynx.cypherplus.blob.{BytesInputStreamSource, InputStreamSource}
import org.grapheco.lynx.types.LynxValue
import serialize.StreamUtils
import serialize.StreamUtils.long2ByteArray

import java.io.{BufferedWriter, File, FileWriter}
import java.nio.ByteBuffer
import java.nio.file.{Files, Paths}

/**
 * @author cai584770
 * @date 2024/5/23 8:22
 * @Version
 */
object ProteinSequence{

  val EMPTY:ProteinSequence = fromBytes(Array[Byte]())

  def fromFile(filePath: String): ProteinSequence = {
    val (sequenceInformation, sequence) = FileProcess.getInformationAndSequence(filePath)

    new ProteinSequence {
      override val information: String = sequenceInformation
      override val length: Long = sequence.length
      override val mimeType: MimeType = MimeTypeFactory.fromText("txt/fasta")
      override val streamSource: InputStreamSource = new BytesInputStreamSource(sequence.getBytes("UTF-8"))
    }
  }

  def fromURL(url: String): ProteinSequence = {
    EMPTY
  }


  def fromBytes(bytes: Array[Byte]): ProteinSequence = {
    val informationLength = ByteBuffer.wrap(bytes.slice(0, 4)).getInt

    val informationFromBytes = new String(bytes.slice(0, informationLength), "UTF-8")
    val streamSourceFromBytes = bytes.slice(informationLength, bytes.length)

    new ProteinSequence {
      override val information: String = informationFromBytes
      override val length: Long = streamSourceFromBytes.length
      override val mimeType: MimeType = MimeTypeFactory.fromText("application/octet-stream")
      override val streamSource: InputStreamSource = new BytesInputStreamSource(streamSourceFromBytes)
    }

  }


  def export(proteinSequence: ProteinSequence, filePath: String): Unit = {
    val directoryPath = Paths.get(filePath).getParent.toString
    val directory = Paths.get(directoryPath)

    if (!Files.exists(directory)) {
      try {
        Files.createDirectories(directory)
      } catch {
        case e: Exception =>
          println(s"creat file '$filePath' failed: ${e.getMessage}")
      }
    }

    val information = proteinSequence.information
    val sequenceArrayByte = proteinSequence.streamSource.offerStream(inputStream => StreamUtils.inputStreamToByteArray(inputStream))

    val sequence = new String(sequenceArrayByte, "UTF-8")
    val result = information + "\n" + sequence

    val file = new File(filePath)
    val bw = new BufferedWriter(new FileWriter(file))
    try {
      bw.write(result)
    } finally {
      bw.close()
    }

  }
}


trait ProteinSequence extends BlobExpand{
  val information: String

  def getSequence: String = {

    null
  }

  override def toBytes(): Array[Byte] = {
    val lengthAB = long2ByteArray(length)
    val streamBytes = streamSource.offerStream(inputStream => StreamUtils.inputStreamToByteArray(inputStream))

    Array.concat(lengthAB, streamBytes)

  }

  override def subProperty(property: String): LynxValue = {
    property match {
      case "information" => LynxValue(information)
      case "length" => LynxValue(length)
      case "mimeType" => LynxValue(mimeType)
      case _ => throw new Exception("No secondary attributes exist")
    }
  }

  override def toString(): String = s"ProteinSequence(information=${information},length=${length})"


}
