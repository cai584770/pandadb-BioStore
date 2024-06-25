package org.cai.biopanda.factory

import org.cai.biopanda.extensiontype.ProteinSequenceType
import org.cai.biopanda.sequence.{FASTA, ProteinSequence}
import org.cai.exception.NoFileException
import org.grapheco.lynx.types.LynxType
import org.grapheco.pandadb.plugin.typesystem.TypeFactory

import java.nio.file.{Files, Paths}
import scala.io.Source

/**
 * @author cai584770
 * @date 2024/6/25 13:01
 * @Version
 */
object ProteinSequenceFactory extends TypeFactory[ProteinSequence]{
  override def getType: LynxType = ProteinSequenceType.instance

  override def fromBytes(bytes: Array[Byte]): ProteinSequence = new ProteinSequence(new String(bytes, "UTF-8"))

  override def fromString(string: String): ProteinSequence = new ProteinSequence(string)

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
    new ProteinSequence(fasta.getSequence)
  }
}
