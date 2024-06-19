package biopanda.sequence

import biopanda.`type`.{DNASequenceType, ProteinSequenceType}
import biopanda.sequence.BioSequenceType.{DNA, Protein, RNA}
import exception.{BioSequenceTypeException, NoFileException}
import org.grapheco.lynx.types.LynxType
import org.grapheco.pandadb.plugin.annotations.ExtensionType
import store.ReStoreSequence.from2bit

import java.nio.file.{Files, Paths}
import scala.io.Source

/**
 * @author cai584770
 * @date 2024/6/10 11:11
 * @Version
 */
@ExtensionType
class ProteinSequence(sequence: String) extends Sequence {
  override val seq: String = sequence
  val bioSequenceType: BioSequenceType.Value = BioSequenceType.Protein

  override def lynxType: LynxType = new ProteinSequenceType

}


object ProteinSequence {

  def fromFile(filepath: String): ProteinSequence = {

    if (!Files.exists(Paths.get(filepath))) throw new NoFileException()

    val source = Source.fromFile(filepath)
    try {
      new ProteinSequence(source.mkString)
    } finally {
      source.close()
    }

  }

  def fromFASTA(fasta: FASTA): ProteinSequence = {
    new ProteinSequence(new String(fasta.streamSource, "UTF-8"))
  }

  def fromString(str: String): ProteinSequence = {
    new ProteinSequence(str)
  }


}