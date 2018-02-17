package com.scalamill.signin.api

import scala.collection.mutable.ListBuffer

object Solution extends App {

  println("hello")
  val lines: List[String] = scala.io.Source.stdin.getLines().toList
  val firstLine = lines.head.split(" ")
  val treeNodes = firstLine(0).toInt
  val tasks = firstLine(1).toInt
  val tree = new Tree()


  val nodes = lines.tail.head.split(" ").map(x => tree.root += TreeNode(x.toInt))


  for (i <- 2 to treeNodes) {
    val edge = lines(i).split(" ")
    val f = edge(0).toInt
    val s = edge(1).toInt

    tree.insert(f, s)
  }

  println(tree)

  for (i <- treeNodes to treeNodes + tasks) {
    val edge = lines(i).split(" ")
    val f = edge(0).toInt
    val s = edge(1).toInt


    // first find the node from where the path start, then find seuqence sum greater than k with minimum node
    // tree.find(f,s)


  }

  println(tree.root)
}

class Tree() {

  var root: ListBuffer[TreeNode] = ListBuffer[TreeNode]()

  def findPath(q: Int, sumLeast: Int) = {


    var overAllMax = sumLeast
    var numberOfNodes = Integer.MAX_VALUE
    var paths = ""

    def pathHelper(node: TreeNode, sumAccumulated: Int, nodeVisited: Int, path: String): (Int, Int, String) = {
      if (node.node.isDefined) {
        val sums = for {
          nodeI <- node.node.get
        } yield {
          val (sum, noNodes, pathI) = pathHelper(nodeI, sumAccumulated + node.data, nodeVisited + 1, path + " " + nodeI.data)
          if (sum >= overAllMax && noNodes <= numberOfNodes) {
            overAllMax = sum
            numberOfNodes = noNodes
            paths = pathI
          }
          (sum, noNodes, pathI)
        }
        (overAllMax, numberOfNodes, paths)
      } else (sumAccumulated, nodeVisited, path)

    }

    val k: Option[TreeNode] = root.find(_.data == q)

   val data: (Int, Int, String) =  if (k.isDefined) {
      pathHelper(k.get, 0, 0, "")
    }
    else (0, 0, "")

   println(data)
  }


  def insert(first: Int, second: Int) = {

    val firstNode = root(first - 1)
    val secondNode = root(second - 1)
    firstNode.node.get += secondNode


  }
}

case class TreeNode(val data: Int, var node: Option[ListBuffer[TreeNode]] = Some(ListBuffer[TreeNode]()))