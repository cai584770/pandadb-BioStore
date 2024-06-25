package org.cai.biopanda.extensiontype

import org.grapheco.lynx.types.{LTAny, LynxType}

/**
 * @author cai584770
 * @date 2024/6/10 10:37
 * @Version
 */
object DNASequenceType {
  val instance: DNASequenceType = new DNASequenceType {

    override def parentType: LynxType = LTAny

    override def toString: String = "DNASequence"
  }


}

sealed abstract class DNASequenceType extends LynxType