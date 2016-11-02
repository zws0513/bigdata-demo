package com.zw.demo.tenth

import java.io.InputStream

/**
  * Created by zhangws on 16/10/28.
  */
class IterableInputStream extends InputStream with Iterable[Byte]{
  def read(): Int = 0

  def iterator: Iterator[Byte] = {
    null
  }
}

object Ten extends App {

  println("xxx")
}
