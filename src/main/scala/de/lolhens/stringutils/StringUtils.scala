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

    def replaceAllLit(s: String, replacement: String): String = {
      @tailrec
      def rec(self: String, stringBuilder: StringBuilder): Unit = {
        val index = self.indexOf(s)
        if (index >= 0) {
          stringBuilder ++= self.take(index)
          stringBuilder ++= replacement
          rec(self.drop(index + s.length), stringBuilder)
        } else
          stringBuilder ++= self
      }

      val stringBuilder = StringBuilder.newBuilder
      rec(self, stringBuilder)
      stringBuilder.toString
    }

    def replaceAllLit(strings: Seq[String])(f: String => String): String = {
      @tailrec
      def rec(self: String, stringBuilder: StringBuilder): Unit = {
        val matches = strings.map(s => s -> self.indexOf(s)).filter(_._2 >= 0)
        if (matches.nonEmpty) {
          val (s, index) = matches.minBy(_._2)
          stringBuilder ++= self.take(index)
          stringBuilder ++= f(s)
          rec(self.drop(index + s.length), stringBuilder)
        } else
          stringBuilder ++= self
      }

      val stringBuilder = StringBuilder.newBuilder
      rec(self, stringBuilder)
      stringBuilder.toString
    }

    def replaceAllRegex(regex: String)(f: PartialFunction[List[String], String]): String =
      regex.r.replaceAllIn(self, e => Regex.quoteReplacement(f.lift(e.subgroups).getOrElse(e.matched)))
  }

  implicit class RegexContext(sc: StringContext) {
    def r: Regex = {
      val notEscaped = """(?<!\\)(?:\\\\)*"""

      def captureNone(regex: String, captureLast: Boolean = false): String = {
        val captureLastRegex = if (captureLast) s".*(?:$notEscaped)${"(".escapeRegex}(?=[^${"?:".escapeRegex}])" else ""
        regex.replaceAll(s"($notEscaped)${"(".escapeRegex}(?=[^${"?:".escapeRegex}]$captureLastRegex)", "$1(?:")
      }

      val newParts = sc.parts.dropRight(1).map(captureNone(_, captureLast = true)) :+ captureNone(sc.parts.last)

      new Regex(newParts.mkString, sc.parts.tail.map(_ => "x"): _*)
    }
  }

}
