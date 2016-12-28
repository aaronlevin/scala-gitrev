import com.typesafe.sbt.GitPlugin
import com.typesafe.sbt.GitPlugin.autoImport._
import de.heikoseeberger.sbtheader.HeaderPlugin
import de.heikoseeberger.sbtheader.HeaderPlugin.autoImport._
import de.heikoseeberger.sbtheader.HeaderPattern
import de.heikoseeberger.sbtheader.license._
import org.scalafmt.sbt.ScalaFmtPlugin
import org.scalafmt.sbt.ScalaFmtPlugin.autoImport._
import sbt._
import sbt.Keys._
import sbt.plugins.JvmPlugin

object Build extends AutoPlugin {

  override def requires =
    JvmPlugin && HeaderPlugin && GitPlugin && ScalaFmtPlugin

  override def trigger = allRequirements

  override def projectSettings =
    reformatOnCompileSettings ++
    Vector(
      // Compile settings
      scalaVersion := Version.Scala,
      crossScalaVersions := Vector(scalaVersion.value),
      scalacOptions ++= Vector(
        "-unchecked",
        "-deprecation",
        "-language:_",
        "-target:jvm-1.8",
        "-encoding", "UTF-8"
      ),
      unmanagedSourceDirectories.in(Compile) :=
        Vector(scalaSource.in(Compile).value),
      unmanagedSourceDirectories.in(Test) :=
        Vector(scalaSource.in(Test).value),

      // Publish settings
      organization := "com.soundcloud",
      licenses += ("MIT",
                   url("https://opensource.org/licenses/MIT")),
      mappings.in(Compile, packageBin)
        += baseDirectory.in(ThisBuild).value / "LICENSE" -> "LICENSE",

      // scalafmt settings
      formatSbtFiles := false,
      scalafmtConfig := Some(baseDirectory.in(ThisBuild).value / ".scalafmt.conf"),
      ivyScala := ivyScala.value.map(_.copy(overrideScalaVersion = sbtPlugin.value)), // TODO Remove once this workaround no longer needed (https://github.com/sbt/sbt/issues/2786)!

      // Git settings
      git.useGitDescribe := true,

      // Header settings
      headers := Map("scala" -> MIT("2016", "Aaron Levin"))
    )
}
