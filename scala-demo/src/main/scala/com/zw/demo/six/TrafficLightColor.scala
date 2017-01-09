package com.zw.demo.six

/**
  * Created by zhangws on 16/11/24.
  */
object TrafficLightColor extends Enumeration {
    type TrafficLightColor = Value
    val Red, Yellow, Green = Value
}

object TrafficLightColor2 extends Enumeration {
    val Red = Value(0, "Stop")
    val Yellow = Value(10)  // 缺省名称为字段名
    val Green = Value("Go") // ID不指定时, 将在前一个枚举值基础上加一, 从零开始
}

