sonatypeProfileName := "aaronlevin"

// To sync with Maven central, you need to supply the following information:
pomExtra in Global := {
  <url>https://github.com/aaronlevin/scala-gitrev</url>
  <licenses>
    <license>
      <name>Apache 2</name>
      <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
    </license>
  </licenses>
  <scm>
    <connection>scm:git:git@github.com:aaronlevin/scala-gitrev.git</connection>
    <developerConnection>scm:git:git@github.com:aaronlevin/scala-gitrev.git</developerConnection>
    <url>git@github.com:aaronlevin/scala-gitrev.git</url>
  </scm>
  <developers>
    <developer>
      <id>aaronlevin</id>
      <name>Aaron Levin</name>
      <url>aaronlevin.ca</url>
    </developer>
  </developers>
}
