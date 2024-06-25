package org.cai.biopanda.factory

import org.cai.biopanda.extensiontype.BioSequenceType
import org.cai.biopanda.sequence.{BioSequence, FASTA}
import org.cai.exception.NoFileException
import org.grapheco.lynx.types.LynxType
import org.grapheco.pandadb.plugin.typesystem.TypeFactory
import serialize.DeSerialize.decodeBioSequence

import java.nio.file.{Files, Paths}
import scala.io.Source

/**
 * @author cai584770
 * @date 2024/6/25 11:14
 * @Version
 */
object BioSequenceFactory extends TypeFactory[BioSequence] {
  override def getType: LynxType = BioSequenceType.instance

  override def fromBytes(bytes: Array[Byte]): BioSequence = decodeBioSequence(bytes)

  override def fromString(string: String): BioSequence = new BioSequence {
    override def getSequence: String = string
  }

  def fromFile(filepath: String): BioSequence = {

    if (!Files.exists(Paths.get(filepath))) throw new NoFileException()

    val source = Source.fromFile(filepath)
    try {
      new BioSequence() {
        override def getSequence: String = source.mkString
      }
    } finally {
      source.close()
    }

  }

  def fromFASTA(fasta: FASTA): BioSequence = {
    new BioSequence() {
      override def getSequence: String = fasta.getSequence
    }
  }


}
