package com.zw.demo.tenth

import java.awt.geom.Ellipse2D

/**
  * Created by zhangws on 16/10/28.
  */
trait RectangleLike {
  this:Ellipse2D.Double =>
  def translate(dx : Int, dy : Int): Unit = {
    this.x += dx
    this.y += dy
  }
  def grow(h : Int, v : Int): Unit = {
    this.width = v
    this.height = h
  }
}
