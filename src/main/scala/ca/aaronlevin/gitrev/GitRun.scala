/*
 * Copyright 2016 aaronlevin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ca.aaronlevin.gitrev

import sys.process.{ Process, ProcessLogger }

/**
  * between Scala 2.10 and 2.11 the Process API changed. This small
  * module supports cross-compiling to 2.10
  */
object RunGit {

  /**
    * Small helper to run git commands and process the output
    * @param command the shell command to be run, as a list of strings
    *                (space delimited).
    * @return Either an error or a string containing the output from stdout.
    */
  def runGit(command: List[String]): Either[RunGitError, String] = {
    val procDecl             = Process(s"git ${command.mkString(" ")}")
    var output: List[String] = Nil
    try {
      val process =
        procDecl.run(
          ProcessLogger(o => output = o.split("\n").toList, _ => Unit))
      if (process.exitValue() == 0) {
        output match {
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
