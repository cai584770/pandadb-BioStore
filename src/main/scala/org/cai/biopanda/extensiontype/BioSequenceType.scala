package org.cai.biopanda.extensiontype

import org.grapheco.lynx.types.{LTAny, LynxType}

/**
 * @author cai584770
 * @date 2024/6/10 15:16
 * @Version
 */
object BioSequenceType  {
  val instance: BioSequenceType = new BioSequenceType {

    override def parentType: LynxType = LTAny

    override def toString: String = "BioSequence"
  }

}

sealed abstract class BioSequenceType extends LynxType