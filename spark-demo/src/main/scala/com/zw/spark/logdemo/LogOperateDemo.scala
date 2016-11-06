package com.zw.spark.logdemo

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by zhangws on 16/11/6.
  */
object LogOperateDemo {

    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName(LogOperateDemo.getClass.getSimpleName)
            .setMaster("local")
        val sc = new SparkContext(conf)

//        hw01(sc)
        hw02(sc)
    }

    /**
      * 统计每个设备的上行流量总和、下行流量总和、最早访问时间
      *
      * @param sc Spark运行上下文
      */
    def hw01(sc: SparkContext): Unit = {
        val line = sc.textFile("file:///Users/zhangws/Documents/doc/02.study/01.大数据开发实战/day13/作业/access.log")

        val log = line.map(line => {
            val log = line.split("\t")
            val time = log(0).toLong
            val deviceId = log(1)
            val up = log(2).toLong
            val down = log(3).toLong

            val logDemo = LogDemo(up, down, time)
            (deviceId, logDemo)
        })
        // 第一种方案
        log.reduceByKey((m, n) => {
            val upSum = m.up + n.up
            val downSum = m.down + n.up
            val time = Math.min(m.time, n.time)

            val logDemo = LogDemo(upSum, downSum, time)
            logDemo
        }).foreach(println)

        // 第二种方案
        //        log.groupByKey()
        //            .map(m => {
        //                val deviceId = m._1
        //                var upSum = 0l  // 上行流量
        //                var downSum = 0l // 下行流量
        //                var minTime = Long.MaxValue // 最早访问时间
        //
        //                m._2.foreach(item => {
        //                    upSum += item.up
        //                    downSum += item.down
        //                    minTime = Math.min(minTime, item.time)
        //                })
        //                (deviceId, upSum, downSum, minTime)
        //            })
        //            .foreach(println)
    }

    /**
      * 对上行流量总和、下行流量总和、时间戳进行排序，取前5名
      *
      * @param sc Spark运行上下文
      */
    def hw02(sc: SparkContext): Unit = {
        val line = sc.textFile("file:///Users/zhangws/Documents/doc/02.study/01.大数据开发实战/day13/作业/access.log")

        val log = line.map(line => {
            val log = line.split("\t")
            val time = log(0).toLong
            val deviceId = log(1)
            val up = log(2).toLong
            val down = log(3).toLong

            val logSort = LogSort(up, down, time)
            (logSort, deviceId)
        })
        log.sortByKey(false).take(5)
            .foreach(v => {
                val up = v._1.up
                val down = v._1.down
                val time = v._1.time
                val deviceId = v._2

                println(deviceId + " " + up + " " + down + " " + time)
            })
    }
}
