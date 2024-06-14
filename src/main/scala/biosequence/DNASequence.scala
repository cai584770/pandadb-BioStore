package biosequence

import biopanda.sequence.BioSequenceType.{BioSequenceType, DNA}
import biopanda.sequence.{BioSequenceType}

import org.grapheco.lynx.cypherplus.{Blob, MimeType, MimeTypeFactory}
import org.grapheco.lynx.cypherplus.blob.{BytesInputStreamSource, InputStreamSource}

/**
 * @author cai584770
 * @date 2024/5/20 10:20
 * @Version
 */
case class DNASequence(information: String, supplyInformation: Map[String, List[(Any, Any)]], streamSource: InputStreamSource, length: Long, mimeType: MimeType,bioSequenceType: BioSequenceType.Value=DNA) extends GeneSequence

object DNASequence extends GeneSequenceStore[DNASequence] {

  override protected def createInstance(
     information: String,
     supplyInformation: Map[String, List[(Any, Any)]],
     streamSource: InputStreamSource,
     length: Long,
     mimeType: MimeType,
     bioSequenceType: BioSequenceType
   ): DNASequence = DNASequence(information, supplyInformation, streamSource, length, mimeType)

  override protected def emptyInstance: DNASequence = null
}