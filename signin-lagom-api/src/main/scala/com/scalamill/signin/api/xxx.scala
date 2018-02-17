package dp

object LIS extends App {

  val array  = scala.io.Source.stdin.getLines().toArray

  val arraySize = array(0).split(" ")(0)
  val increasingSeq = array(0).split(" ")(1)
  val actualArray: Array[String] = array(1).split(" ")



  val arr = Array(0, 8, 4, 12, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11, 7, 15)

  val arr2 = Array(2, 6, 3, 4, 1, 2, 9, 5, 8)

  val arr3 = Array(15, 27, 14, 38, 26, 55, 46, 65, 85)

  println(arr.length)

  println(lis_wrapper(arr))
  println(lis_wrapper(arr2))
  println(lis_wrapper(arr3))
  println(lisDp(arr))
  println(lisDp(arr2))
  println(lisDp(arr3))

  //start with the length of the array, then solve for sublength

  def lis_wrapper(arr: Array[Int]) = {

    var overAllMax = Integer.MIN_VALUE

    def lis(high: Int): Int = {

      //if array length is one
      var current_length_of_major = 1

      if (high == 1) return current_length_of_major

      for (i <- 1 to high - 1) {

        val current_sub_problem_length = lis(i)

        if (arr(i - 1) <= arr(high - 1) &&
          current_length_of_major < current_sub_problem_length + 1)
          current_length_of_major = current_sub_problem_length + 1

      }

      if (overAllMax < current_length_of_major) overAllMax = current_length_of_major
      current_length_of_major
    }

    lis(arr.length)
    overAllMax

  }

  def lisDp(arr: Array[Int]) = {

    val lisTable = Array.ofDim[Int](arr.length)

    for (i <- 0 to arr.length - 1) lisTable(i) = 1

    for (i <- 1 to arr.length - 1) {

      for (j <- 0 until i) {

        if (arr(j) < arr(i) && lisTable(i) < lisTable(j) + 1) {

          lisTable(i) = lisTable(j) + 1

        }

      }

    }

    lisTable.max
  }

  def printActualSequence = ???

}
