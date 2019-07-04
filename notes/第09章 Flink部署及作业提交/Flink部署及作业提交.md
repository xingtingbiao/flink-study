Flink部署及作业提交



1. Flink源码编译
2. Standalone单机模式
3. Standalone集群模式
4. YARN模式
5. 常用属性
6. Flink scala shell





1. Flink源码编译
	1) 下载源码包：wget https://archive.apache.org/dist/flink/flink-1.8.0/flink-1.8.0-src.tgz
	2) 编译：mvn clean install -DskipTests -Dfast -Pvendor-repos -Dhadoop.version=2.6.0-cdh5.15.1




2. Standalone单机模式
	cd flink-dist/ 目录（成功编译后生成）

