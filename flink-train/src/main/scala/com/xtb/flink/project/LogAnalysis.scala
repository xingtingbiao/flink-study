package com.xtb.flink.project

import java.text.SimpleDateFormat
import java.util.{Date, Properties}

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.watermark.Watermark
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer
import org.apache.flink.streaming.connectors.elasticsearch.ElasticsearchSinkFunction
import org.apache.flink.streaming.connectors.elasticsearch6.ElasticsearchSink
import org.apache.flink.api.common.functions.RuntimeContext
import org.apache.flink.streaming.connectors.elasticsearch.RequestIndexer

import org.apache.http.HttpHost
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.client.Requests
import org.slf4j.LoggerFactory

/**
  * 统计一分钟内每个域名访问产生的流量
  */
object LogAnalysis {

  val log = LoggerFactory.getLogger("LogAnalysis")


  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)

    val topic = "test"
    val properties = new Properties()
    properties.setProperty("bootstrap.servers", "localhost:9092")
    properties.setProperty("group.id", "test")
    val consumer = new FlinkKafkaConsumer[String](topic, new SimpleStringSchema(), properties)
    val data = env.addSource(consumer)
    val logData = data.map(x => {
      val splits = x.split("\t")
      val level = splits(2)
      val timeStr = splits(3)
      var time = 0l
      try {
        time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timeStr).getTime
      } catch {
        case e: Exception => {
          log.error(s"time parse error $timeStr", e.getMessage)
          // e.printStackTrace()
        }
      }
      val domain = splits(5)
      val traffic = splits(6).toLong

      (level, time, domain, traffic)
    }).filter(x => x._1.equals("E") && x._2.toLong != 0l)
      //      .filter(_._2.toLong != 0l).filter(_._1.equals("E"))
      .map(x => (x._2, x._3, x._4))

    /**
      * 在生产环境上，一定要考虑代码的健壮性和业务数据的准确性
      * 脏数据或者不符合业务逻辑的数据需要过滤掉，再进行相应的业务逻辑处理
      */

    //    logData.print()

    val resultData = logData.assignTimestampsAndWatermarks(new AssignerWithPeriodicWatermarks[(Long, String, Long)]() {
      val maxOutOfOrderness = 3500L // 3.5 seconds
      var currentMaxTimestamp: Long = _

      override def extractTimestamp(element: (Long, String, Long), previousElementTimestamp: Long): Long = {
        val timestamp = element._1
        currentMaxTimestamp = Math.max(timestamp, currentMaxTimestamp)
        timestamp
      }

      override def getCurrentWatermark: Watermark = {
        // return the watermark as current highest timestamp minus the out-of-orderness bound
        new Watermark(currentMaxTimestamp - maxOutOfOrderness)
      }
    }).keyBy(1)
      .window(TumblingEventTimeWindows.of(Time.minutes(1)))
      .reduce((v1, v2) => (v1._1, v1._2, v1._3 + v2._3))
      .map(x => {
        val time = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(x._1))
        (time, x._2, x._3)
      })

    // resultData.print()

    val httpHosts = new java.util.ArrayList[HttpHost]
    httpHosts.add(new HttpHost("127.0.0.1", 9200, "http"))

    val esSinkBuilder = new ElasticsearchSink.Builder[(String, String, Long)](
      httpHosts,
      new ElasticsearchSinkFunction[(String, String, Long)] {
        def createIndexRequest(element: (String, String, Long)): IndexRequest = {
          val json = new java.util.HashMap[String, Any]
          json.put("time", element._1)
          json.put("domain", element._2)
          json.put("traffics", element._3)
          val id = element._1 + "-" + element._2

          Requests.indexRequest()
            .index("cdn")
            .`type`("traffic")
            .id(id)
            .source(json)
        }

        @Override
        def process(element: (String, String, Long), ctx: RuntimeContext, indexer: RequestIndexer): Unit = {
          indexer.add(createIndexRequest(element))
        }
      }
    )

    // configuration for the bulk requests; this instructs the sink to emit after every element, otherwise they would be buffered
    esSinkBuilder.setBulkFlushMaxActions(1)

    // finally, build and add the sink to the job's pipeline
    resultData.addSink(esSinkBuilder.build)

    env.execute("LogAnalysis")
  }

}
