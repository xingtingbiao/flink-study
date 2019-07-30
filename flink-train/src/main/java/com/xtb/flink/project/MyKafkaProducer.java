package com.xtb.flink.project;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

public class MyKafkaProducer {

    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        String topic = "test";

        while (true) {
            StringBuilder builder = new StringBuilder();
            builder.append("imooc").append("\t")
                    .append("CN").append("\t")
                    .append(getLevel()).append("\t")
                    .append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())).append("\t")
                    .append(getIp()).append("\t")
                    .append(getDomain()).append("\t")
                    .append(getTraffic()).append("\t");
            System.out.println(builder.toString());
            producer.send(new ProducerRecord<>(topic, builder.toString()));

            Thread.sleep(2000);
        }
    }

    private static long getTraffic() {
        return new Random().nextInt(10000);
    }

    private static String getDomain() {
        String[] domains = {
                "v1.go2yd.com",
                "v2.go2yd.com",
                "v3.go2yd.com",
                "v4.go2yd.com",
                "vim.go2yd.com"
        };
        return domains[new Random().nextInt(domains.length)];
    }

    private static String getIp() {
        String[] ips = {"223.104.18.110", "113.101.75.194", "27.17.127.135"
                , "183.225.139.16", "112.1.66.34", "175.148.211.190"
                , "183.227.58.21", "59.83.198.84", "117.28.38.28", "117.59.39.169"};
        return ips[new Random().nextInt(ips.length)];
    }

    public static String getLevel() {
        String[] levels = {"M", "E"};
        return levels[new Random().nextInt(levels.length)];
    }


}
