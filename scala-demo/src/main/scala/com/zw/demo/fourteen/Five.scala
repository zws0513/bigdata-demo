package com.zw.demo.fourteen

/**
  * Created by zhangws on 16/11/28.
  */
object Five extends App {
    def leafSum(list: List[Any]): Int = {
        var total = 0
        list.foreach {
            case l: List[Any] => total += leafSum(l)
            case i: Int => total += i
        }
        total
    }

    val l: List[Any] = List(List(3, 8), 2, List(5))
    println(leafSum(l))
}
