package com.zw.demo.fourteen

/**
  * Created by zhangws on 16/11/28.
  */
object Seven extends App {

    sealed abstract class BinaryTree

    case class Leaf(value: Int) extends BinaryTree

    case class Node(tr: BinaryTree*) extends BinaryTree

    def leafSum(tree: BinaryTree): Int = {
        tree match {
            case Node(r@_*) => r.map(leafSum).sum
            case Leaf(v) => v
        }
    }

    val r = Node(Node(Leaf(3), Leaf(8)), Leaf(2), Node(Leaf(5)))
    println(leafSum(r))
}
