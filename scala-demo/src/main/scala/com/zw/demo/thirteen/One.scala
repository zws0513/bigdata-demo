package com.zw.demo.thirteen

import scala.collection.mutable

object One {

    def indexes(str: String): mutable.HashMap[Char, mutable.SortedSet[Int]] = {
        val map = new mutable.HashMap[Char, mutable.SortedSet[Int]]
        var i = 0
        str.foreach {
            c =>
                map.get(c) match {
                    case Some(value) => map(c) = value + i
                    case None => map += (c -> mutable.SortedSet {
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
