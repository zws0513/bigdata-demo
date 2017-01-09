package com.zw.demo.fourteen

/**
  * Created by zhangws on 16/11/28.
  */
object Ten extends App {
    def compose(f: Double => Option[Double], g: Double => Option[Double]) = {
        (x: Double) =>
            if (f(x).isEmpty || g(x).isEmpty) None
            else g(x)
    }

    import scala.math.sqrt

    def f(x: Double) = if (x >= 0) Some(sqrt(x)) else None

    def g(x: Double) = if (x != 1) Some(1 / (x - 1)) else None

    val h = compose(f, g)
    println(h(0))
    println(h(1))
    println(h(2))
}
