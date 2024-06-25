package org.cai.biopanda.factory

import org.cai.biopanda.extensiontype.DNASequenceType
import org.cai.biopanda.sequence.{DNASequence, FASTA}
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
object DNASequenceFactory extends TypeFactory[DNASequence]{
  override def getType: LynxType = DNASequenceType.instance

  override def fromBytes(bytes: Array[Byte]): DNASequence = new DNASequence(new String(bytes,"UTF-8"))

  override def fromString(string: String): DNASequence = new DNASequence(string)

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
    new DNASequence(fasta.getSequence)
  }

}
