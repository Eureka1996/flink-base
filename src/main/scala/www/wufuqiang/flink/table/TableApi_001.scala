package www.wufuqiang.flink.table

import org.apache.flink.api.scala.{DataSet, ExecutionEnvironment,_}
import org.apache.flink.table.api.{Table, TableEnvironment}

/**
  * @ author wufuqiang
  * @ date 2019/4/7/007 - 14:51
  **/
object TableApi_001 {
  def main(args: Array[String]): Unit = {

    val dataSetEnv = ExecutionEnvironment.getExecutionEnvironment

    val hdfsDataPath = "D:\\wufuqiangbd\\ideaProjects\\GenerateLogs\\student.csv"

    val tableEnv = TableEnvironment.getTableEnvironment(dataSetEnv)

    val dataset1:DataSet[Student] = dataSetEnv.readCsvFile[Student](hdfsDataPath,includedFields = Array[Int](0,1,2,3,4),pojoFields = Array[String]("id","name","sex","age","department"))

    tableEnv.registerDataSet("studentTable",dataset1)

    val resultTable:Table = tableEnv.sqlQuery("select id,name,sex,age,department from studentTable where age > 80")

    val allDataTable = tableEnv.scan("studentTable")

    val resultTableCount:Table = allDataTable.groupBy("department").select("department,count(id) as total")

    tableEnv.toDataSet[(String,Long)](resultTableCount).print()

/*    val value:DataSet[(Int,String,String,Int,String)] = tableEnv.toDataSet[(Int,String,String,Int,String)](resultTable)
    resultTable.printSchema()
    print("-------------------------")
    value.print()*/

//    dataSetEnv.execute("table api")

  }


}

case class Student(id:Int,name:String,sex:String,age:Int,department:String)
