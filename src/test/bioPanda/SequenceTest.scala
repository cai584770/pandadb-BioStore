package bioPanda

import biopanda.sequence.{ProteinSequence, Sequence}
import org.junit.Test

/**
 * @author cai584770
 * @date 2024/6/19 9:51
 * @Version
 */
class SequenceTest {

  @Test
  def test0():Unit={
    val pro:Sequence= Sequence.fromString("aa")
  }
}
