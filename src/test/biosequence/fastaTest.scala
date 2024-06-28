package biosequence

import org.cai.biopanda.factory.FASTAFactory
import org.cai.biopanda.factory.FASTAFactory.`export`
import org.cai.biopanda.sequence.BioSequenceEnum.{DNA, RNA}
import org.cai.biopanda.sequence.FASTA
import org.junit.Test

/**
 * @author cai584770
 * @date 2024/6/27 17:36
 * @Version
 */
class fastaTest {

  val input = "C:\\Users\\MSI-NB\\Desktop\\project\\files\\ADARB1.fa"
  val output = "D:\\GithubRepository\\BioSequence\\src\\test\\biosequence\\data\\ADARB1.fa"

  @Test
  def t1(): Unit = {
    val f: FASTA = FASTAFactory.fromFile(input, DNA)
    println(f"f.getSupplyInformation:${f.getSupplyInformation}")
    println(f"f.getInformation:${f.getInformation}")
    println(f"f.getBioSequenceType:${f.getBioSequenceType}")
//    println(f"f.getSequence:${f.getSequence}")
    println(f.toString)
    println("success")
    export(f, output)
  }

  @Test
  def t2(): Unit = {

  }

  @Test
  def t3(): Unit = {

  }


}
