package functions

import biopanda.sequence.{DNASequence, ProteinSequence}
import org.grapheco.lynx.cypherplus.DefaultFunctions
import org.grapheco.lynx.func.LynxProcedure
import org.grapheco.lynx.types.property.LynxString
import org.grapheco.pandadb.plugin.annotations.UDFCollection

/**
 * @author cai584770
 * @date 2024/5/23 9:35
 * @Version
 */
@UDFCollection
class ProteinSequenceFunction{


  @LynxProcedure(name = "ProteinSequence.fromFile")
  def fromFile(filePath: LynxString): ProteinSequence = {
    ProteinSequence.fromFile(filePath.value)
  }


  @LynxProcedure(name = "ProteinSequence.length")
  def length(proteinSequence: ProteinSequence): Long = {
    proteinSequence.seq.length
  }
}
