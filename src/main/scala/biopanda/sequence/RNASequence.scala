package biopanda.sequence

import biopanda.`type`.RNASequenceType
import org.grapheco.lynx.types.{AnyType, LynxType, LynxValue}
import org.grapheco.pandadb.plugin
import org.grapheco.pandadb.plugin.annotations.ExtensionType
import serialize.StreamUtils.{int2BytesArray, long2ByteArray}

/**
 * @author cai584770
 * @date 2024/6/10 11:01
 * @Version
 */
@ExtensionType
class RNASequence(sequence: String) extends Sequence(sequence) {

  val bioSequenceType: BioSequenceType.Value = BioSequenceType.RNA

  override def lynxType: LynxType = new RNASequenceType


}