package org.cai.align

import org.biojava.nbio.alignment.Alignments.PairwiseSequenceAlignerType
import org.biojava.nbio.alignment.{Alignments, SimpleGapPenalty}
import org.biojava.nbio.core.alignment.matrices.SubstitutionMatrixHelper
import org.biojava.nbio.core.alignment.template.{SequencePair, SubstitutionMatrix}
import org.biojava.nbio.core.sequence.DNASequence
import org.biojava.nbio.core.sequence.compound.NucleotideCompound
import org.cai.biopanda.sequence.BioSequenceEnum.DNA
import org.cai.biopanda.sequence.FASTA
import org.cai.exception.{AlignNotFoundException, BioSequenceTypeException}

/**
 * @author cai584770
 * @date 2024/5/21 8:49
 * @Version
 */
object Align {

  def local(querySequence: String, targetGeneSequence: FASTA): SequencePair[DNASequence, NucleotideCompound] = {
    val (target, query) = targetGeneSequence.getBioSequenceType match {
      case DNA => (new DNASequence(targetGeneSequence.getSequence), new DNASequence(querySequence))
      case _ => throw new BioSequenceTypeException
    }

    val gapP = new SimpleGapPenalty()
    gapP.setOpenPenalty(5)
    gapP.setExtensionPenalty(2)
    val matrix: SubstitutionMatrix[NucleotideCompound] = SubstitutionMatrixHelper.getNuc4_4
    val psa = Alignments.getPairwiseAlignment(query, target, PairwiseSequenceAlignerType.LOCAL, gapP, matrix)

    //    val psa: SequencePair[DNASequence, NucleotideCompound] = Alignments.getPairwiseAlignment(query, target, PairwiseSequenceAlignerType.LOCAL, gapP, matrix)
    if (psa != null) {
      return psa
    }

    throw new AlignNotFoundException
  }

  def global(querySequence: String, targetGeneSequence: FASTA): SequencePair[DNASequence, NucleotideCompound] = {
    val (target, query) = targetGeneSequence.getSequence match {
      case DNA => (new DNASequence(targetGeneSequence.getSequence), new DNASequence(querySequence))
      case _ => throw new BioSequenceTypeException
    }

    val gapP = new SimpleGapPenalty()
    gapP.setOpenPenalty(5)
    gapP.setExtensionPenalty(2)
    val matrix: SubstitutionMatrix[NucleotideCompound] = SubstitutionMatrixHelper.getNuc4_4

    val psa: SequencePair[DNASequence, NucleotideCompound] =
      Alignments.getPairwiseAlignment(query, target, PairwiseSequenceAlignerType.GLOBAL, gapP, matrix)
    if (psa != null) {
      return psa
    }

    throw new AlignNotFoundException
  }

}
