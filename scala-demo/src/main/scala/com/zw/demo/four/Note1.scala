package com.zw.demo.four

object Note1 {
    def main(args: Array[String]) {
        // 定义不可变映射
        val scores1 = Map("Alice" -> 10, "Bob" -> 3)
        // 获取映射中的值
        println(scores1("Alice"))
        // 如果包含键"Bill", 返回对应值, 否则返回
        println(scores1.getOrElse("Bill", 1))

        // 定义可变映射
        val scores2 = scala.collection.mutable.Map("Alice" -> 10, "Bob" -> 3)
        // 更新映射中的值
        scores2("Alice") = 11
        println(scores2("Alice"))

        // 定义空映射
        val scores3 = new scala.collection.mutable.HashMap[String, Int]
        // 添加关系
        scores3 += ("Alice" -> 3, "Bob" -> 4)
        for ((k, v) <- scores3) println("k = " + k + " v = " + v)
        // 移除某个键
        scores3 -= "Bob"
        for ((k, v) <- scores3) println("k = " + k + " v = " + v)

        //
        val scores4 = scala.collection.immutable.SortedMap("Alice" -> 10, "Bob" -> 3)
    }
}
