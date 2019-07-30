package com.xtb.flink.project

import java.util.Properties

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer

object LogAnalysis {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val topic = "test"
    val properties = new Properties()
    properties.setProperty("bootstrap.servers", "localhost:9092")
    properties.setProperty("group.id", "test")
    val consumer = new FlinkKafkaConsumer[String](topic, new SimpleStringSchema(), properties)
    val data = env.addSource(consumer)
    data.print()

    env.execute("LogAnalysis")
  }

}
