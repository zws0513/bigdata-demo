package com.zw.spark.logdemo

/**
  * 移动互联网日志数据统计分析
  *
  * Created by zhangws on 16/11/6.
  */
class LogDemo(val up:Long, val down:Long, val time:Long) extends Serializable {

}

object LogDemo {
    def apply(up:Long, down:Long, time:Long): LogDemo = {
        new LogDemo(up, down, time)
    }
}
