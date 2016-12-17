import sbt._

object Version {
  final val Scala     = "2.12.0"
  final val ScalaTest = "3.0.1"
}

object Library {
  val scalaTest = "org.scalatest" %% "scalatest" % Version.ScalaTest
}
