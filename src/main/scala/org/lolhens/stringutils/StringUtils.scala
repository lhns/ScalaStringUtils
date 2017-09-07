package org.lolhens.stringutils

import scala.language.implicitConversions

class StringUtils(val self: String) extends AnyVal {
  def padRight(len: Int, elem: String = " "): String =
    if (self.length < len) {
      val padding = len - self.length
      self + (elem * ((padding / elem.length) + 1)).take(padding)
    } else
      self

  def padLeft(len: Int, elem: String = " "): String =
    if (self.length < len) {
      val padding = len - self.length
      (elem * ((padding / elem.length) + 1)).take(padding) + self
    } else
      self

  def splitLit(sep: String, length: Int = -1): List[String] = {
    def splitRec(string: String, length: Int, parts: List[String] = Nil): List[String] = {
      val sepIndex = string.indexOf(sep)
      if (sepIndex == -1 || length == 0)
        parts :+ string
      else
        splitRec(string.drop(sepIndex + sep.length), if (length == -1) -1 else length - 1, parts :+ string.take(sepIndex))
    }

    splitRec(self, length).padTo(length, "")
  }
}

object StringUtils {
  implicit def toStringUtils(string: String): StringUtils = new StringUtils(string)
}
