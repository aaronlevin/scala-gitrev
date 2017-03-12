// *****************************************************************************
// Projects
// *****************************************************************************

lazy val `scala-gitrev` =
  project
    .in(file("."))
    .enablePlugins(AutomateHeaderPlugin, GitVersioning)
    .settings(settings)
    .settings(
      libraryDependencies ++= Seq(
        "org.typelevel" %% "macro-compat" % "1.1.1",
        "org.scala-lang" % "scala-compiler" % scalaVersion.value % "provided",
        compilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full),
        library.scalaCheck % Test,
        library.scalaTest  % Test
      )
    )

// *****************************************************************************
// Library dependencies
// *****************************************************************************

lazy val library =
  new {
    object Version {
      val scalaCheck = "1.13.4"
      val scalaTest  = "3.0.1"
    }
    val scalaCheck = "org.scalacheck" %% "scalacheck" % Version.scalaCheck
    val scalaTest  = "org.scalatest"  %% "scalatest"  % Version.scalaTest
}

// *****************************************************************************
// Settings
// *****************************************************************************        |

resolvers ++= Seq(
    Resolver.sonatypeRepo("releases"),
    Resolver.sonatypeRepo("public"),
      Resolver.sonatypeRepo("snapshots")
    )

publishTo := {
  val nexus = "https://oss.sonatype.org/"
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")

    /*
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
  */
}

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

publishMavenStyle := true

pomExtra := (
  <url>https://github.com/aaronlevin/scala-gitrev</url>
  <scm>
    <connection>scm:git:git@github.com:aaronlevin/scala-gitrev.git</connection>
    <url>git@github.com:aaronlevin/scala-gitrev.git</url>
  </scm>
  <developers>
    <developer>
      <id>aaronlevin</id>
      <name>Aaron Levin</name>
      <url>aaronlevin.ca</url>
    </developer>
  </developers>
  )

lazy val settings =
  commonSettings ++
  scalafmtSettings ++
  gitSettings ++
  headerSettings

lazy val commonSettings =
  Seq(
    scalaVersion := "2.12.1",
    crossScalaVersions := Seq(scalaVersion.value, "2.11.8", "2.10.6"),
    organization := "ca.aaronlevin",
    version := "0.1.1",
    licenses += ("Apache 2.0",
                 url("http://www.apache.org/licenses/LICENSE-2.0")),
    mappings.in(Compile, packageBin) +=
      baseDirectory.in(ThisBuild).value / "LICENSE" -> "LICENSE",
    scalacOptions ++= Seq(
      "-unchecked",
      "-deprecation",
      "-language:_",
      //"-target:jvm-1.8",
      "-encoding", "UTF-8"
    ),
    javacOptions ++= Seq(
      "-source", "1.8",
      "-target", "1.8"
    ),
    unmanagedSourceDirectories.in(Compile) :=
      Seq(scalaSource.in(Compile).value),
    // include scala_2.x directories for cross-compilation
    unmanagedSourceDirectories.in(Compile) := Seq(
      scalaSource.in(Compile).value,
      sourceDirectory.in(Compile).value / ("scala_" + scalaBinaryVersion.value)
    ),
    unmanagedSourceDirectories.in(Test) :=
      Seq(scalaSource.in(Test).value)
)

lazy val scalafmtSettings =
  reformatOnCompileSettings ++
  Seq(
    formatSbtFiles := false,
    scalafmtConfig :=
      Some(baseDirectory.in(ThisBuild).value / ".scalafmt.conf"),
    ivyScala :=
      ivyScala.value.map(_.copy(overrideScalaVersion = sbtPlugin.value)) // TODO Remove once this workaround no longer needed (https://github.com/sbt/sbt/issues/2786)!
  )

lazy val gitSettings =
  Seq(
    git.useGitDescribe := true
  )

import de.heikoseeberger.sbtheader.HeaderPattern
import de.heikoseeberger.sbtheader.license.Apache2_0
lazy val headerSettings =
  Seq(
    headers := Map("scala" -> Apache2_0("2016", "aaronlevin"))
  )
