package org.cai.biopanda.opts

import org.grapheco.lynx.types.LynxValue
import org.grapheco.lynx.types.property.LynxString

/**
 * @author cai584770
 * @date 2024/6/25 15:38
 * @Version
 */
object FASTAOpts {
  def allPropertyNames: Seq[String] = {
    Seq.empty
  }

  def extractProperty(propertyName: String): Option[LynxValue] = {
    Option(LynxString("valueOf" + propertyName))
  }
}
