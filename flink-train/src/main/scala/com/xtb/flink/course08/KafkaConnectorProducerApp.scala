package com.xtb.flink.course08

import java.util.Properties

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer
import org.apache.flink.streaming.connectors.kafka.internals.KeyedSerializationSchemaWrapper

object KafkaConnectorProducerApp {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val data = env.socketTextStream("localhost", 9999)

    data.print()

    // 第一种方式
    val brokerList = "localhost:9092"
    val topic = "test"
    //    val producer = new FlinkKafkaProducer[String](
    //      brokerList, // broker list
    //      topic,      // target topic
    //      new SimpleStringSchema  // serialization schema
    //    )

    // 第二种方式：明确在生产者端指定exactly-once语义
    val prop = new Properties()
    prop.setProperty("bootstrap.servers", brokerList)
    val producer = new FlinkKafkaProducer[String](
      topic,
      new KeyedSerializationSchemaWrapper[String](new SimpleStringSchema[String]()),
      prop,
      FlinkKafkaProducer.Semantic.EXACTLY_ONCE
    )

    // versions 0.10+ allow attaching the records' event timestamp when writing them to Kafka;
    // this method is not available for earlier Kafka versions
    producer.setWriteTimestampToKafka(true)
    data.addSink(producer)

    env.execute("KafkaConnectorProducerApp")
  }

}
