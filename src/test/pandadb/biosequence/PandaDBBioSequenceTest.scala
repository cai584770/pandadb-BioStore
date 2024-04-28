package pandadb.biosequence

import org.grapheco.lynx.types.LynxValue
import org.grapheco.pandadb.driver.PandaDBDriver
import org.junit.Test

/**
 * @author cai584770
 * @date 2024/4/25 16:18
 * @Version
 */
class PandaDBBioSequenceTest {

  val host = "10.0.82.148"
  val port = 7600
  val db:PandaDBDriver = new PandaDBDriver(host,port)


  @Test
  def insertTest():Unit= {
    val t1 = System.nanoTime()
    db.query("create (n: plant {name:'TAIR10',species:'Arabidopsis thaliana',chromosome1: BioSequence.fromFile('/home/cjw/program/data/TAIR10/chr1.fa'),chromosome2: BioSequence.fromFile('/home/cjw/program/data/TAIR10/chr2.fa'),chromosome3: BioSequence.fromFile('/home/cjw/program/data/TAIR10/chr3.fa'),chromosome4: BioSequence.fromFile('/home/cjw/program/data/TAIR10/chr4.fa'),chromosome5: BioSequence.fromFile('/home/cjw/program/data/TAIR10/chr5.fa')})")
    println(s"insert time:${(System.nanoTime()-t1)/1000000.0}")
//    db.query("create (n: plant {name:'TAIR10',species:'Arabidopsis thaliana'})")
//    db.query("create (n: plant {name:'TAIR10',species:'Arabidopsis thaliana'})")
//    db.query("create (n: plant {name:'TAIR10',species:'Arabidopsis thaliana'})")
//    db.query("create (n: plant {name:'TAIR10',species:'Arabidopsis thaliana'})")

  }

  @Test
  def queryTest(): Unit = {
    val iter: Iterator[Map[String, LynxValue]] = db.query("MATCH (n: plant ) RETURN n.chromosome1")
    iter.foreach { next =>
      next.foreach { case (key, value) =>
        println(s"Key: $key, Value: ${value}")
//        println(s"Key: $key, Value: ${value.getClass}")

      }
    }




  }


  @Test
  def test02(): Unit = {
//    db.query("create (n:Person{name:'TAIR10',age:18})")
    val iter: Iterator[Map[String, LynxValue]] = db.query("match (n) return n.chromosome1")

    iter.foreach { next =>
        println(next.mkString)
    }


  }



  @Test
  def test03(): Unit = {
    val iter: Iterator[Map[String, LynxValue]] = db.query("match (n) return n")

    iter.foreach { next =>
      println(next.mkString)
    }


  }

  @Test // delete all nodes
  def deleteAllNodes(): Unit = {
    db.query("MATCH(n) DETACH DELETE n")
    val iter: Iterator[Map[String, LynxValue]] = db.query("match (n) return n")

    iter.foreach { next =>
      if (iter.hasNext) {
        println(next.mkString)
      }
    }


  }


}
