package dnaSequence

import align.Align
import biopanda.sequence.DNASequence
import org.biojava.nbio.core.alignment.template.SequencePair
import org.biojava.nbio.core.sequence.compound.NucleotideCompound
import org.grapheco.lynx.cypherplus.DefaultFunctions
import org.grapheco.lynx.func.LynxProcedure
import org.grapheco.lynx.types.property.LynxString
import org.grapheco.pandadb.plugin.annotations.UDFCollection
import search.{Search, SequencePartitioning}
import serialize.StreamUtils
import store.ReStoreSequence

/**
 * @author cai584770
 * @date 2024/5/21 15:55
 * @Version
 */
@UDFCollection
class DNASequenceFunction{


  @LynxProcedure(name = "DNASequence.fromFile")
  def fromFile(filePath: LynxString): DNASequence = {
    DNASequence.fromFile(filePath.value)
  }

  @LynxProcedure(name = "DNASequence.length")
  def length(dnaSequence: DNASequence): Long = {
    dnaSequence.seq.length
  }


}
