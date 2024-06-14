package file

import org.junit.jupiter.api.Test
import utils.file.{FileNormalize, FileProcess}


/**
 * @author cai584770
 * @date 2024/4/8 16:31
 * @Version
 */
class FileInformationAndSequence {

  val inputpath = "D:/GithubRepository/biosequence/src/test/fileprocess/data/chr1.fa"
  val inputpath1 = "D:/GithubRepository/biosequence/src/test/biosequence/data/88_chr1.fa"

  val sequence = "CCGGTTTCTCTGGTTGAAAATCATTGTGTATATAATNATAATTTTATCNTTTTTATGNAATTG\nCCGGTTTCTCTGGTTGAAAATCATTGTGTATATAATNATAATTTTATCNTTTTTATGNAATTG\nCCGGTTTCTCTGGTTGAAAATCATTGTGTATATAATNATAATTTTATCNTTTTTATGNAATTG\nCCGGTTTCTCTGGTTGAAAATCATTGTGTATATAATNATAATTTTATCNTTTTTATGNAATTG\nCCGGTTTCTCTGGTTGAAAATCATTGTGTATATAATNATAATTTTATCNTTTTTATGNAATTG"

  @Test
  def test01(): Unit = {
    val sequence1 = FileNormalize.remove(sequence)

    println(sequence1)
  }

  @Test
  def test02():Unit={
    val startTime = System.nanoTime()

    val (information,sequence) = FileProcess.getInformationAndSequence(inputpath1)

    val endTime = System.nanoTime()
    val durationMs = (endTime - startTime) / 1000000.0

    println(information)
    println("---")
    println(sequence)

    println(s"runtime：$durationMs ms")
  }


  

}
