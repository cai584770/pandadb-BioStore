package biopanda.file

import org.grapheco.lynx.types.LynxType
import org.grapheco.pandadb.plugin.AnyType
import org.grapheco.pandadb.plugin.annotations.ExtensionType

/**
 * @author cai584770
 * @date 2024/6/11 10:48
 * @Version
 */
  case class Identifier(seqId: String, readNum: Int, length: Int)

case class Quality(quality: String)

case class ShortRead(read: String)

@ExtensionType
case class FASTQ(identifier: Identifier, shortRead: ShortRead, separator: String, quality: Quality) extends AnyType {
  override def serialize(): Array[Byte] = ???

  override def deserialize(bytes: Array[Byte]): AnyType = ???

  override def value: Any = ???

  override def lynxType: LynxType = ???
}


object FASTQ {

  def fromFile(filePath: String): List[FASTQ] = {
    val source = scala.io.Source.fromFile(filePath)
    val lines = source.getLines().toList
    source.close()

    lines.grouped(4).collect {
      case List(identifier, sequence, separator, quality) =>
        val id = parseIdentifier(identifier)
        FASTQ(id, ShortRead(sequence), separator, Quality(quality))
    }.toList

  }

  def parseIdentifier(identifier: String): Identifier = {
    val pattern = """@(\S+)\s+(\d+)\s+length=(\d+)""".r
    identifier match {
      case pattern(seqId, readNum, length) => Identifier(seqId, readNum.toInt, length.toInt)
      case _ => throw new IllegalArgumentException(s"Invalid identifier format: $identifier")
    }
  }
}
