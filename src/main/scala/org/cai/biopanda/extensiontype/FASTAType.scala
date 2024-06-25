package org.cai.biopanda.extensiontype

import org.grapheco.lynx.types.{LTAny, LynxType}

/**
 * @author cai584770
 * @date 2024/6/11 16:07
 * @Version
 */
object FASTAType{
  val instance: FASTAType = new FASTAType {

    override def parentType: LynxType = LTAny

    override def toString: String = "FASTA"
  }


}
sealed abstract class FASTAType extends LynxType