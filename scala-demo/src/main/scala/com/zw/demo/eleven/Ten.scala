package com.zw.demo.eleven

class RichFile2(val path: String) {}

object RichFile2 {
    def apply(path: String): RichFile2 = {
        new RichFile2(path)
    }

    def unapplySeq(richFile2: RichFile2): Option[Seq[String]] = {
        if (richFile2.path == null) {
            None
        } else {
            if (richFile2.path.startsWith("/")) {
                Some(richFile2.path.substring(1).split("/"))
            } else {
                Some(richFile2.path.split("/"))
            }
        }
    }
}

object Ten {
    def main(args: Array[String]) {
        val richFile2 = RichFile2("/home/cay/readme.txt")

        val RichFile2(r@_*) = richFile2
        println(r)
    }
}
