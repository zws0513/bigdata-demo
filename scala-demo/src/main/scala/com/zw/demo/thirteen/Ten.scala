package com.zw.demo.thirteen

import scala.collection.immutable.HashMap

/**
  * Created by zhangws on 16/11/28.
  */
object Ten {
    def main(args: Array[String]) {
        val str = "abdcsdcd"
        val frequencies = str.par.aggregate(HashMap[Char, Int]())(
            {
                (a, b) =>
                    a + (b -> (a.getOrElse(b, 0) + 1))
            }
            , {
                (map1, map2) =>
                    (map1.keySet ++ map2.keySet).foldLeft(HashMap[Char, Int]()) {
                        (result, k) =>
                            result + (k -> (map1.getOrElse(k, 0) + map2.getOrElse(k, 0)))
                    }
            }
        ).foreach(println)
    }
}
