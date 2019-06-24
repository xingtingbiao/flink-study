package com.xtb.flink.course05

import org.apache.flink.streaming.api.functions.source.SourceFunction

class MyNonParallelSourceFunction extends SourceFunction[Long] {
  var isRunning = true
  var count = 1L

  override def run(ctx: SourceFunction.SourceContext[Long]): Unit = {
    while (isRunning & count < 1000) {
      ctx.collect(count)
      count += 1
      Thread.sleep(1000)
    }
  }

  override def cancel(): Unit = {
    isRunning = false
  }
}
