package com.zw.demo.twelve

object Note2 {

    // 函数参数的专业术语叫换名调用参数, 和常规的参数不同, 函数在被调用时,
    // 参数表达式不会被求值, 如下 x == 0
    def until(condition: => Boolean)(block: => Unit): Unit = {
        if (!condition) {
            block
            until(condition)(block)
        }
    }

    // 如果在带名函数中使用return的话, 则需要给出其返回类型
    def indexOf(str: String, ch: Char): Int = {
        var i = 0
        until(i == str.length) {
            if (str(i) == ch) return i
            i += 1
        }
        -1
    }

    def main(args: Array[String]): Unit = {
        println(indexOf("test", 'x'))
    }
}
