package com.zw.demo.fourteen

/**
  * Created by zhangws on 16/11/28.
  */
object Three extends App {

    def swap(arr: Array[Any]) = {
        arr match {
            case Array(first, second, rest@_*) => Array(second, first) ++ rest
            case _ => arr
        }
    }

    println(swap(Array("1", "2", "3", "4")).mkString(","))
}
