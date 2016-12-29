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

package ca.aaronlevin

import macrocompat.bundle
import scala.language.experimental.macros
import scala.reflect.macros.whitebox

package object gitrev {

  /**
    * A small sealed-trait heirarchy to capture errors while calling out to git
    */
  sealed trait RunGitError
  case class BadExitValue(code: Int) extends RunGitError
  case object UnexpectedGitOutput    extends RunGitError
  case object RuntimeFailure         extends RunGitError

  @bundle // macro-compat addition
  class GitRevMacro(val c: whitebox.Context) {
    import c.universe._
    import ca.aaronlevin.gitrev.RunGit.runGit

    def gitHash_impl: Tree = {
      val response = runGit(List("rev-parse", "HEAD")) match {
        case Right(r) => r
        case Left(_)  => "UNKNOWN"
      }
      q""" $response """
    }

    def gitHashShort_impl: Tree = {
      val response =
        runGit(List("rev-parse", "--short", "HEAD")) match {
          case Right(r) => r
          case Left(_)  => "UNKNOWN"
        }
      q""" $response """
    }

  }

  /**
    * a Macro to produce the shortened git hash
    */
  def gitHashShort: String = macro GitRevMacro.gitHashShort_impl

  /**
    * a Macro to produce the full git hash
    */
  def gitHash: String = macro GitRevMacro.gitHash_impl

}
