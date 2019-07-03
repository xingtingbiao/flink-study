Flink Connectors



1. Connectors是什么
2. Hadoop FileSystem
3. Apache Kafka




1. Connectors是什么
	Flink的数据连接器


2. Hadoop FileSystem
	详见官网和代码




3. Apache Kafka
  1) 安装Zookeeper
     https://archive.cloudera.com/cdh5/cdh/5/
     wget http://archive.cloudera.com/cdh5/cdh/5/zookeeper-3.4.5-cdh5.15.1.tar.gz

  2) 安装Kafka
     wget https://archive.apache.org/dist/kafka/1.1.1/kafka_2.11-1.1.1.tgz

     run: ./bin/kafka-server-start.sh -daemon config/server.properties 

     详见官网：kafka.apache.org
     ./bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test
     ./bin/kafka-topics.sh --list --zookeeper localhost:2181 
     ./bin/kafka-console-producer.sh --broker-list localhost:9092 --topic test
     ./bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test --from-beginning




 Flink对接Kafka作为Source使用




