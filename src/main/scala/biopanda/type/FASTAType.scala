package biopanda.`type`

import org.grapheco.lynx.types.{LTAny, LynxType}

/**
 * @author cai584770
 * @date 2024/6/11 16:07
 * @Version
 */
class FASTAType extends LynxType{
  override def parentType: LynxType = LTAny

  override def toString: String = "FASTA"
}
