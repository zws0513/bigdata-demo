package com.zw.demo.thirteen

import scala.collection.{immutable, mutable}

object Two {

    def indexes(str: String): mutable.HashMap[Char, immutable.ListSet[Int]] = {
        val map = new mutable.HashMap[Char, immutable.ListSet[Int]]
        var i = 0
        str.foreach {
            c =>
                map.get(c) match {
                    case Some(value) => map(c) = value + i
                    case None => map += (c -> immutable.ListSet {
                        i
                    })
                }
                i += 1
        }
        map
    }

    def main(args: Array[String]) {
        println(indexes("Mississippi"))
    }
}
