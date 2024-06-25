package org.cai.biopanda.factory

import org.cai.biopanda.extensiontype.RNASequenceType
import org.cai.biopanda.sequence.{FASTA, RNASequence}
import org.cai.exception.NoFileException
import org.grapheco.lynx.types.LynxType
import org.grapheco.pandadb.plugin.typesystem.TypeFactory

import java.nio.file.{Files, Paths}
import scala.io.Source

/**
 * @author cai584770
 * @date 2024/6/25 13:00
 * @Version
 */
object RNASequenceFactory extends TypeFactory[RNASequence]{
  override def getType: LynxType = RNASequenceType.instance

  override def fromBytes(bytes: Array[Byte]): RNASequence = new RNASequence(new String(bytes, "UTF-8"))

  override def fromString(string: String): RNASequence = new RNASequence(string)

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
    new RNASequence(fasta.getSequence)
  }
}
