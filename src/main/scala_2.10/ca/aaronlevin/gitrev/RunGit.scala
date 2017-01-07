package ca.aaronlevin.gitrev

import scala.sys.process.Process

/**
  * between Scala 2.10 and 2.11 the Process API changed. This small
  * module supports cross-compiling to 2.10
  */
object RunGit {

  /**
    * Small helper to run git commands and process the output
    */
  def runGit(commands: List[String]): Either[RunGitError, String] = {
    val procDecl = Process(s"git ${commands.mkString(" ")}")
    val output   = procDecl.lines
    try {
      val process = procDecl.run()
      if (process.exitValue() == 0) {
        output.toList match {
          case out :: Nil => Right(out)
          case _          => Left(BadExitValue(process.exitValue()))
        }
      } else {
        Left(UnexpectedGitOutput)
      }
    } catch {
      case _: java.io.IOException => Left(RuntimeFailure)
    }
  }
}
