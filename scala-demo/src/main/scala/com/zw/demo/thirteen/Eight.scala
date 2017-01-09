package com.zw.demo.thirteen

/**
  * Created by zhangws on 16/11/28.
  */
object Eight {

    def toMultiDim(arr: Array[Double], n: Int): Array[Array[Double]] = {
        // grouped产出下标为[0, n)的元素,然后是[n, 2*n)的元素
        arr.grouped(n).toArray
    }

    def main(args: Array[String]) {
        val arr = Array(1.0, 2, 3, 4, 5, 6)
        toMultiDim(arr, 3).foreach(a => println(a.mkString(",")))
    }
}
