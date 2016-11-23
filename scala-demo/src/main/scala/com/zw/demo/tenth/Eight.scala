package com.zw.demo.tenth

import java.io.{FileInputStream, InputStream}

trait Logger {
    def log(msg: String)
}

trait PrintLogger extends Logger {
    def log(msg: String) = println(msg)
}

trait Buffering {
    this: InputStream with Logger =>

    val BUF_SIZE: Int = 5
    val buf: Array[Byte] = new Array[Byte](BUF_SIZE)
    var bufsize: Int = 0 // 缓存数据大小
    var pos: Int = 0 // 当前位置

    override def read(): Int = {
        if (pos >= bufsize) { // 读取数据
            bufsize = this.read(buf, 0, BUF_SIZE)
            if (bufsize <= 0) return bufsize
            log("buffered %d bytes: %s".format(bufsize, buf.mkString(", ")))
            pos = 0
        }
        pos += 1 // 移位
        buf(pos - 1) // 返回数据
    }
}

object Eight {

    def main(args: Array[String]) {
        val f = new FileInputStream("myapp.log") with Buffering with PrintLogger
        for (i <- 1 to 20) println(f.read())
    }
}
