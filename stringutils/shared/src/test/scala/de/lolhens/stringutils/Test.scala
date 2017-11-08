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

    val r"($c($a.*)-($b.*)-(.*))" = "foo-bar-test"
    assert(a == "foo")
    assert(b == "bar")

    assert("asdfsdasdsdjsj".replaceAllLit("sd", "*-") == "asdfsdasdsdjsj".replaceAllLiterally("sd", "*-"))

    assert(("-12367" match {
      case r"(-?([0-9]|%)+)" => c
      case _ => "n"
    }) == "foo-bar-test")

    assert("abchelloworld".replaceAllLit(Seq("hello", "abc")) {
      case "abc" => "123"
      case "hello" => "world"
    } == "123worldworld")
  }
}
