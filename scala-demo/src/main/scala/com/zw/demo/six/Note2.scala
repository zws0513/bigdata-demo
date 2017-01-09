package com.zw.demo.six

import com.zw.demo.six.TrafficLightColor._

object Note2 {

    def doWhat(color: TrafficLightColor) = {
        if (color == Red) "stop"
        else if (color == Yellow) "hurry up"
        else "go"
    }

    def main(args: Array[String]) {
        // values方法获得所有枚举值得集
        for (c <- TrafficLightColor2.values) println(c.id + ": " + c)

        // 通过枚举的ID或名称来进行查找定位
        println(TrafficLightColor(2))
        println(TrafficLightColor.withName("Red"))
    }
}
