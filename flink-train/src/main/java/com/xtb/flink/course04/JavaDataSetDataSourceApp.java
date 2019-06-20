package com.xtb.flink.course04;

import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.io.CsvReader;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;

import java.util.Arrays;

public class JavaDataSetDataSourceApp {

    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        // fromCollection(env);
        csvFile(env);

    }

    private static void csvFile(ExecutionEnvironment env) throws Exception {
        String path = "./people.csv";
        DataSource<Tuple2<String, Integer>> value = env.readCsvFile(path)
                .ignoreFirstLine()
                .includeFields("110").types(String.class, Integer.class);
        value.print();
    }

    private static void fromCollection(ExecutionEnvironment env) throws Exception {
        DataSource<String> value = env.fromCollection(Arrays.asList("a", "b", "a"));
        value.print();
    }
}
