package com.zw.demo.thirteen

object Three {

    def removeZero(list: List[Int]): List[Int] = {
        list.filter(_ != 0)
    }

    def main(args: Array[String]) {
        println(removeZero(List(3, 25, 0, 2, 0, 0)))
    }
}
