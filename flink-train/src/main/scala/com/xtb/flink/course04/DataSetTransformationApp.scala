package com.xtb.flink.course04

import java.util.concurrent.atomic.AtomicInteger

import org.apache.flink.api.scala._

object DataSetTransformationApp {
//  var i = 0
  private val i = new AtomicInteger(1)
  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
    mapPartition(env)
  }

  private def mapPartition(env: ExecutionEnvironment): Unit = {
    val value = env.fromCollection(1 to 100).setParallelism(10)
    value.mapPartition(x => {
      val conn = getConn
      x.map((_, conn))
    }).print()
  }

  def getConn: Int = {
    val c = i.getAndIncrement()
    println(s"$c......")
    c
  }
}
