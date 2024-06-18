package biopanda.highthroughput

import biopanda.`type`.FASTQType
import biopanda.highthroughput.entity.{Identifier, Quality, ShortRead}
import org.grapheco.lynx.types.LynxType
import org.grapheco.pandadb.plugin.AnyType
import org.grapheco.pandadb.plugin.annotations.ExtensionType
import serialize.DeSerialize.decodeFASTQ
import serialize.Serialize.encodeFASTQ

/**
 * @author cai584770
 * @date 2024/6/11 10:48
 * @Version
 */
@ExtensionType
case class FASTQ(identifier: Identifier, shortRead: ShortRead, quality: Quality) extends AnyType {
  override def serialize(): Array[Byte] = encodeFASTQ(identifier, shortRead, quality)

  override def deserialize(bytes: Array[Byte]): AnyType = decodeFASTQ(bytes)

  override def value: Any = identifier.toString

  override def lynxType: LynxType = new FASTQType
}


object FASTQ {

  def fromFile(filePath: String): List[FASTQ] = {
    val source = scala.io.Source.fromFile(filePath)
    val lines = source.getLines().toList
    source.close()

    lines.grouped(4).collect {
      case List(identifier, sequence, separator, quality) =>
        val id = parseIdentifier(identifier)
        FASTQ(id, ShortRead(sequence), Quality(quality))
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
