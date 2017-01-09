package com.zw.demo.three

import scala.collection.JavaConversions.{asScalaBuffer, bufferAsJavaList}
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object Note1 {

    def main(args: Array[String]) {
        val command = ArrayBuffer("ls", "-al", "/home/cay")
        // Scala到Java的转换
        val pd = new ProcessBuilder(command)

        val cmd: mutable.Buffer[String] = pd.command() // Java到Scala的转换
    }
}
