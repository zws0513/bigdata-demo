package com.zw.demo.eleven

class RichFile(val path: String) {}

object RichFile {
    def apply(path: String): RichFile = {
        new RichFile(path)
    }

//    def unapply(richFile: RichFile) = {
//        if (richFile.path == null) {
//            None
//        } else {
//            val reg = "([/\\w+]+)/(\\w+)\\.(\\w+)".r
//            val reg(r1, r2, r3) = richFile.path
//            Some((r1, r2, r3))
//        }
//    }

    def unapplySeq(richFile:RichFile):Option[Seq[String]]={
        if(richFile.path == null){
            None
        } else {
            if (richFile.path.startsWith("/")) {
                Some(richFile.path.substring(1).split("/"))
            } else {
                Some(richFile.path.split("/"))
            }
        }
    }
}

object NineTen {
    def main(args: Array[String]) {
        val richFile = RichFile("/home/cay/readme.txt")
//        val RichFile(r1, r2, r3) = richFile
//        println(r1)
//        println(r2)
//        println(r3)

        val RichFile(r @ _*) = richFile
        println(r)
    }
}
