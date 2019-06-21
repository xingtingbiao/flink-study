package com.xtb.flink.course04

import org.apache.flink.api.scala._
import org.apache.flink.core.fs.FileSystem.WriteMode

object DataSetSinkApp {

  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
    val text = env.fromCollection(1 to 10)
    // 如果这里设置了并行度, 这里的filePath将会是一个文件夹
    text.writeAsText("out.txt", WriteMode.OVERWRITE)
    env.execute("DataSetSinkApp")
  }

}
