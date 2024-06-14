package biopanda.sequence

import biopanda.`type`.{DNASequenceType, ProteinSequenceType}
import biopanda.sequence.BioSequenceType.DNA
import org.grapheco.lynx.types.LynxType
import org.grapheco.pandadb.plugin.annotations.ExtensionType

/**
 * @author cai584770
 * @date 2024/6/10 11:11
 * @Version
 */
@ExtensionType
class ProteinSequence(sequence: String) extends Sequence(sequence) {
  val bioSequenceType: BioSequenceType.Value = BioSequenceType.Protein

  override def lynxType: LynxType = new ProteinSequenceType



}
