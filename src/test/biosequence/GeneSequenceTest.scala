package biosequence

import fileprocess.FileNormalize.normalize
import fileprocess.FileProcess.getInformationAndSequence
import org.junit.Test

import java.nio.file.{Files, Paths}

/**
 * @author cai584770
 * @date 2024/5/20 15:33
 * @Version
 */
class GeneSequenceTest {
  val rnaFile = "D:\\GithubRepository\\BioSequence\\src\\test\\genesequence\\data\\rna\\ADARB1.fa"
  val dnaFile = "D:\\GithubRepository\\BioSequence\\src\\test\\genesequence\\data\\dna\\TAIR10_chr1.fas"

  @Test
  def test01(): Unit = {
    val t1 = System.currentTimeMillis()
    val dnaSequence = DNASequence.fromFile(dnaFile)
    println(System.currentTimeMillis()-t1)

    val dnaSequenceAB = dnaSequence.toBytes()
    val dnaSequence1 = DNASequence.fromBytes(dnaSequenceAB)

    DNASequence.export(dnaSequence1,"D:\\GithubRepository\\BioSequence\\src\\test\\genesequence\\data\\dna\\TAIR10_chr1_temp.fas")

    println(filesAreEqual("D:\\GithubRepository\\BioSequence\\src\\test\\genesequence\\data\\dna\\TAIR10_chr1_temp.fas",dnaFile))

  }

  @Test
  def test02(): Unit = {
    val rnaSequence = RNASequence.fromFile(rnaFile)
    println(rnaSequence.geneType)
    val rnaSequenceAB = rnaSequence.toBytes()
    val rnaSequence1 = RNASequence.fromBytes(rnaSequenceAB)

    RNASequence.export(rnaSequence1, "D:\\GithubRepository\\BioSequence\\src\\test\\genesequence\\data\\rna\\ADARB1_temp.fa")

    println(filesAreEqual("D:\\GithubRepository\\BioSequence\\src\\test\\genesequence\\data\\rna\\ADARB1_temp.fa", rnaFile))
  }

  @Test
  def test03(): Unit = {
    val (information,sequence) = getInformationAndSequence(dnaFile)
    println(information)
//    println(sequence)
    val convertSequence = convertToRNA(sequence)
//    println(convertSequence)
    val result = normalize(convertSequence)
    println(result)

  }

  @Test
  def test0():Unit={

  }

  def filesAreEqual(filePath1: String, filePath2: String): Boolean = {
    val path1 = Paths.get(filePath1)
    val path2 = Paths.get(filePath2)

    // Check if both files exist
    if (!Files.exists(path1) || !Files.exists(path2)) {
      return false
    }

    // Check if both files have the same size
    if (Files.size(path1) != Files.size(path2)) {
      return false
    }

    // Read file contents
    val content1 = Files.readAllBytes(path1)
    val content2 = Files.readAllBytes(path2)

    // Compare file contents
    content1.sameElements(content2)
  }

  def convertToRNA(dnaSequence: String): String = {
    dnaSequence.replace('T', 'U')
  }

}
