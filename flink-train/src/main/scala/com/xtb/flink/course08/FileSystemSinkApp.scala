package com.xtb.flink.course08

import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.connectors.fs.StringWriter
import org.apache.flink.streaming.connectors.fs.bucketing.{BucketingSink, DateTimeBucketer}

object FileSystemSinkApp {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val data = env.socketTextStream("localhost", 9999)

    val path = "file:///Users/xingtingbiao/xtb/project/my_work/flink-study/sink"
    // TODO 该方式写出的文件都比较小, 不适合直接写入HDFS
    val sink = new BucketingSink[String](path)
    sink.setBucketer(new DateTimeBucketer("yyyy-MM-dd--HHmm"))
    sink.setWriter(new StringWriter())
//    sink.setBatchSize(1024 * 1024 * 400) // this is 400 MB,
//    sink.setBatchRolloverInterval(20 * 60 * 1000); // this is 20 mins
    sink.setBatchRolloverInterval(20 * 1000)

    data.addSink(sink)

    env.execute("FileSystemSinkApp")
  }

}
