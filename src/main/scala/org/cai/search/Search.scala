package org.cai.search

import org.biojava.nbio.alignment.Alignments.PairwiseSequenceAlignerType
import org.biojava.nbio.alignment.{Alignments, SimpleGapPenalty}
import org.biojava.nbio.core.alignment.matrices.SubstitutionMatrixHelper
import org.biojava.nbio.core.alignment.template.{SequencePair, SubstitutionMatrix}
import org.biojava.nbio.core.sequence.DNASequence
import org.biojava.nbio.core.sequence.compound.NucleotideCompound

import scala.collection.mutable.ListBuffer

/**
 * @author cai584770
 * @date 2024/4/28 15:44
 * @Version
 */
object Search {

  def searchAll(query: String, sequences: Array[String]): List[SequencePair[DNASequence, NucleotideCompound]] = {
    val queryDNASequence = new DNASequence(query)
    val gapP = new SimpleGapPenalty()
    gapP.setOpenPenalty(5.toShort)
    gapP.setExtensionPenalty(2.toShort)
    val matrix: SubstitutionMatrix[NucleotideCompound] = SubstitutionMatrixHelper.getNuc4_4()

    val matchingResults = ListBuffer.empty[SequencePair[DNASequence, NucleotideCompound]]

    for (sequence <- sequences) {
      val targetDNASequence = new DNASequence(sequence)
      val psa: SequencePair[DNASequence, NucleotideCompound] =
        Alignments.getPairwiseAlignment(queryDNASequence, targetDNASequence, PairwiseSequenceAlignerType.LOCAL, gapP, matrix)
      if (psa != null) {
        matchingResults += psa
      }
    }

    matchingResults.toList
  }

  def searchOne(query: String, sequences: Array[String]): SequencePair[DNASequence, NucleotideCompound] = {
    val queryDNASequence = new DNASequence(query)
    val gapP = new SimpleGapPenalty()
    gapP.setOpenPenalty(5.toShort)
    gapP.setExtensionPenalty(2.toShort)
    val matrix: SubstitutionMatrix[NucleotideCompound] = SubstitutionMatrixHelper.getNuc4_4()

    for (sequence <- sequences) {
      val targetDNASequence = new DNASequence(sequence)
      val psa: SequencePair[DNASequence, NucleotideCompound] =
        Alignments.getPairwiseAlignment(queryDNASequence, targetDNASequence, PairwiseSequenceAlignerType.LOCAL, gapP, matrix)
      if (psa != null) {
        return psa
      }

    }

    null
  }

}
