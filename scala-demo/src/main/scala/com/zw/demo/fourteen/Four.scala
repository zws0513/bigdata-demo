package com.zw.demo.fourteen

/**
  * Created by zhangws on 16/11/28.
  */
object Four extends App {

    abstract class Item

    case class Multiple(num: Int, item: Item) extends Item

    case class Article(description: String, price: Double) extends Item

    case class Bundle(description: String, discount: Double, item: Item*) extends Item

    def price(it: Item): Double = it match {
        case Article(_, a) => a
        case Bundle(_, disc, its@_*) => its.map(price).sum - disc
        case Multiple(n, t) => n * price(t)
    }

    val p = price(Multiple(10, Article("Blackwell Toster", 29.95)))
    println(p)
}
