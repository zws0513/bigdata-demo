package com.zw.spark.broadcastdemo

import org.apache.spark.{SparkContext, SparkConf}

/**
  * Created by zhangws on 16/11/6.
  */
object ValidateDemo {

    def main(args: Array[String]) {
        val conf = new SparkConf().setAppName(ValidateDemo.getClass.getSimpleName).setMaster("local")
        val sc = new SparkContext(conf)

        val flag = 10
        val bcFlag = sc.broadcast(flag)

        val testRDD = sc.parallelize(1 to 5, 3)
        testRDD.map(num => num * bcFlag.value)
            .collect().foreach(println)

        val tempAccumulator = sc.longAccumulator

        testRDD.foreach(m => tempAccumulator.add(m))
        println(tempAccumulator.value)
    }

}
