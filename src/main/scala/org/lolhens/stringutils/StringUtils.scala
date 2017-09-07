package org.lolhens.stringutils

import scala.language.implicitConversions

class StringUtils(val self: String) extends AnyVal {
  def padRight(len: Int, elem: String): String =
    if (self.length < len) {
      val padding = len - self.length
      self + (elem * ((padding / elem.length) + 1)).take(padding)
    } else
      self

  def padLeft(len: Int, elem: String): String =
    if (self.length < len) {
      val padding = len - self.length
      (elem * ((padding / elem.length) + 1)).take(padding) + self
    } else
      self

  def splitLit(sep: String, minParts: Int = 0): List[String] = {
    def splitRec(string: String, parts: List[String]): List[String] = {
      val sepIndex = string.indexOf(sep)
      if (sepIndex == -1)
        parts :+ string
      else
        parts :+ string.take(sepIndex) :+ string.drop(sepIndex + sep.length)
    }

    splitRec(self, Nil).padTo(minParts, "")
  }
}

object StringUtils {
  implicit def toStringUtils(string: String): StringUtils = new StringUtils(string)
}
