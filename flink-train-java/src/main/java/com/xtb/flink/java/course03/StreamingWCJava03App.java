package com.xtb.flink.java.course03;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;

/**
 * 使用Java API 开发Flink的一个流处理应用程序
 * wc源自一个socket  : nc -lk 9999
 * flink 流计算最后一定要调用执行函数
 */
public class StreamingWCJava03App {
    public static void main(String[] args) throws Exception {

        // step1: get env
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // step2: read
        DataStreamSource<String> text = env.socketTextStream("localhost", 9999);
        // step3: transform
        text.flatMap((FlatMapFunction<String, WC>) (value, collector) -> {
            for (String token : value.toLowerCase().split("\\W+")) {
                if (token.length() > 0) collector.collect(new WC(token, 1));
            }
        }).returns(WC.class)
                .keyBy("word")
                .timeWindow(Time.seconds(5))
                .sum("count").print();

        env.execute("StreamingWCJava03App");
    }

    public static class WC {
        private String word;
        private int count;

        public WC() {
        }

        public WC(String word, int count) {
            this.word = word;
            this.count = count;
        }

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        @Override
        public String toString() {
            return "WC{" +
                    "word='" + word + '\'' +
                    ", count=" + count +
                    '}';
        }
    }
}
