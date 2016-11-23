package com.zw.demo.eleven

class RichFile1(val path: String) {}

object RichFile1 {
    def apply(path: String): RichFile1 = {
        new RichFile1(path)
    }

    def unapply(richFile1: RichFile1) = {
        if (richFile1.path == null) {
            None
        } else {
            val reg = "([/\\w+]+)/(\\w+)\\.(\\w+)".r
            val reg(r1, r2, r3) = richFile1.path
            Some((r1, r2, r3))
        }
    }
}

object Nine {
    def main(args: Array[String]) {
        val richFile1 = RichFile1("/home/cay/readme.txt")
        val RichFile1(r1, r2, r3) = richFile1
        println(r1)
        println(r2)
        println(r3)

    }
}
