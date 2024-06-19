package functions

import biopanda.sequence.RNASequence
import org.grapheco.lynx.func.LynxProcedure
import org.grapheco.lynx.types.property.LynxString
import org.grapheco.pandadb.plugin.annotations.UDFCollection

/**
 * @author cai584770
 * @date 2024/5/21 16:19
 * @Version
 */
@UDFCollection
class RNASequenceFunction {

  @LynxProcedure(name = "RNASequence.fromFile")
  def fromFile(filePath: LynxString): RNASequence = {
    RNASequence.fromFile(filePath.value)
  }


  @LynxProcedure(name = "RNASequence.getSupplyInformation")
  def length(rnaSequence: RNASequence): Long = {
    rnaSequence.seq.length
  }


}

