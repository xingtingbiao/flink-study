package com.xtb.flink.java.course02;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;

/**
 * 使用Java API 开发Flink的一个批处理应用程序
 */
public class BatchWCJavaApp {
    public static void main(String[] args) throws Exception {
        String input = "./data";
        // step1: get execution environment
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        // step2: read file
        DataSource<String> text = env.readTextFile(input);
        // step3: transform
        // notes: 每当lambda表达式使用Java泛型时，您需要显式声明类型信息. https://flink.sojb.cn/dev/java_lambdas.html
        text.flatMap((FlatMapFunction<String, Tuple2<String, Integer>>) (value, collector) -> {
            String[] tokens = value.toLowerCase().split("\\W+");
            for (String token : tokens) {
                if (token.length() > 0) collector.collect(new Tuple2<>(token, 1));
            }
        }).returns(Types.TUPLE(Types.STRING, Types.INT))
                .groupBy(0)
                .sum(1).print();
        //text.print();
    }
}
