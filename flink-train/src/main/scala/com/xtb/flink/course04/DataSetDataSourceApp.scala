package com.xtb.flink.course04

import org.apache.flink.api.scala._
import org.apache.flink.configuration.Configuration

object DataSetDataSourceApp {


  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
    // fromCollection(env)
//    textFile(env)
//    csvFile(env)
    readRecursiveFile(env)
    //    env.execute("JavaDataSetDataSourceApp")
  }

  private def fromCollection(env: ExecutionEnvironment) = {
    val value = env.fromCollection(List("a", "b", "c"))
    value.print()
  }

  private def textFile(env: ExecutionEnvironment): Unit = {
    // path也可以是一个文件夹
    val path = "file:///Users/xingtingbiao/xtb/data/hello.txt"
    val line = env.readTextFile(path)
    line.print()
  }

  private def csvFile(env: ExecutionEnvironment): Unit = {
    val path = "./people.csv"
//    val value = env.readCsvFile[(String, Int, String)](path, ignoreFirstLine = true)
    val value = env.readCsvFile[(String, Int)](path, ignoreFirstLine = true, includedFields = Array(0, 1))
    value.print()
  }

  private def readRecursiveFile(env: ExecutionEnvironment): Unit = {
    val path = "file:///Users/xingtingbiao/xtb/data/nested/"
    env.readTextFile(path).print()
    println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
    val conf = new Configuration()
    conf.setBoolean("recursive.file.enumeration", true)
    env.readTextFile(path)
      .withParameters(conf)
      .print()
  }

}
