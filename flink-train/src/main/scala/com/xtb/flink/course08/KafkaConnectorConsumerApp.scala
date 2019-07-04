package com.xtb.flink.course08

import java.util.Properties

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.CheckpointingMode
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer

object KafkaConnectorConsumerApp {

  def main(args: Array[String]): Unit = {
    val properties = new Properties()
    properties.setProperty("bootstrap.servers", "localhost:9092")
    // only required for Kafka 0.8
    properties.setProperty("zookeeper.connect", "localhost:2181")
    properties.setProperty("group.id", "test")
    val topic = "test"
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    //设置checkpoint的一些常用参数设置
//    env.enableCheckpointing(5000)
//    env.getCheckpointConfig.setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE)
//    env.getCheckpointConfig.setCheckpointTimeout(50000)
//    env.getCheckpointConfig.setMaxConcurrentCheckpoints(1)


    // 详见官网, 这里默认消费者端默认采用了setStartFromGroupOffsets实现exactly-once语义 ****
    val consumer = new FlinkKafkaConsumer[String](topic, new SimpleStringSchema(), properties)
    val data = env.addSource(consumer)
    data.print()

    env.execute("KafkaConnectorConsumerApp")
  }

}
