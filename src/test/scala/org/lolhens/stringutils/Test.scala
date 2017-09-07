package org.lolhens.stringutils

import org.lolhens.stringutils.StringUtils._

object Test {
  def main(args: Array[String]): Unit = {
    val string = "abcd"

    assert(string.padRight(2, "foo") == "abcd")
    assert(string.padRight(5, "foo") == "abcdf")
    assert(string.padRight(12, "foo") == "abcdfoofoofo")
    assert(string.padLeft(2, "foo") == "abcd")
    assert(string.padLeft(5, "foo") == "fabcd")
    assert(string.padLeft(12, "foo") == "foofoofoabcd")

    assert(string.splitLit("bc", 4) == List("a", "d", "", ""))
    assert(string.splitLit("abcd") == List("", ""))
  }
}
