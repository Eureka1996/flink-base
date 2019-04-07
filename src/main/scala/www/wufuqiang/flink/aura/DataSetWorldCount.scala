package www.wufuqiang.flink.aura

import org.apache.flink.api.scala._

/**
  * @ author wufuqiang
  * @ date 2019/4/3/003 - 17:21
  **/
object DataSetWorldCount {

  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment

    case class WordCount(word:String,count:Long)
    val dataset:DataSet[WordCount] = env.fromElements("a b","c a" ,"a b").flatMap(x=>x.split(" ")).map(WordCount(_,1))


    val dataset4:AggregateDataSet[WordCount] = dataset.groupBy(0).sum(1)

    dataset4.print()

//    env.execute("auraworldcount")
  }

}
