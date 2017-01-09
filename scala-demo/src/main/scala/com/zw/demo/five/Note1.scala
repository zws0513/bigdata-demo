package com.zw.demo.five

class Person {
    private var name = ""
    private var age = 0

    def this(name: String) {
        this  // 调用主构造器
        this.name = name
    }

    def this(name: String, age: Int) {
        this(name)
        this.age = age
    }
}

class Person2 {

    // Scala会生成一个私有的final字段和一个getter方法,但没有setter
    val timeStamp = new java.util.Date

    private var privateAge = 0

    // 必须初始化字段
    def increment() {
        privateAge += 1
    }

    // 方法默认是公有的
    def current() = privateAge

    // 自定义age的getter方法
    def age = privateAge

    // 自定义age的setter方法
    def age_= (newValue: Int) {
        if (newValue > privateAge) privateAge = newValue
    }
}



object Note1 extends App {
    val fred = new Person2
    fred.age = 20
    println(fred.age)
    fred.age = 12
    println(fred.age)
}
