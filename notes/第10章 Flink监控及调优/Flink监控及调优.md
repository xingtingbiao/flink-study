Flink监控及调优


1. HistoryServer
2. REST API
3. Metrics
4. 常用优化




1. HistoryServer概述及配置
	详见官网：https://ci.apache.org/projects/flink/flink-docs-release-1.8/monitoring/historyserver.html



3. Metrics
	详见官网：https://ci.apache.org/projects/flink/flink-docs-release-1.8/monitoring/metrics.html


Ganglia




4. Flink中常用的优化策略
	1）资源
	2）并行度
	3）数据倾斜
		group by: 
			random_key + random
			key  - random
		join on xxx=xxx
			repartion-repartion strategy   大大
			broadcast-forward strategy    大小




