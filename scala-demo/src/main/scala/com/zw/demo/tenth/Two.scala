package com.zw.demo.tenth

/**
  * Created by zhangws on 16/10/28.
  */
object Two {

  def main(args: Array[String]) {
    val arr : Array[OrderedPoint] = new Array[OrderedPoint](3)
    arr(0) = new OrderedPoint(4,5)
    arr(1) = new OrderedPoint(2,2)
    arr(2) = new OrderedPoint(4,6)
    val sortedArr = arr.sortWith(_ > _)
    sortedArr.foreach((point:OrderedPoint) => println("x = " + point.getX + " y = " + point.getY))
  }

}
