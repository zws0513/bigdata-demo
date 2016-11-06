package com.zw.spark.logdemo

/**
  * 移动互联网日志数据统计排序
  *
  * Created by zhangws on 16/11/6.
  */
class LogSort(val up: Long, val down: Long, val time: Long)
    extends Ordered[LogSort] with Serializable {

    /**
      * 排序上行流量\下行流量\时间戳
      *
      * @param that
      * @return
      */
    override def compare(that: LogSort): Int = {
        if (this.up != that.up) {
            (this.up - that.up).toInt
        } else if (this.down != that.down) {
            (this.down - that.down).toInt
        } else {
            (this.time - that.time).toInt
        }
    }
}

object LogSort {
    def apply(up: Long, down: Long, time: Long): LogSort = {
        new LogSort(up, down, time)
    }
}
