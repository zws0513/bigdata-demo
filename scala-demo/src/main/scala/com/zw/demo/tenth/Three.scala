package com.zw.demo.tenth

/**
  * Created by zhangws on 16/10/28.
  */
object Three {

  def main(args: Array[String]) {
    val log = new CryptoLogger {}

    val plain = "abcdef"
    println("明文为：" + plain)
    println("加密后为：" + log.crypto(plain))
    println("加密后为：" + log.crypto(plain, -3))
  }
}
