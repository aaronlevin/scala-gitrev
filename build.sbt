lazy val `scala-gitrev` =
  project.in(file(".")).enablePlugins(AutomateHeaderPlugin, GitVersioning)

libraryDependencies ++= Vector(
  Library.scalaTest % "test"
)

initialCommands := """|import com.soundcloud.scala.gitrev._
                      |""".stripMargin
