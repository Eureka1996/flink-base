package www.wufuqiang.flink

import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows
import org.apache.flink.streaming.api.windowing.time.Time

/**
  * @ author wufuqiang
  * @ date 2019/4/1/001 - 14:12
  **/
object EventTimeWindow01 {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment

//    从调用时刻开始给env创建的每一个stream追加时间特征
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)

    val streamKeyBy = env.socketTextStream("10-255-0-139",11111).assignTimestampsAndWatermarks(
      new BoundedOutOfOrdernessTimestampExtractor[String](Time.milliseconds(0)) {
        override def extractTimestamp(element: String): Long = {
//          eventTime是日志生成时间，我们从日志中解析EventTime
          val eventTime = element.split(" ")(0).toLong
          println(eventTime)
          eventTime
        }
      }
    ).map(item=>(item.split(" ")(1),1)).keyBy(0)


//    滚动窗口
    val streamWindow = streamKeyBy.window(TumblingEventTimeWindows.of(Time.seconds(5)))

    val streamReduce = streamWindow.reduce((item1,item2)=>(item1._1,item1._2+item2._2))
    streamReduce.print()
    env.execute("FirstEventTimeWindow")

  }

}
