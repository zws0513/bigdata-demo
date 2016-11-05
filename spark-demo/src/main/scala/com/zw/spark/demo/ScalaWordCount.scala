package com.zw.spark.demo

import org.apache.spark.{SparkConf, SparkContext}

/**
  * 统计单词个数
  *
  * Created by zhangws on 16/10/21.
  */
object ScalaWordCount {
    def main(args: Array[String]): Unit = {
        countDemo()
        //    countSortByKeyDemo()
    }

    def countDemo(): Unit = {
        val conf = new SparkConf().setAppName(ScalaWordCount.getClass.getSimpleName)
        val sc = new SparkContext(conf)
        //    val textFile = sc.textFile("file:///Users/zhangws/opt/spark-2.0.1-bin-hadoop2.6/README.md")
        val textFile = sc.textFile("file:///home/zkpk/spark-2.0.1/README.md")
        val words = textFile.flatMap(line => line.split(" "))
        val wordPairs = words.map(word => (word, 1))
        val wordCounts = wordPairs.reduceByKey((a, b) => a + b)
        println("wordCounts: ")
        wordCounts.collect().foreach(println)
    }

    def countSortByKeyDemo(): Unit = {
        val conf = new SparkConf().setAppName(ScalaWordCount.getClass.getSimpleName)
        val sc = new SparkContext(conf)
        sc.textFile("file:///Users/zhangws/opt/spark-2.0.1-bin-hadoop2.6/README.md")
            .flatMap(line => line.split(" "))
            .map(word => (word, 1))
            .reduceByKey((a, b) => a + b)
            .map(m => (m._2, m._1))
            .sortByKey()
            .map(m => (m._2, m._1))
            .foreach(println)
    }
}
