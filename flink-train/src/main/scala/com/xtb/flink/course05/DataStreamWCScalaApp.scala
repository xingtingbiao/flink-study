package com.xtb.flink.course05

import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time

object DataStreamWCScalaApp {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    
//    socketStreamFunction(env)
//    nonParallelSourceFunction(env)
//    parallelSourceFunction(env)
//    richParallelSourceFunction(env)
    splitSelectFunction(env)

    env.execute("DataStreamWCScalaApp")
  }

  // 这里的addSource不能设置并行度
  def nonParallelSourceFunction(env: StreamExecutionEnvironment) = {
    val value = env.addSource(new MyNonParallelSourceFunction).setParallelism(2)
    value.print()
  }

  def parallelSourceFunction(env: StreamExecutionEnvironment) = {
    val value = env.addSource(new MyParallelSourceFunction).setParallelism(2)
    value.print()
  }

  def richParallelSourceFunction(env: StreamExecutionEnvironment) = {
    val value = env.addSource(new MyRichParallelSourceFunction).setParallelism(2)
    value.print()
  }

  def socketStreamFunction(env: StreamExecutionEnvironment) = {
    val text = env.socketTextStream("localhost", 9999)
    text.flatMap(_.toLowerCase.split("\\W+").filter(_.nonEmpty))
      .map((_, 1))
      .keyBy(0)
      .timeWindow(Time.seconds(5))
      .sum(1)
      .print()
  }

  def splitSelectFunction(env: StreamExecutionEnvironment): Unit = {
    val value = env.addSource(new MyNonParallelSourceFunction)
    val splits = value.split(
      num =>
        num % 2 match {
          case 0 => List("even")
          case 1 => List("old")
        }
    )
    splits.select("even").print()
  }
}
