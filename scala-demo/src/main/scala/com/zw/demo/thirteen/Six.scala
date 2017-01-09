package com.zw.demo.thirteen

object Six {

    def main(args: Array[String]) {
        val lst = List(1, 2, 3, 4, 5)
        // foldRight方法, List[Int]()是初始值(为空)被增加到右边
        // :: 与 +:相同, 头部添加
        println((lst :\ List[Int]()) (_ :: _))
        // foldLeft方法, List[Int]()是初始值(为空)被增加到左边
        // :+ 尾部添加
        println((List[Int]() /: lst) (_ :+ _))
        println((List[Int]() /: lst) ((a, b) => b :: a))
    }
}
