package com.xtb.flink.java.course02;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

/**
 * 使用Java API 开发Flink的一个流处理应用程序
 * wc源自一个socket  : nc -lk 9999
 * flink 流计算最后一定要调用执行函数
 */
public class StreamingWCJavaApp {
    public static void main(String[] args) throws Exception {

        // step1: get env
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // step2: read
        DataStreamSource<String> text = env.socketTextStream("localhost", 9999);
        // step3: transform
        text.flatMap((FlatMapFunction<String, Tuple2<String, Integer>>) (value, collector) -> {
            for (String token : value.toLowerCase().split("\\W+")) {
                if (token.length() > 0) collector.collect(new Tuple2<>(token, 1));
            }
        }).returns(Types.TUPLE(Types.STRING, Types.INT))
                .keyBy(0)
                .timeWindow(Time.seconds(5))
                .sum(1)
                .print();

        env.execute("StreamingWCJavaApp");
    }
}
