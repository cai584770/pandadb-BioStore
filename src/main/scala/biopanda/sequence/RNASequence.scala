package biopanda.sequence

import biopanda.`type`.RNASequenceType
import exception.NoFileException
import org.grapheco.lynx.types.LynxType
import org.grapheco.pandadb.plugin.annotations.ExtensionType

import java.nio.file.{Files, Paths}
import scala.io.Source

/**
 * @author cai584770
 * @date 2024/6/10 11:01
 * @Version
 */
@ExtensionType
class RNASequence(sequence: String) extends Sequence {
  override val seq: String = sequence
  val bioSequenceType: BioSequenceType.Value = BioSequenceType.RNA

  override def lynxType: LynxType = new RNASequenceType


}

object RNASequence {
  def fromFile(filepath: String): RNASequence = {

    if (!Files.exists(Paths.get(filepath))) throw new NoFileException()

    val source = Source.fromFile(filepath)
    try {
      new RNASequence(source.mkString)
    } finally {
      source.close()
    }

  }

  def fromFASTA(fasta: FASTA): RNASequence = {
    new RNASequence(new String(fasta.streamSource, "UTF-8"))
  }

  def fromString(str: String): RNASequence = {
    new RNASequence(str)
  }
}