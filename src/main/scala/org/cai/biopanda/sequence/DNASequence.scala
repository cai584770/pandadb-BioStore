package org.cai.biopanda.sequence

import org.cai.biopanda.extensiontype.DNASequenceType
import org.cai.biopanda.sequence.BioSequenceEnum.DNA
import org.grapheco.lynx.types.LynxType

/**
 * @author cai584770
 * @date 2024/6/7 10:53
 * @Version
 */
class DNASequence(sequence: String, bioSequenceType: BioSequenceEnum.Value = DNA) extends BioSequence {
  override def getSequence: String = sequence

  def getBioSequenceType: BioSequenceEnum.Value = bioSequenceType

  override def lynxType: LynxType = DNASequenceType.instance

}




