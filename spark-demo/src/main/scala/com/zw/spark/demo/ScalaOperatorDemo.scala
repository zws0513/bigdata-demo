package com.zw.spark.demo

import com.google.gson.Gson
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ArrayBuffer

/**
  * <p>
  * transformation类型的算子
  * flatMap  map  mapPartitions
  * mapPartitionsWithIndex
  * reduceByKey groupByKey
  * aggregateByKey  countByKey  sortByKey
  * filter  join  cogroup  intersection
  * union  distinct  sample
  * repartitions  coalesce  cartesian
  * <p>
  * action类型的算子
  * foreach  reduce  takeSample
  * collect  take  saveAsObjectFile
  * saveAsSequenceFile count
  * <p>
  * Created by zhangws on 16/10/30.
  */
object ScalaOperatorDemo {

    def main(args: Array[String]): Unit = {
//        map()
//        filter
        flatMap()
        //    cartesian
        //    mapPartitionsWithIndex
    }

    class User(var id: Int, var name: String, var pwd: String, var sex: Int)

    /**
      * map算子
      * <p>
      * map和foreach算子:
      * 1. 循环map调用元的每一个元素;
      * 2. 执行call函数, 并返回.
      * </p>
      */
    private def map() {
        val conf = new SparkConf().setAppName(ScalaOperatorDemo.getClass.getSimpleName).setMaster("local")
        val sc = new SparkContext(conf)

        val datas: Array[String] = Array(
            "{'id':1,'name':'xl1','pwd':'xl123','sex':2}",
            "{'id':2,'name':'xl2','pwd':'xl123','sex':1}",
            "{'id':3,'name':'xl3','pwd':'xl123','sex':2}")

        sc.parallelize(datas)
            .map(v => {
                new Gson().fromJson(v, classOf[User])
            })
            .foreach(user => {
                println("id: " + user.id
                    + " name: " + user.name
                    + " pwd: " + user.pwd
                    + " sex:" + user.sex)
            })
    }

    def filter() : Unit = {
        val conf = new SparkConf().setAppName(ScalaOperatorDemo.getClass.getSimpleName).setMaster("local")
        val sc = new SparkContext(conf)

        val datas = Array(1, 2, 3, 7, 4, 5, 8)

        sc.parallelize(datas)
            .filter(v => v >= 3)
            .foreach(println)
    }

    def flatMap() : Unit =  {
        val conf = new SparkConf().setAppName(ScalaOperatorDemo.getClass.getSimpleName).setMaster("local")
        val sc = new SparkContext(conf)

        val datas = Array(
            "aa,bb,cc",
            "cxf,spring,struts2",
            "java,C++,javaScript")

        sc.parallelize(datas)
            .flatMap(line => line.split(","))
            .foreach(println)
    }

    def mapPartitions() : Unit = {

    }

    /**
      * 笛卡尔算子
      */
    def cartesianDemo(): Unit = {

    }

    /**
      * mapPartitionsWithIndex算子
      */
    def mapPartitionsWithIndexDemo: Unit = {
        val conf = new SparkConf().setAppName(ScalaOperatorDemo.getClass.getSimpleName).setMaster("local")
        val sc = new SparkContext(conf)

        val namesRdd = sc.parallelize(Array("张三1", "李四1", "王五1", "张三2", "李四2",
            "王五2", "张三3", "李四3", "王五3", "张三4"), 3)

        val resultRDD = namesRdd.mapPartitionsWithIndex((m, n) => {
            val result = ArrayBuffer[String]()
            while (n.hasNext) {
                result.append("分区索引:" + m + "\t" + n.next())
            }
            result.iterator
        })

        resultRDD.foreach(v => println(v))
    }

}
