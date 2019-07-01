package com.xtb.flink.course07

import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time

object WindowScalaApp {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val text = env.socketTextStream("localhost", 9999)

    text.flatMap(_.toLowerCase.split("\\W+").filter(_.nonEmpty))
      .map((_, 1))
      .keyBy(0)
//      .timeWindow(Time.seconds(5))   // 多看源码
      .timeWindow(Time.seconds(10), Time.seconds(5))  // 滑动窗口
      .sum(1)
      .print()

    env.execute("WindowScalaApp")
  }

}
