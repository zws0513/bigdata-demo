package com.zw.demo.fourteen

/**
  * Created by zhangws on 16/11/28.
  */
object Two extends App {
    def swap[S, T](tup: (S, T)) = {
        tup match {
            case (a, b) => (b, a)
        }
    }

    println(swap[String, Int](("1", 2)))
}
