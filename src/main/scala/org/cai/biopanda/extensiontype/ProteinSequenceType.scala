package org.cai.biopanda.extensiontype

import org.grapheco.lynx.types.{LTAny, LynxType}

/**
 * @author cai584770
 * @date 2024/6/10 10:38
 * @Version
 */
object ProteinSequenceType {
  val instance: ProteinSequenceType = new ProteinSequenceType {

    override def parentType: LynxType = LTAny

    override def toString: String = "ProteinSequence"
  }


}

sealed abstract class ProteinSequenceType extends LynxType