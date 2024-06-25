package org.cai.biopanda.extensiontype

import org.grapheco.lynx.types.{LTAny, LynxType}

/**
 * @author cai584770
 * @date 2024/6/10 10:38
 * @Version
 */
object RNASequenceType{
  val instance: RNASequenceType = new RNASequenceType {

    override def parentType: LynxType = LTAny

    override def toString: String = "RNASequence"
  }

}
sealed abstract class RNASequenceType extends LynxType