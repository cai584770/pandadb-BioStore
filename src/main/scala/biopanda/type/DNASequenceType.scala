package biopanda.`type`

import org.grapheco.lynx.types.{LTAny, LynxType}

/**
 * @author cai584770
 * @date 2024/6/10 10:37
 * @Version
 */
class DNASequenceType extends LynxType{
  override def parentType: LynxType = LTAny

  override def toString: String = "DNASequence"
}
