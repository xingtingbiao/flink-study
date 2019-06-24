DataStream API编程




Flink中使用数据源
StreamExecutionEnvironment.addSource(sourceFunction).
Flink comes with a number of pre-implemented source functions, but you can always write your own custom sources by 
implementing the SourceFunction for non-parallel sources
详见SourceFunction 源码    不可设置并行度

implementing the ParallelSourceFunction interface 
详见源码   可设置并行度的

extending the RichParallelSourceFunction for parallel sources.





Transformation
	split函数：详见代码
	select函数：详见代码


