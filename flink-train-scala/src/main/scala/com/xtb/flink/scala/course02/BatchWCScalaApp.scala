package com.xtb.flink.scala.course02

import org.apache.flink.api.scala._

/**
  * 使用Scala API 开发Flink的一个批处理应用程序
  */
object BatchWCScalaApp {
  def main(args: Array[String]): Unit = {
    val input: String = "./data"
    val env = ExecutionEnvironment.getExecutionEnvironment
    val text = env.readTextFile(input)
    text.flatMap(_.toLowerCase.split("\\W+").filter(_.nonEmpty))
      .map((_, 1))
      .groupBy(0)
      .sum(1).print()
  }
}
