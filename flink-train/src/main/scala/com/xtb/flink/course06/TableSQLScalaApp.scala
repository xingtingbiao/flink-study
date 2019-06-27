package com.xtb.flink.course06

import org.apache.flink.api.scala._
import org.apache.flink.table.api.scala._
import org.apache.flink.types.Row

object TableSQLScalaApp {

  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
    val tableEnv = BatchTableEnvironment.create(env)

    val path = "file:///Users/xingtingbiao/xtb/project/my_work/flink-study/train-data/sales.csv"
    val csv = env.readCsvFile[SalesLog](path, ignoreFirstLine = true)

    tableEnv.registerDataSet("sales", csv)
    val result = tableEnv.scan("sales")
      // .printSchema()
      // .select("transactionId")
      .select('*)
    result.printSchema()
    result.toDataSet[Row].print()

    // tableEnv.sqlQuery("")

    // tableEnv.toDataSet[Row](result).print()
    // csv.print()
  }

  case class SalesLog(transactionId: String, customerId: String, itemId: String, amountPaid: Double)

}
