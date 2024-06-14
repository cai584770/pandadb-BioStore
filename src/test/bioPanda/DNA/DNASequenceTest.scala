package bioPanda.DNA

import biopanda.sequence.DNASequence
import org.junit.Test

/**
 * @author cai584770
 * @date 2024/6/11 15:21
 * @Version
 */
class DNASequenceTest {

  val dnasequence = "AGCTTTAGCTAGGCAGTCAGCTGATCAGTCAGCTGATCGGCTACGTACGTACGTA"

  @Test
  def test01(): Unit = {
    val dna = new DNASequence(dnasequence)

    println(s"dna:${dna}")
    println(s"dna.bioSequenceType:${dna.bioSequenceType}")
    println(s"dna.serialize:${dna.serialize().mkString("Array(", ", ", ")")}")
    println(s"value:${dna.value}")
    println(dna.toString)

    val ab = dna.serialize()

    val dna1 = dna.deserialize(ab)

    println(s"dna1:${dna1}")



  }

  @Test
  def test0():Unit={

  }

}
