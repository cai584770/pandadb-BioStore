package functions

import biosequence.ProteinSequence
import org.grapheco.lynx.cypherplus.DefaultFunctions
import org.grapheco.lynx.func.LynxProcedure
import org.grapheco.lynx.types.property.LynxString

/**
 * @author cai584770
 * @date 2024/5/23 9:35
 * @Version
 */
class ProteinSequenceFunction extends DefaultFunctions{
  @LynxProcedure(name = "ProteinSequence.fromURL")
  override def fromURL(url: LynxString): ProteinSequence = {
    ProteinSequence.fromURL(url.value)
  }

  @LynxProcedure(name = "ProteinSequence.fromFile")
  override def fromFile(filePath: LynxString): ProteinSequence = {
    ProteinSequence.fromFile(filePath.value)
  }

  @LynxProcedure(name = "ProteinSequence.export")
  def export(proteinSequence: ProteinSequence, filePath: String): Unit = {
    ProteinSequence.export(proteinSequence, filePath)
  }

  @LynxProcedure(name = "ProteinSequence.EMPTY")
  def empty(): ProteinSequence = {
    ProteinSequence.EMPTY
  }
}
