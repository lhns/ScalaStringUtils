package de.lolhens.stringutils

import de.lolhens.stringutils.StringUtils._

object Test {
  def main(args: Array[String]): Unit = {
    val string = "abcd"

    assert(string.padRight(2, "foo") == "abcd")
    assert(string.padRight(5, "foo") == "abcdf")
    assert(string.padRight(12, "foo") == "abcdfoofoofo")
    assert(string.padLeft(2, "foo") == "abcd")
    assert(string.padLeft(5, "foo") == "fabcd")
    assert(string.padLeft(12, "foo") == "foofoofoabcd")

    assert("abcdabcdabcd".splitLit("bc", 8) == List("a", "da", "da", "d", "", "", "", ""))
    assert("abcdabcdabcd".splitLit("abcd") == List("", "", "", ""))
    assert("abcdabcdabcd".splitLit("bc", 2) == List("a", "da", "dabcd"))
  }
}
