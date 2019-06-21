package com.xtb.flink.test

import org.apache.flink.api.scala._
import org.junit.Test

@Test
class TestScala {

  @Test
  def hello(): Unit = {
    println("hello world")
  }

  @Test
  def test(): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
    val value = env.fromCollection(List(("abc", List("1", "2")), ("def", List("3", "4"))))
    value.flatMap(x => {
      x._2.map((x._1, _))
    }).print()
  }
}
