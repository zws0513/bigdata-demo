package com.zw.mllib

import org.apache.spark.mllib.fpm.FPGrowth
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Example for mining frequent itemsets using FP-growth.
 */
object FPGrowthExample {
  def main(args: Array[String]) {
    var masterUrl = "local[1]"
    var dataPath = "data/mllib/sample_fpgrowth.txt"

    if (args.length > 0) {
      masterUrl = args(0)
    } else if(args.length > 1) {
      dataPath = args(1)
    }
    val input: String = dataPath
    val minSupport: Double = 0.3
    val numPartition: Int = 10

    val conf = new SparkConf().setMaster(masterUrl).setAppName("FPGrowthExample")
    val sc = new SparkContext(conf)
    val transactions = sc.textFile(input).map(_.split(" ")).cache()

    println(s"Number of transactions: ${transactions.count()}")

    val model = new FPGrowth()
      .setMinSupport(minSupport)
      .setNumPartitions(numPartition)
      .run(transactions)

    println(s"Number of frequent itemsets: ${model.freqItemsets.count()}")

    model.freqItemsets.collect().foreach { itemset =>
      println(itemset.items.mkString("[", ",", "]") + ", " + itemset.freq)
    }
    sc.stop()
  }
}
// scalastyle:on println
