package biosequence

import biosequence.StreamUtils.{inputStreamToByteArray, int2BytesArray}
import fileprocess.FileProcess
import org.grapheco.lynx.cypherplus.blob.{BytesInputStreamSource, InputStreamSource}
import org.grapheco.lynx.cypherplus.{Blob, BlobExpand, MimeType, MimeTypeFactory}
import org.grapheco.lynx.types.LynxValue
import serialize.Serialize.{deserializeMap, serializeMap}
import store.{ReStoreSequence, StoreSequence}

import java.io.{BufferedWriter, ByteArrayInputStream, ByteArrayOutputStream, File, FileWriter, InputStream}
import java.nio.ByteBuffer
import java.nio.file.{Files, Paths}

/**
 * @author cai584770
 * @date 2024/4/8 15:25
 * @Version
 *
 */
class BioSequence(val information:String, val supplyInformation:Map[String, List[(Any, Any)]], val streamSource: InputStreamSource, val length: Long, val mimeType: MimeType) extends BlobExpand {

  override def toBytes(): Array[Byte] = {
    val informationBytes = information.getBytes("UTF-8")
    val informationLengthBytes = int2BytesArray(informationBytes.length)
    val supplyInformationBytes = serializeMap(supplyInformation)
    val supplyInformationLengthBytes = int2BytesArray(supplyInformationBytes.length)
    val streamBytes = streamSource.offerStream(inputStream => StreamUtils.inputStreamToByteArray(inputStream))
    val streamLengthBytes = int2BytesArray(streamBytes.length)

    Array.concat(informationLengthBytes,supplyInformationLengthBytes,streamLengthBytes,informationBytes,supplyInformationBytes,streamBytes)
  }



  override def toString(): String = s"BioSequence(information=${information},length=${length})"

  override def subProperty(property: String): LynxValue = {
    property match {
      case "information" => LynxValue(information)
      case "supplyInformation" => LynxValue(supplyInformation)
      case "length" => LynxValue(length)
      case "mimeType" => LynxValue(mimeType)
      case _ => throw new Exception("No secondary attributes exist")
    }
  }

  override def equals(obj: Any): Boolean = obj match {
    case bioSequence: BioSequence =>{
      information == bioSequence.information &&
        supplyInformation == bioSequence.supplyInformation &&
        length == bioSequence.length
    }
    case _ =>
      false
  }

}

object BioSequence{

  val EMPTY: BioSequence = null

  /***
   *  import DNAsequence from file(FASTA)
   * @param filePath filepath
   * @return
   */
  def fromFile(filePath: String): BioSequence = {
    val (information, sequence) = FileProcess.getInformationAndSequence(filePath)
    val (supplyInformation, streamSource) = StoreSequence.to2bit(sequence)
    new BioSequence(information,supplyInformation,new BytesInputStreamSource(streamSource),sequence.length,Some(MimeTypeFactory.fromText("application/octet-stream")).value)
  }

  def fromURL(url: String): BioSequence = {
    null
  }

  def fromBytes(bytes: Array[Byte]): BioSequence ={
    val informationLength = ByteBuffer.wrap(bytes.slice(0, 4)).getInt
    val supplyInformationLength = ByteBuffer.wrap(bytes.slice(4, 8)).getInt
    val streamSourceLength = ByteBuffer.wrap(bytes.slice(8, 12)).getInt

    val information = new String(bytes.slice(12, 12 + informationLength), "UTF-8")
    val supplyInformation = deserializeMap(bytes.slice(12 + informationLength, 12 + informationLength + supplyInformationLength))
    val streamSource = bytes.slice(12 + informationLength + supplyInformationLength, bytes.length)

    new BioSequence(information,supplyInformation,new BytesInputStreamSource(streamSource), supplyInformation.get("Length").flatMap(_.headOption.map {
      case (intValue: Int, _) => intValue.toLong
      case (longValue: Long, _) => longValue
      case _ => 0L
    }).getOrElse(0L),MimeTypeFactory.fromText("application/octet-stream"))

  }

  def exportSequence(bioSequence: BioSequence,filePath:String):Unit={
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

    val information = bioSequence.information
    val supplyInformation = bioSequence.supplyInformation
    val sequenceArrayByte = bioSequence.streamSource.offerStream(inputStream => StreamUtils.inputStreamToByteArray(inputStream))

    val sequence = ReStoreSequence.from2bit(sequenceArrayByte,supplyInformation)

    val result = information+"\n"+sequence

    val file = new File(filePath)
    val bw = new BufferedWriter(new FileWriter(file))
    try {
      bw.write(result)
    } finally {
      bw.close()
    }

  }



}