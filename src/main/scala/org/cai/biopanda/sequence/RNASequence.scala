package org.cai.biopanda.sequence

import org.cai.biopanda.extensiontype.RNASequenceType
import org.cai.biopanda.sequence.BioSequenceEnum.RNA
import org.grapheco.lynx.types.LynxType

/**
 * @author cai584770
 * @date 2024/6/10 11:01
 * @Version
 */
class RNASequence(sequence: String,bioSequenceType: BioSequenceEnum.Value = RNA) extends BioSequence {
  override def getSequence: String = sequence

  def getBioSequenceType: BioSequenceEnum.Value = bioSequenceType

  override def lynxType: LynxType = RNASequenceType.instance

}
