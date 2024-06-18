package biopanda.`type`

import org.grapheco.lynx.types.{LTAny, LynxType}

/**
 * @author cai584770
 * @date 2024/6/18 18:42
 * @Version
 */
class IdentifierType extends LynxType{
  override def parentType: LynxType = LTAny

  override def toString: String = "Identifier"
}
