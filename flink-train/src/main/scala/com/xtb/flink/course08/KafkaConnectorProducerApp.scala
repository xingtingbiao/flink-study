package com.xtb.flink.course08

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer

object KafkaConnectorProducerApp {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val data = env.socketTextStream("localhost", 9999)

    data.print()

    val brokerList = "localhost:9092"
    val topic = "test"
    val producer = new FlinkKafkaProducer[String](
      brokerList, // broker list
      topic,      // target topic
      new SimpleStringSchema  // serialization schema
    )

    // versions 0.10+ allow attaching the records' event timestamp when writing them to Kafka;
    // this method is not available for earlier Kafka versions
    producer.setWriteTimestampToKafka(true)
    data.addSink(producer)

    env.execute("KafkaConnectorProducerApp")
  }

}
