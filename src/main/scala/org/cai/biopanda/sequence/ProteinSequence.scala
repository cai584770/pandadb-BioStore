package org.cai.biopanda.sequence

import org.cai.biopanda.extensiontype.ProteinSequenceType
import org.cai.biopanda.sequence.BioSequenceEnum.Protein
import org.grapheco.lynx.types.LynxType

/**
 * @author cai584770
 * @date 2024/6/10 11:11
 * @Version
 */
class ProteinSequence(sequence: String, bioSequenceType: BioSequenceEnum.Value = Protein) extends BioSequence {
  override def getSequence: String = sequence

  def getBioSequenceType: BioSequenceEnum.Value = bioSequenceType

  override def lynxType: LynxType = ProteinSequenceType.instance

}

