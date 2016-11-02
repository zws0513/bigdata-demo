package com.zw.demo.tenth

import java.awt.Point

/**
  * Created by zhangws on 16/10/28.
  */
class OrderedPoint(
                  x:Int,
                  y:Int
                  ) extends Point(x:Int, y:Int) with Ordered[Point]{

  override def compare(that: Point): Int = {
    if (this.x <= that.x && this.y < that.y) -1
    else if (this.x == that.x && this.y == that.y) 0
    else 1
  }
}
