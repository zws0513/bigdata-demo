package com.zw.demo.six

class Account private (val id: Int, initialBalance: Double) {
    private var balance = initialBalance

    override def toString = "id = " + id + " initialBalance = " + initialBalance
}

object Account {
    private var lastNumber = 0

    def newUniqueNumber() = { lastNumber += 1; lastNumber }

    def apply(initialBalance: Double) =
        new Account(newUniqueNumber(), initialBalance)
}

object Note1 {

    def main(args: Array[String]) {
        val acct = Account(100.0)
        println(acct)
    }
}
