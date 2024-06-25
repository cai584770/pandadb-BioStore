package org.cai.exception

/**
 * @author cai584770
 * @date 2024/5/30 15:50
 * @Version
 */
class SAMNoHeadException extends Exception{
  override def getMessage: String = "SAM File Not Head."
}
