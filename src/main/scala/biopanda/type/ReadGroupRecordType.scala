package biopanda.`type`

import org.grapheco.lynx.types.{LTAny, LynxType}

/**
 * @author cai584770
 * @date 2024/6/12 9:46
 * @Version
 */
class ReadGroupRecordType extends LynxType{
  override def parentType: LynxType = LTAny

  override def toString: String = "ReadGroupRecord"
}
