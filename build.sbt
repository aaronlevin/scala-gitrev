lazy val `scala-gitrev` =
  project.in(file(".")).enablePlugins(AutomateHeaderPlugin, GitVersioning)

libraryDependencies ++= Vector(
  Library.macros ,
  Library.scalaTest % "test"
)

initialCommands := """|import com.soundcloud.gitrev._
                      |""".stripMargin
