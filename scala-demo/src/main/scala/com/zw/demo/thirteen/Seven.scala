package com.zw.demo.thirteen

/**
  * Created by zhangws on 16/11/28.
  */
object Seven {
    def main(args: Array[String]) {
        val prices = List(5.0, 20.0, 9.95)
        val quantities = List(10, 2, 1)
        println((prices zip quantities) map {
            Function.tupled(_ * _)
        })

    }
}
