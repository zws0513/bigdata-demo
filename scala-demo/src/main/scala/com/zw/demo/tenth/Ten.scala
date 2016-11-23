package com.zw.demo.tenth

import java.io.{FileInputStream, InputStream}

/**
  * Created by zhangws on 16/10/28.
  */
trait IterableInputStream extends InputStream with Iterable[Byte] {

    class InputStreamIterator(outer: IterableInputStream) extends Iterator[Byte] {
        def hasNext: Boolean = outer.available() > 0

        def next: Byte = outer.read().toByte
    }

    override def iterator: Iterator[Byte] = new InputStreamIterator(this)
}

object Ten extends App {
    val fis = new FileInputStream("myapp.log") with IterableInputStream
    val it = fis.iterator
    while (it.hasNext)
        println(it.next())
    fis.close()
}
