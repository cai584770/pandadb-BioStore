package org.cai.biopanda.opts

import org.grapheco.lynx.types.LynxValue
import org.grapheco.lynx.types.property.LynxString

/**
 * @author cai584770
 * @date 2024/6/25 17:51
 * @Version
 */
object RNAOpts {
  def allPropertyNames: Seq[String] = {
    Seq.empty
  }

  def extractProperty(propertyName: String): Option[LynxValue] = {
    Option(LynxString("valueOf" + propertyName))
  }
}
