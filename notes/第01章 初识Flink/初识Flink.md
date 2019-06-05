初识Flink

1. Flink概述 
2. Flink Layer 
3. Flink应用程序运行方式 
4. Flink vs Storm vs Spark Streaming
5. Flink应用场景
6. Flink发展趋势
7. 如何学习Flink






一. Flink概述 
  1. Flink 是什么
  2. Unbounded vs Bounded Data
  3. Batch vs Streaming


  Apache Flink is a framework and distributed processing engine for stateful computations over unbounded and bounded data streams. Flink has been designed to run in all common cluster environments, perform computations at in-memory speed and at any scale

  2. Unbounded vs Bounded Data
    a. Unbounded Data 有头无尾   
    b. Bounded Data   有头有尾

    ==>  都可以用Flink进行处理，对应的就是流处理和批处理


二. 业界流处理框架的对比
  1. Spark : Streaming 结构化流  批处理为主
             流式处理是批式处理的一个特例（mini batch）
  2. Flink : 流式为主，批处理是流处理的一个特例
  3. Storm : 流式 Tuple


