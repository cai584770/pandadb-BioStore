package org.cai.biopanda.factory

import org.cai.biopanda.extensiontype.FASTAType
import org.cai.biopanda.sequence.BioSequenceEnum.{BioSequenceType, DNA, Protein, RNA}
import org.cai.biopanda.sequence.{FASTA, FASTAImpl}
import org.cai.exception.BioSequenceTypeException
import org.cai.serialize.DeSerialize.decodeFASTA
import org.cai.store.StoreSequence
import org.cai.utils.file.{FileNormalize, FileProcess}
import org.grapheco.lynx.types.LynxType
import org.grapheco.pandadb.plugin.typesystem.TypeFactory

import java.io.{BufferedWriter, FileWriter}
import java.nio.charset.StandardCharsets

/**
 * @author cai584770
 * @date 2024/6/25 13:01
 * @Version
 */
object FASTAFactory extends TypeFactory[FASTA]{
  override def getType: LynxType = FASTAType.instance

  override def fromBytes(bytes: Array[Byte]): FASTA = decodeFASTA(bytes)

  override def fromString(string: String): FASTA = {
    new FASTAImpl("",None,string.getBytes("UTF-8"))
  }

  def fromFile(filepath: String,bioSequenceType: BioSequenceType = DNA): FASTA = {
    val (information, sequence) = FileProcess.getInformationAndSequence(filepath)

    bioSequenceType match {
      case DNA | RNA =>
        val (supplyInformation, streamSource) = StoreSequence.to2bit(sequence, bioSequenceType)
        new FASTAImpl(information.substring(1), Some(supplyInformation), streamSource, bioSequenceType)
      case Protein =>
        new FASTAImpl(information.substring(1), None, sequence.getBytes(StandardCharsets.UTF_8), bioSequenceType)
      case _ => throw new BioSequenceTypeException
    }
  }

  def export(fasta: FASTA, outFilePath: String): Unit = {
    val identificationLine = ">" + fasta.getInformation + "\n"

    val sequence = fasta.getSequence

    val writer = new BufferedWriter(new FileWriter(outFilePath))
    try {
      writer.write(identificationLine)
      writer.write(FileNormalize.insertNewlines(sequence, 79))
    } finally {
      writer.close()
    }

  }


}
