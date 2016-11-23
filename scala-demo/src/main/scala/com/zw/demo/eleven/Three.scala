package com.zw.demo.eleven

import scala.math.abs

class Fraction(n: Int, d: Int) {
    private val num: Int = if (d == 0) 1 else n * sign(d) / gcd(n, d)
    private val den: Int = if (d == 0) 0 else d * sign(d) / gcd(n, d)

    override def toString = num + "/" + den

    // 正负号
    def sign(a: Int): Int = if (a > 0) 1 else if (a < 0) -1 else 0

    // 最大公约数
    def gcd(a: Int, b: Int): Int = if (b == 0) abs(a) else gcd(b, a % b)

    def +(other: Fraction): Fraction = {
        Fraction((this.num * other.den) + (other.num * this.den), this.den * other.den)
    }

    def -(other: Fraction): Fraction = {
        Fraction((this.num * other.den) - (other.num * this.den), this.den * other.den)
    }

    def *(other: Fraction): Fraction = {
        Fraction(this.num * other.num, this.den * other.den)
    }

    def /(other: Fraction): Fraction = {
        Fraction(this.num * other.den, this.den * other.num)
    }
}

object Fraction {
    def apply(n: Int, d: Int) = new Fraction(n, d)
}

object Three extends App {
    val f = new Fraction(15, -6)
    val p = new Fraction(20, 60)
    println(f)
    println(p)
    println(f + p)
    println(f - p)
    println(f * p)
    println(f / p)
}
