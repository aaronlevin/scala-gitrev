import sbt._

object Version {
  final val Scala     = "2.10.6"
  final val ScalaTest = "3.0.1"
}

object Library {
  val scalaTest = "org.scalatest" %% "scalatest" % Version.ScalaTest
  val macros = "org.scala-lang" % "scala-reflect" % Version.Scala
}
