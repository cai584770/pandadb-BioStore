package biopanda.`type`

import org.grapheco.lynx.types.{LTAny, LynxType}

/**
 * @author cai584770
 * @date 2024/6/18 18:58
 * @Version
 */
class FASTQType extends LynxType{
  override def parentType: LynxType = LTAny

  override def toString: String = "FASTQ"
}
