package biopanda.sequence

import biopanda.`type`.DNASequenceType
import biopanda.sequence.BioSequenceType.DNA
import org.grapheco.lynx.cypherplus.MimeType
import org.grapheco.lynx.cypherplus.blob.InputStreamSource
import org.grapheco.lynx.types.LynxType
import org.grapheco.pandadb.plugin.AnyType
import org.grapheco.pandadb.plugin.annotations.ExtensionType

/**
 * @author cai584770
 * @date 2024/6/7 10:53
 * @Version
 */
@ExtensionType
class DNASequence(sequence: String) extends Sequence {
  override val seq: String = sequence

  val bioSequenceType: BioSequenceType.Value = BioSequenceType.DNA

  override def lynxType: LynxType = new DNASequenceType

  override def deserialize(bytes: Array[Byte]): AnyType = new DNASequence(new String(bytes, "UTF-8"))


//  override def toString: String = "DNA Sequence:"+(if (sequence.length > 97) (sequence.substring(0, 97)+"...") else sequence + "...")
}


