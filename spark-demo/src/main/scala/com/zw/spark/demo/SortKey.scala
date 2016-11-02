package com.zw.spark.demo

import org.apache.spark.{SparkContext, SparkConf}

/**
  * Spark中的二次排序
  *
  * Created by zhangws on 16/10/30.
  */
class SortKey(val first:Int, val second:Int) extends Ordered[SortKey] with Serializable {

  override def compare(that : SortKey): Int = {
    if (this.first - that.first != 0) {
      this.first - that.first
    } else {
      this.second - that.second
    }
  }
}

object SortKey {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName(SortKey.getClass.getSimpleName).setMaster("local")
    val sc = new SparkContext(conf)

    val lines = sc.textFile("file:///Users/zhangws/Documents/num")
    lines.map(line => {
      val info = line.split(" ")
      (new SortKey(info(0).toInt, info(1).toInt), line)
    }).sortByKey(ascending = false)
      .map(m => m._2)
      .foreach(println)
  }
}
