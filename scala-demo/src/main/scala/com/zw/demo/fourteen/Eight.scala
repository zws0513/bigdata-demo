package com.zw.demo.fourteen

/**
  * Created by zhangws on 16/11/28.
  */
object Eight extends App {

    sealed abstract class BinaryTree

    case class Leaf(value: Int) extends BinaryTree

    case class Node(op: Char, leafs: BinaryTree*) extends BinaryTree

    def eval(tree: BinaryTree): Int = {
        tree match {
            case Node(op, leafs@_*) => op match {
                case '+' => leafs.map(eval).sum
                case '-' => -leafs.map(eval).sum
                case '*' => leafs.map(eval).product
            }
            case Leaf(v) => v
        }
    }

    val x = Node('+', Node('*', Leaf(3), Leaf(8)), Leaf(2), Node('-', Leaf(5)))
    println(x)
    println(eval(x))
}
