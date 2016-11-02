package com.zw.demo.tenth

/**
  * Created by zhangws on 16/10/28.
  */
trait CryptoLogger {
  def crypto(str : String, key : Int = 3) : String = {
    for ( i <- str) yield
      if (key >= 0) (97 + ((i - 97 + key)%26)).toChar
      else (97 + ((i - 97 + 26 + key)%26)).toChar
  }
}
