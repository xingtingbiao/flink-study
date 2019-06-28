Flink中Time及Windows使用


1. Flink中的Time类型   ***

2. Flink中的Windows编程    ****

3. Flink中的watermarks    *****




1. Flink中的Time类型
	Event Time / Processing Time / Ingestion Time
	详见官网：
	https://ci.apache.org/projects/flink/flink-docs-release-1.8/dev/event_time.html

时间时间   10:30
摄入时间   11:00
处理时间   11:30

思考：
对于流处理应用来说，应该以哪一个时间作为基准时间来进行业务逻辑处理比较合理呢？

幂等性


env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)



2. Flink中的Windows编程
	详见官网：https://ci.apache.org/projects/flink/flink-docs-release-1.8/dev/stream/operators/windows.html


	Window Assigners
	窗口分配器：定义如何将数据分配给窗口
	A WindowAssigner is responsible for assigning each incoming element to one or more windows.

	内置四种窗口分配器：
	tumbling windows   滚动窗口
	sliding windows    滑动窗口
	session windows    会话窗口
	global windows     全局窗口

	[start timestamp, end timestamp) 左闭右开




