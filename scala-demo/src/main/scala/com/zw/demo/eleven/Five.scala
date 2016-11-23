package com.zw.demo.eleven

class Table {
    var s: String = ""

    def |(str: String): Table = {
        Table(this.s + "<td>" + str + "</td>")
    }

    def ||(str: String): Table = {
        Table(this.s + "</tr><tr><td>" + str + "</td>")
    }

    override def toString: String = {
        "<table><tr>" + this.s + "</tr></table>"
    }
}

object Table {
    def apply(): Table = {
        new Table
    }

    def apply(str: String): Table = {
        val t = new Table
        t.s = str
        t
    }
}

object Five extends App {
    println(Table() | "Java" | "Scala" || "Gosling" | "Odersky" || "JVM" | "JVM,.NET")
}
