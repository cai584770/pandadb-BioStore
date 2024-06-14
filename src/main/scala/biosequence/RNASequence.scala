package biosequence

import biopanda.sequence.BioSequenceType
import biopanda.sequence.BioSequenceType.{BioSequenceType, DNA}
import org.grapheco.lynx.cypherplus.{MimeType, MimeTypeFactory}
import org.grapheco.lynx.cypherplus.blob.{BytesInputStreamSource, InputStreamSource}
import store.StoreSequence
import utils.file.FileProcess

/**
 * @author cai584770
 * @date 2024/5/20 9:52
 * @Version
 */

case class RNASequence(information:String, supplyInformation:Map[String, List[(Any, Any)]],  streamSource: InputStreamSource, length: Long, mimeType: MimeType, bioSequenceType: BioSequenceType) extends GeneSequence

object RNASequence  extends GeneSequenceStore[RNASequence] {
   def fromFile(filePath: String):RNASequence= super.fromFile(filePath,BioSequenceType.RNA)

   def export(geneSequence: RNASequence, filePath: String): Unit = super.export(geneSequence,filePath, BioSequenceType.RNA)

  override protected def createInstance(
     information: String,
     supplyInformation: Map[String, List[(Any, Any)]],
     streamSource: InputStreamSource,
     length: Long,
     mimeType: MimeType,
     bioSequenceType: BioSequenceType
   ): RNASequence = RNASequence(information, supplyInformation, streamSource, length, mimeType, BioSequenceType.RNA)

  override protected def emptyInstance: RNASequence = null
}