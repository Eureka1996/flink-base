package www.wufuqiang.flink

import org.apache.flink.streaming.api.scala._

/**
  * @ author wufuqiang
  * @ date 2019/3/31/031 - 9:14
  **/
object FlinkSource01 {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val stream = env.readTextFile("test00.txt").flatMap(_.split(" "))
//    val stream = env.socketTextStream("10-255-0-139",11111)
//    val stream02 = env.generateSequence(1,10)
//    val streamMap = stream.flatMap(_.split(" "))
//    val streamConnection = stream.connect(stream02)
//    streamConnection.map(print(_),print(_))
//    streamMap.print()
    val streamSplit = stream.split(word =>
      ("hadoop".equals(word)) match{
        case true => List("hadoop")
        case false => List("other")
      }
    )

    val streamHadoop = streamSplit.select("hadoop")
    val streamOther = streamSplit.select("other")

    streamHadoop.print()
//    streamOther.print()
    env.execute("FirstJob")
  }

}
