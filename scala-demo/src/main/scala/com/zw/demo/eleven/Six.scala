package com.zw.demo.eleven

import scala.collection.mutable.ArrayBuffer

class ASCIIArt(str: String) {
    val arr: ArrayBuffer[ArrayBuffer[String]] = new ArrayBuffer[ArrayBuffer[String]]()

    if (str != null && !str.trim.equals("")) {
        str.split("[\r\n]+").foreach(
            line => {
                val s = new ArrayBuffer[String]()
                s += line
                arr += s
            }
        )
    }

    def this() {
        this("")
    }

    /**
      * 横向结合
      * @param other
      * @return
      */
    def +(other: ASCIIArt): ASCIIArt = {
        val art = new ASCIIArt()
        // 获取最大行数
        val length = if (this.arr.length >= other.arr.length) this.arr.length else other.arr.length
        for (i <- 0 until length) {
            val s = new ArrayBuffer[String]()
            // 获取this中的行数据, 行数不足,返回空行
            val thisArr: ArrayBuffer[String] = if (i < this.arr.length) this.arr(i) else new ArrayBuffer[String]()
            // 获取other中的行数据, 行数不足,返回空行
            val otherArr: ArrayBuffer[String] = if (i < other.arr.length) other.arr(i) else new ArrayBuffer[String]()
            // 连接this
            thisArr.foreach(s += _)
            // 连接other
            otherArr.foreach(s += _)
            art.arr += s
        }
        art
    }

    /**
      * 纵向结合
      * @param other
      * @return
      */
    def *(other: ASCIIArt): ASCIIArt = {
        val art = new ASCIIArt()
        this.arr.foreach(art.arr += _)
        other.arr.foreach(art.arr += _)
        art
    }

    override def toString = {
        var ss: String = ""
        arr.foreach(ss += _.mkString(" ") + "\n")
        ss
    }
}

object Six extends App {
    // stripMargin: "|"符号后面保持原样
    val a = new ASCIIArt(
        """ /\_/\
          |( ' ' )
          |(  -  )
          | | | |
          |(__|__)
          | """.stripMargin)
    val b = new ASCIIArt(
        """   -----
          | / Hello \
          |<  Scala |
          | \ Coder /
          |   -----
          | """.stripMargin)
    println(a + b * b)
    println((a + b) * b)
    println(a * b)
}
