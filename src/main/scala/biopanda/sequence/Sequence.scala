package biopanda.sequence

import biopanda.`type`.{DNASequenceType, SequenceType}
import org.grapheco.lynx.cypherplus.Blob
import org.grapheco.lynx.types.LynxType
import org.grapheco.pandadb.plugin.AnyType
import org.grapheco.pandadb.plugin.annotations.ExtensionType


/**
 * @author cai584770
 * @date 2024/6/10 11:13
 * @Version
 */

@ExtensionType
class Sequence(sequence:String) extends AnyType{

  override def value: Any = sequence

  override def lynxType: LynxType = new SequenceType

  override def serialize(): Array[Byte] = sequence.getBytes("UTF-8")

  override def deserialize(bytes: Array[Byte]): AnyType = new Sequence(new String(bytes, "UTF-8"))

  override def toString: String = f"${this.getClass.getSimpleName}:"+(if (sequence.length > 97) (sequence.substring(0, 97)+"...") else sequence + "...")

}

object Sequence{





}