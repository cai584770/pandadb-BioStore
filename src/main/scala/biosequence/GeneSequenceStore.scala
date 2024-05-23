package biosequence

import fileprocess.FileProcess
import biosequence.GeneType.{DNA, GeneType}
import org.grapheco.lynx.cypherplus.{Blob, MimeType, MimeTypeFactory}
import org.grapheco.lynx.cypherplus.blob.{BytesInputStreamSource, InputStreamSource}
import serialize.Serialize.deserializeMap
import serialize.StreamUtils
import store.{ReStoreSequence, StoreSequence}

import java.io.{BufferedWriter, File, FileWriter}
import java.nio.ByteBuffer
import java.nio.file.{Files, Paths}

/**
 * @author cai584770
 * @date 2024/5/20 10:50
 * @Version
 */
trait GeneSequenceStore[T <: GeneSequence] {
  protected def createInstance(information: String, supplyInformation: Map[String, List[(Any, Any)]], streamSource: InputStreamSource, length: Long, mimeType: MimeType,geneType: GeneType): T

  protected def emptyInstance: T
  val EMPTY: T = emptyInstance

  def fromFile(filePath: String, geneType: GeneType = GeneType.DNA):T={
    val (information, sequence) = FileProcess.getInformationAndSequence(filePath)
    val (supplyInformation, streamSource) = StoreSequence.to2bit(sequence, geneType)
    createInstance(information,
      supplyInformation,
      new BytesInputStreamSource(streamSource),
      sequence.length,
      MimeTypeFactory.fromText("application/octet-stream"),
      geneType)
  }

  def fromURL(url: String): T = {
    EMPTY
  }


  def fromBytes(bytes: Array[Byte]): T = {
    val informationLength = ByteBuffer.wrap(bytes.slice(0, 4)).getInt
    val supplyInformationLength = ByteBuffer.wrap(bytes.slice(4, 8)).getInt
    val streamSourceLength = ByteBuffer.wrap(bytes.slice(8, 12)).getInt

    val information = new String(bytes.slice(12, 12 + informationLength), "UTF-8")
    val supplyInformation = deserializeMap(bytes.slice(12 + informationLength, 12 + informationLength + supplyInformationLength))
    val streamSource = bytes.slice(12 + informationLength + supplyInformationLength, bytes.length)

    createInstance(
      information,
      supplyInformation,
      new BytesInputStreamSource(streamSource),
      supplyInformation.get("Length").flatMap(_.headOption.map {
        case (intValue: Int, _) => intValue.toLong
        case (longValue: Long, _) => longValue
        case _ => 0L
      }).getOrElse(0L),
      MimeTypeFactory.fromText("application/octet-stream"),
      GeneType.DNA
    )
  }



  def export(geneSequence: T, filePath: String, geneType: GeneType = GeneType.DNA): Unit = {
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

    val information = geneSequence.information
    val supplyInformation = geneSequence.supplyInformation
    val sequenceArrayByte = geneSequence.streamSource.offerStream(inputStream => StreamUtils.inputStreamToByteArray(inputStream))

    val sequence = ReStoreSequence.from2bit(sequenceArrayByte, supplyInformation, geneType)

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
