package com.zw.demo.tenth

/**
  * Created by zhangws on 16/10/28.
  */

trait Fly{
  def fly(){
    println("flying")
  }

  def flywithnowing()
}

trait Walk{
  def walk(){
    println("walk")
  }
}

class Bird{
  var name:String = _
}

class BlueBird extends Bird with Fly with Walk{
  def flywithnowing() {
    println("BlueBird flywithnowing")
  }
}

object Seven {

  def main(args: Array[String]) {
    val b = new BlueBird()
    b.walk()
    b.flywithnowing()
    b.fly()
  }
}
