package genesequence

import functions.BioSequence
import serialize.StreamUtils.int2BytesArray
import genesequence.GeneType.GeneType
import org.grapheco.lynx.cypherplus.{Blob, BlobExpand}
import org.grapheco.lynx.cypherplus.blob.InputStreamSource
import org.grapheco.lynx.types.LynxValue
import serialize.Serialize.serializeMap
import serialize.StreamUtils
import store.ReStoreSequence

import scala.collection.immutable.Map

/**
 * @author cai584770
 * @date 2024/5/20 9:52
 * @Version
 */
trait GeneSequence extends BlobExpand{
  val information:String
  val supplyInformation:Map[String, List[(Any, Any)]]
  val geneType:GeneType

  def getSequence: String = {
    val sequenceArrayByte = streamSource.offerStream(inputStream => StreamUtils.inputStreamToByteArray(inputStream))

    ReStoreSequence.from2bit(sequenceArrayByte, supplyInformation, geneType)
  }

  override def toBytes():Array[Byte] = {
    val informationBytes = information.getBytes("UTF-8")
    val informationLengthBytes = int2BytesArray(informationBytes.length)
    val supplyInformationBytes = serializeMap(supplyInformation)
    val supplyInformationLengthBytes = int2BytesArray(supplyInformationBytes.length)
    val streamBytes = streamSource.offerStream(inputStream => StreamUtils.inputStreamToByteArray(inputStream))
    val streamLengthBytes = int2BytesArray(streamBytes.length)

    Array.concat(informationLengthBytes, supplyInformationLengthBytes, streamLengthBytes, informationBytes, supplyInformationBytes, streamBytes)
  }

  override def subProperty(property: String): LynxValue = {
    property match {
      case "information" => LynxValue(information)
      case "supplyInformation" => LynxValue(supplyInformation)
      case "length" => LynxValue(length)
      case "mimeType" => LynxValue(mimeType)
      case _ => throw new Exception("No secondary attributes exist")
    }
  }

  override def toString(): String = s"BioSequence(information=${information},length=${length})"

}

