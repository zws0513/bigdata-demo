package com.zw.demo.fourteen

/**
  * Created by zhangws on 16/11/28.
  */
object Six extends App {

    sealed abstract class BinaryTree

    case class Leaf(value: Int) extends BinaryTree

    case class Node(left: BinaryTree, right: BinaryTree) extends BinaryTree

    def leafSum(tree: BinaryTree): Int = {
        tree match {
            case Node(a, b) => leafSum(a) + leafSum(b)
            case Leaf(v) => v
        }
    }

    val r = Node(Leaf(3), Node(Leaf(3), Leaf(9)))
    println(leafSum(r))
}
