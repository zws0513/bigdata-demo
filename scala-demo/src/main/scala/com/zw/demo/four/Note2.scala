package com.zw.demo.four

import scala.collection.JavaConversions.mapAsScalaMap
import scala.collection.JavaConversions.propertiesAsScalaMap
import scala.collection.JavaConversions.mapAsJavaMap
import java.awt.font.TextAttribute._

object Note2 {

    def main(args: Array[String]) {
        // Java映射转Scala映射
        val scores: scala.collection.mutable.Map[String, Int] = new java.util.TreeMap[String, Int]

        // 从java.util.Properties到Map[String, String]的转换
        val props: scala.collection.Map[String, String] = System.getProperties

        // Scala映射转Java映射
        val attrs = Map(FAMILY -> "Serif", SIZE -> 12)
        val font = new java.awt.Font(attrs)
    }
}
