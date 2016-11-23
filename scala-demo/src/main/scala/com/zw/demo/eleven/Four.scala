package com.zw.demo.eleven

class Money(val dollar: Int, val cent: Int) {
    def +(other: Money): Money = {
        Money(this.dollar + other.dollar, this.cent + other.cent)
    }

    def -(other: Money): Money = {
        Money(0, this.toCent - other.toCent)
    }

    def <(other: Money): Boolean = this.dollar < other.dollar || (this.dollar == other.dollar && this.cent < other.cent)

    def ==(other: Money): Boolean = this.dollar == other.dollar && this.cent == other.cent

    private def toCent = this.dollar * 100 + this.cent

    override def toString = { "dollar = " + this.dollar + " cent = " + this.cent}
}

object Money {
    def apply(dollar: Int, cent: Int) = {
        val d = dollar + cent / 100
        new Money(d, cent % 100)
    }
}

object Four extends App {
    val m1 = Money(1, 200)
    val m2 = Money(2, 2)
    println(m1 + m2)
    println(m1 - m2)
    println(m2 - m1)
    println(m1 == m2)
    println(m1 < m2)
    println(Money(1, 75) + Money(0, 50))
    println(Money(1, 75) + Money(0, 50) == Money(2, 25))
}
