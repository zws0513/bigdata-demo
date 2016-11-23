package com.zw.demo.eleven

class BigSequence(private var value: Long = 0) {

    def update(bit: Int, state: Int) = {
        if (state == 1) value |= (state & 1L) << bit % 64
        else value &= ~(1L << bit % 64)
    }

    def apply(bit: Int): Int = if (((value >> bit % 64) & 1L) > 0) 1 else 0

    override def toString = "%64s".format(value.toBinaryString).replace(" ", "0")
}

object Seven {
    def main(args: Array[String]) {
        val x = new BigSequence()
        x(5) = 1
        x(63) = 1
        x(64) = 1

        println(x(5))
        x(64) = 0
        println(x)
    }
}
