inThisBuild(Seq(
  name := "ScalaStringUtils",
  organization := "de.lolhens",
  version := "0.0.5",

  scalaVersion := "2.12.3",

  bintrayReleaseOnPublish := false
))

name := (name in ThisBuild).value

lazy val root = project.in(file("."))
  .settings(publishArtifact := false)
  .aggregate(
    stringutilsJVM_2_11,
    stringutilsJVM_2_12,
    stringutilsJS_2_11,
    stringutilsJS_2_12
  )

lazy val stringutils = crossProject.crossType(CrossType.Full)
  .settings(name := (name in ThisBuild).value)

lazy val stringutilsJVM_2_11 = stringutils.jvm.cross("2.11.11").settings(name := (name in ThisBuild).value)
lazy val stringutilsJVM_2_12 = stringutils.jvm.cross("2.12.3").settings(name := (name in ThisBuild).value)
lazy val stringutilsJS_2_11 = stringutils.js.cross("2.11.11").settings(name := (name in ThisBuild).value)
lazy val stringutilsJS_2_12 = stringutils.js.cross("2.12.3").settings(name := (name in ThisBuild).value)
