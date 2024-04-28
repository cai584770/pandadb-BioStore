package bioJAVA

import fileprocess.FileProcess.getInformationAndSequence
import org.biojava.nbio.alignment.Alignments.PairwiseSequenceAlignerType
import org.biojava.nbio.alignment.template.GapPenalty
import org.biojava.nbio.alignment.{Alignments, SimpleGapPenalty}
import org.biojava.nbio.core.alignment.matrices.SubstitutionMatrixHelper
import org.biojava.nbio.core.sequence.DNASequence
import org.biojava.nbio.core.sequence.compound.{AmbiguityDNACompoundSet, NucleotideCompound}
import org.biojava.nbio.core.sequence.io.FastaReaderHelper
import org.junit.Test
import org.biojava.nbio.core.sequence.template.Sequence
import org.biojava.nbio.core.alignment.{SimpleAlignedSequence, SimpleSequencePair}
import org.biojava.nbio.core.alignment.template.{AlignedSequence, SequencePair, SubstitutionMatrix}

import java.util
import java.io.File

/**
 * @author cai584770
 * @date 2024/4/22 10:16
 * @Version
 */
class AlignmentTest {

  @Test
  def test01():Unit={
    val sequenceString = "AAGGTTCTGACACATGTTATCTNATTCTATGTTTACATGCTTGTTCATCTTCAGGTTTGGAATCTTGGTTTACCTTACCCAACTTTTACATTGGCCATTGACGATAAGCCCTATCTAAATACCGTCTCTGATGATCACTCTGTTAAGGTCTAATTCTGTTCACTTCTCTGTTATATAATGCATGTCTACTTGTAACTCTTATACTGTGTCGTAGGTTAACCTATTTGTTTGCCTAATGGTATCATCTGAT"
    val sequence: Sequence[NucleotideCompound] = new DNASequence(sequenceString)

    val subSequenceString = "ATGTTATCTNATTCTATGTTTACATGCTTGTTCATCTTCA"
    val subSequence: Sequence[NucleotideCompound] = new DNASequence(subSequenceString)

  }


  @Test
  def test02(): Unit = {

    val queryFile = "D:\\GithubRepository\\biosequence\\src\\test\\bioJAVA\\data\\sampling.fa"
    val targetFile = "D:\\GithubRepository\\biosequence\\src\\test\\bioJAVA\\data\\pseudo108_chr1.fa"

    val (queryInformation,querySequence) =  getInformationAndSequence(queryFile)
    val (targetInformation,targetSequence) =  getInformationAndSequence(targetFile)

    val firstTenMillionChars = targetSequence.take(262144)

    val queryDNASequence = new DNASequence(querySequence)
    val targetDNASequence = new DNASequence(firstTenMillionChars)

    val matrix: SubstitutionMatrix[NucleotideCompound] = SubstitutionMatrixHelper.getNuc4_4()

    val gapP = new SimpleGapPenalty()
    gapP.setOpenPenalty(5.toShort) // 间隙惩罚
    gapP.setExtensionPenalty(2.toShort) // 延展惩罚

    val psa: SequencePair[DNASequence, NucleotideCompound] =
      Alignments.getPairwiseAlignment(queryDNASequence, targetDNASequence, PairwiseSequenceAlignerType.LOCAL, gapP, matrix)

    println(psa)


  }

  @Test
  def test03(): Unit = {
    val targetSeq = "CACGTTTCTTGTGGCAGCTTAAGTTTGAATGTCATTTCTTCAATGGGACGGA" +
      "GCGGGTGCGGTTGCTGGAAAGATGCATCTATAACCAAGAGGAGTCCGTGCGCTTCGACAGC" +
      "GACGTGGGGGAGTACCGGGCGGTGACGGAGCTGGGGCGGCCTGATGCCGAGTACTGGAACA" +
      "GCCAGAAGGACCTCCTGGAGCAGAGGCGGGCCGCGGTGGACACCTACTGCAGACACAACTA" +
      "CGGGGTTGGTGAGAGCTTCACAGTGCAGCGGCGAG"
    val target = new DNASequence(targetSeq, AmbiguityDNACompoundSet.getDNACompoundSet())

    val querySeq = "ACGAGTGCGTGTTTTCCCGCCTGGTCCCCAGGCCCCCTTTCCGTCCTCAGGAA" +
      "GACAGAGGAGGAGCCCCTCGGGCTGCAGGTGGTGGGCGTTGCGGCGGCGGCCGGTTAAGGT" +
      "TCCCAGTGCCCGCACCCGGCCCACGGGAGCCCCGGACTGGCGGCGTCACTGTCAGTGTCTT" +
      "CTCAGGAGGCCGCCTGTGTGACTGGATCGTTCGTGTCCCCACAGCACGTTTCTTGGAGTAC" +
      "TCTACGTCTGAGTGTCATTTCTTCAATGGGACGGAGCGGGTGCGGTTCCTGGACAGATACT" +
      "TCCATAACCAGGAGGAGAACGTGCGCTTCGACAGCGACGTGGGGGAGTTCCGGGCGGTGAC" +
      "GGAGCTGGGGCGGCCTGATGCCGAGTACTGGAACAGCCAGAAGGACATCCTGGAAGACGAG" +
      "CGGGCCGCGGTGGACACCTACTGCAGACACAACTACGGGGTTGTGAGAGCTTCACCGTGCA" +
      "GCGGCGAGACGCACTCGT"
    val query = new DNASequence(querySeq, AmbiguityDNACompoundSet.getDNACompoundSet())

    val matrix: SubstitutionMatrix[NucleotideCompound] = SubstitutionMatrixHelper.getNuc4_2()
    val gapP: GapPenalty = new SimpleGapPenalty()
    gapP.setOpenPenalty(5.toShort)
    gapP.setExtensionPenalty(2.toShort)

    val psa: SequencePair[DNASequence, NucleotideCompound] =
      Alignments.getPairwiseAlignment(query, target, PairwiseSequenceAlignerType.LOCAL, gapP, matrix)

    println(psa)

  }

  @Test
  def test04(): Unit = {
    val queryFile = "D:\\GithubRepository\\biosequence\\src\\test\\bioJAVA\\data\\sampling.fa"
    val targetFile = "D:\\GithubRepository\\biosequence\\src\\test\\bioJAVA\\data\\pseudo108_chr1.fa"

    val (queryInformation, querySequence) = getInformationAndSequence(queryFile)
    val (targetInformation, targetSequence) = getInformationAndSequence(targetFile)
    val queryDNASequence = new DNASequence(querySequence)
    val targetDNASequence = new DNASequence(targetSequence)

    val matrix: SubstitutionMatrix[NucleotideCompound] = SubstitutionMatrixHelper.getNuc4_4()

    val gapP = new SimpleGapPenalty()
    gapP.setOpenPenalty(5.toShort)
    gapP.setExtensionPenalty(2.toShort)


    val psa: SequencePair[DNASequence, NucleotideCompound] =
      Alignments.getPairwiseAlignment(queryDNASequence, targetDNASequence, PairwiseSequenceAlignerType.LOCAL, gapP, matrix)

    println(psa)

  }


  @Test
  def test0(): Unit = {


  }
}
