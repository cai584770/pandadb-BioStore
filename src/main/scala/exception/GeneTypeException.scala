package exception

/**
 * @author cai584770
 * @date 2024/5/20 12:47
 * @Version
 */
class GeneTypeException extends Exception{
  override def getMessage: String = "Invalid gene type provided."
}
