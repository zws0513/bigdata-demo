package com.zw.demo.tenth

/**
  * Created by zhangws on 16/10/28.
  */
object One {

  def main(args: Array[String]): Unit = {
    val egg = new java.awt.geom.Ellipse2D.Double(5, 10, 20, 30) with RectangleLike

    println("x = " + egg.getX + " y = " + egg.getY)
    egg.translate(10, -10)
    println("x = " + egg.getX + " y = " + egg.getY)

    println("w = " + egg.getWidth + " h = " + egg.getHeight)
    egg.grow(10, 21)
    println("w = " + egg.getWidth + " h = " + egg.getHeight)
  }
}
