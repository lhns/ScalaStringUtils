package de.lolhens.stringutils

import scala.annotation.tailrec
import scala.language.implicitConversions
import scala.util.matching.Regex

object StringUtils {

  implicit class StringOps(val self: String) extends AnyVal {
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
      @tailrec
      def splitRec(string: String, length: Int, parts: List[String] = Nil): List[String] = {
        val sepIndex = string.indexOf(sep)
        if (sepIndex == -1 || length == 0)
          parts :+ string
        else
          splitRec(string.drop(sepIndex + sep.length), if (length == -1) -1 else length - 1, parts :+ string.take(sepIndex))
      }

      splitRec(self, length).padTo(length, "")
    }

    def escapeRegex: String = self.replaceAll("([\\<\\>\\(\\)\\[\\]\\{\\}\\\\\\^\\-\\=\\$\\!\\|\\?\\*\\+\\.])", "\\\\$1")

    def replaceAllLit(s: Seq[String], f: String => String): String =
      s.map(_.escapeRegex).mkString("|").r.replaceAllIn(self, e => f(e.matched))

    def replaceAllRegex(regex: String, f: List[String] => String): String =
      regex.r.replaceAllIn(self, e => Regex.quoteReplacement(f(e.subgroups)))
  }

}
