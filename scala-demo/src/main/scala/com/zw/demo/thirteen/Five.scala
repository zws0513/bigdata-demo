package com.zw.demo.thirteen

import scala.collection.mutable

trait MktString {
    this: mutable.Iterable[String] =>
    def mktString(split: String = "") = if (this != Nil) this.reduceLeft(_ + split + _)
}

object Five {

    def main(args: Array[String]) {
        var test = new scala.collection.mutable.HashSet[String] with MktString
        test += "1"
        test += "2"
        test += "3"
        println(test.mktString(","))
    }
}
