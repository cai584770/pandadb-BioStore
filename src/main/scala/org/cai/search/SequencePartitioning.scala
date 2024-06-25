package search

/**
 * @author cai584770
 * @date 2024/4/28 15:14
 * @Version
 */
object SequencePartitioning {

  private val segmentLength: Int = 262143

  /***
   * 0*65535-0*gap -> 1*65535+1*gap
   * 1*65535-1*gap -> 2*65535+1*gap
   * 2*65535-1*gap -> 3*65535+1*gap
   * ...
   * (n-1)*65535-1*gap -> sequence.length
   *
   * @param sequence
   * @param gap
   * @return
   */
  def partition(sequence:String,gap:Int):Array[String]={
    val length = sequence.length
    val numSegments = Math.ceil(length.toDouble / (segmentLength - gap)).toInt
    val result = new Array[String](numSegments)

    var start = 0
    for (i <- 0 until numSegments) {
      val end = Math.min(start + segmentLength + i * gap, length)
      result(i) = sequence.substring(start, end)
      start = start + segmentLength - gap
    }

    result
  }

}
