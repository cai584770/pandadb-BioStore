package genesequence

import fileprocess.FileProcess
import genesequence.GeneType.{DNA, GeneType, RNA}
import org.grapheco.lynx.cypherplus.{MimeType, MimeTypeFactory}
import org.grapheco.lynx.cypherplus.blob.{BytesInputStreamSource, InputStreamSource}
import store.StoreSequence

/**
 * @author cai584770
 * @date 2024/5/20 9:52
 * @Version
 */

case class RNASequence(information:String, supplyInformation:Map[String, List[(Any, Any)]],  streamSource: InputStreamSource, length: Long, mimeType: MimeType, geneType: GeneType) extends GeneSequence

object RNASequence  extends GeneSequenceStore[RNASequence] {
   def fromFile(filePath: String):RNASequence= super.fromFile(filePath,RNA)

   def export(geneSequence: RNASequence, filePath: String): Unit = super.export(geneSequence,filePath, RNA)

  override protected def createInstance(
     information: String,
     supplyInformation: Map[String, List[(Any, Any)]],
     streamSource: InputStreamSource,
     length: Long,
     mimeType: MimeType,
     geneType: GeneType
   ): RNASequence = RNASequence(information, supplyInformation, streamSource, length, mimeType, RNA)

  override protected def emptyInstance: RNASequence = null
}