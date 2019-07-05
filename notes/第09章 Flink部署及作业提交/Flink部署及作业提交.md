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

	./bin/start-cluster.sh  # Start Flink
	Check the Dispatcher’s web frontend at http://localhost:8081

	tail log/flink-*-standalonesession-*.log

	example: https://ci.apache.org/projects/flink/flink-docs-release-1.8/tutorials/local_setup.html





3. Flink Standalone模式部署及参数详解
	
	1) Standalone-分布式
	  详见官网：https://ci.apache.org/projects/flink/flink-docs-release-1.8/ops/deployment/cluster_setup.html

      a. Java 1.8.x or higher,
      b. ssh (sshd must be running to use the Flink scripts that manage remote components)
      c. conf
         flink-conf.yaml
            jobmanager.rpc.address: 10.0.0.1   配置主节点的IP

         jobmanager    主节点
         taskmanager   从节点

         slaves
            每一行配置上从节点的IP/host

       d. 常用配置
          jobmanager.rpc.address   主节点的地址 
          jobmanager.heap.mb and taskmanager.heap.mb    主节点和从节点的最大内存


    the amount of available memory per JobManager (jobmanager.heap.mb),
    the amount of available memory per TaskManager (taskmanager.heap.mb),
    the number of available CPUs per machine (taskmanager.numberOfTaskSlots),
    the total number of CPUs in the cluster (parallelism.default) and
    the temporary directories (io.tmp.dirs)





4. YARN模式    企业级用的最多的方式  *****

	1) Flink on YARN的两种方式
	   ./bin/yarn-session.sh -n 1 -jm 1024m -tm 1024m
	   -n 指的是taskmanager的数量

    注意: ***
    flink1.8必须配置HADOOP_HOME和HADOOP_CLASSPATH
    export HADOOP_HOME=/Users/xingtingbiao/xtb/app/hadoop-2.6.0-cdh5.15.1
    export HADOOP_CLASSPATH=`hadoop classpath`



5. 常用属性
	详见官网：https://ci.apache.org/projects/flink/flink-docs-release-1.8/ops/config.html



6. Flink scala shell





