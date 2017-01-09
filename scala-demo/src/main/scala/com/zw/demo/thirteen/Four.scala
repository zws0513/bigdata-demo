package com.zw.demo.thirteen

/**
  * Created by zhangws on 16/11/28.
  */
object Four {

    def filterMap(arr: Array[String], map: Map[String, Int]) = {
        // map.get返回Some(v), 才会被返回
        arr.flatMap(map.get)
    }

    def main(args: Array[String]) {
        println(filterMap(Array("Tom", "Fred", "Harry"),
            Map("Tom" -> 3, "Dick" -> 4, "Harry" -> 5)).mkString(","))
    }
}
