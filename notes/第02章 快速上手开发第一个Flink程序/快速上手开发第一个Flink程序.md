快速上手开发第一个Flink程序



1. 开发环境的准备
2. 使用Flink开发一个批处理应用程序（Java/Scala）
3. 使用Flink开发一个实时处理应用程序（Java/Scala）




1. 开发环境的准备
  a. JDK
  b. MAVEN  官网: maven.apache.org
  c. IDEA


2. Flink开发批处理应用程序
  需求: 词频统计wordcount（一个文件，统计单词出现的次数）
        分隔符\t
        统计的结果直接输出到控制台（生产上一定是sink到某个地方）
        实现: Flink + Scala
              Flink + Java 


Java工程创建方式：
mvn archetype:generate                               \
      -DarchetypeGroupId=org.apache.flink              \
      -DarchetypeArtifactId=flink-quickstart-java      \
      -DarchetypeVersion=1.8.0


 out of the box ==> OOTB  开箱即用           


开发流程/开发八股文编程：
	1）set up the batch execution environment
	2）read
	3) transform the resulting DataSet<String> using operations
 	4) execute program




Scala工程创建方式:
mvn archetype:generate                               \
      -DarchetypeGroupId=org.apache.flink              \
      -DarchetypeArtifactId=flink-quickstart-scala     \
      -DarchetypeVersion=1.8.0




3. 使用Flink开发一个实时处理应用程序（Java/Scala）



