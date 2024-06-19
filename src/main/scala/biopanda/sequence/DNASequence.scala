package biopanda.sequence

import biopanda.`type`.DNASequenceType
import exception.NoFileException
import org.grapheco.lynx.types.LynxType
import org.grapheco.pandadb.plugin.AnyType
import org.grapheco.pandadb.plugin.annotations.ExtensionType

import java.nio.file.{Files, Paths}
import scala.io.Source

/**
 * @author cai584770
 * @date 2024/6/7 10:53
 * @Version
 */S
@ExtensionType
class DNASequence(sequence: String) extends Sequence {
  override val seq: String = sequence

  val bioSequenceType: BioSequenceType.Value = BioSequenceType.DNA

  override def lynxType: LynxType = new DNASequenceType

  override def deserialize(bytes: Array[Byte]): AnyType = new DNASequence(new String(bytes, "UTF-8"))

}

object DNASequence {
  def fromFile(filepath: String): DNASequence = {
    if (!Files.exists(Paths.get(filepath))) throw new NoFileException()

    val source = Source.fromFile(filepath)
    try {
      new DNASequence(source.mkString)
    } finally {
      source.close()
    }

  }

  def fromFASTA(fasta: FASTA): DNASequence = {
    new DNASequence(new String(fasta.streamSource, "UTF-8"))
  }

  def fromString(str: String): DNASequence = {
    new DNASequence(str)
  }
}




