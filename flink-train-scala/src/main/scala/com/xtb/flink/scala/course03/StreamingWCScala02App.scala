package com.xtb.flink.scala.course03

import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time

object StreamingWCScala02App {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val text = env.socketTextStream("localhost", 9999)

    val counts = text.flatMap{_.toLowerCase.split("\\W+").filter(_.nonEmpty)}
        .map(x => WC(x, 1))
        .keyBy("word")
        .timeWindow(Time.seconds(5))
        .sum("count")

    counts.print()

    env.execute("StreamingWCScala02App")
  }

  case class WC(word: String, count: Int)
}
