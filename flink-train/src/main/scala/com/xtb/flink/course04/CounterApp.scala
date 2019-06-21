package com.xtb.flink.course04

import org.apache.flink.api.common.accumulators.LongCounter
import org.apache.flink.api.common.functions.RichMapFunction
import org.apache.flink.api.scala._
import org.apache.flink.configuration.Configuration

/**
  * 实现计数器
  */
object CounterApp {

  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
    env.fromCollection(1 to 10).map(new RichMapFunction[Int, Int] {
      //step1: 定义一个计数器
      val counter = new LongCounter()
      override def map(in: Int): Int = {
        counter.add(1)
        in
      }
      override def open(parameters: Configuration): Unit = {
        // step2: 注册一个计数器
        getRuntimeContext.addAccumulator("counter", counter)
      }
    }).setParallelism(5).writeAsText("out")
    val result = env.execute("CounterApp")
    val value = result.getAccumulatorResult[Long]("counter")
    println(s"num = $value")
  }
}
