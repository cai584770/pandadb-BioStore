package biopanda.sequence

import biopanda.`type`.{DNASequenceType, SequenceType}
import biopanda.sequence.BioSequenceType.{DNA, Protein, RNA}
import biopanda.sequence.FASTA.FASTAImpl
import exception.{BioSequenceTypeException, NoFileException}
import org.grapheco.lynx.cypherplus.Blob
import org.grapheco.lynx.types.LynxType
import org.grapheco.pandadb.plugin.AnyType
import org.grapheco.pandadb.plugin.annotations.ExtensionType
import serialize.DeSerialize.decodeSequence
import serialize.Serialize.encodeSequence
import store.ReStoreSequence.from2bit
import store.StoreSequence

import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Paths}
import scala.io.Source


/**
 * @author cai584770
 * @date 2024/6/10 11:13
 * @Version
 */
@ExtensionType
trait Sequence extends AnyType{
  val seq:String

  override def value: Any = seq

  override def lynxType: LynxType = new SequenceType

  override def serialize(): Array[Byte] = encodeSequence(seq)

  override def deserialize(bytes: Array[Byte]): AnyType = decodeSequence(bytes)

  override def toString: String = f"${this.getClass.getSimpleName}:"+(if (seq.length > 97) (seq.substring(0, 97)+"...") else seq + "...")

}

object Sequence{

  private class SequenceImpl(val seq:String)extends Sequence

  def fromFile(filepath:String):Sequence={

    if(!Files.exists(Paths.get(filepath))) throw new NoFileException()

    val source = Source.fromFile(filepath)
    try {
      new SequenceImpl(source.mkString)
    } finally {
      source.close()
    }

  }

  def fromFASTA(fasta: FASTA):Sequence={
    fasta.bioSequenceType match {
      case DNA | RNA =>
        new SequenceImpl(from2bit(fasta.streamSource,fasta.supplyInformation.getOrElse(Map.empty)))
      case Protein =>
        new SequenceImpl(new String(fasta.streamSource,"UTF-8"))
      case _ => throw new BioSequenceTypeException
    }

  }

  def fromString(str:String):Sequence={
    new SequenceImpl(str)
  }


}