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

package com.soundcloud

import scala.sys.process.Process

package object gitrev {

  import scala.language.experimental.macros
  import scala.reflect.macros.blackbox.Context

  /**
    * a Macro to produce the shortened git hash
    */
  def gitHashShort: String = macro gitHashShort_impl

  /**
    * a Macro to produce the full git hash
    */
  def gitHash: String = macro gitHash_impl

  /**
    * A small sealed-trait heirarchy to capture errors while calling out to git
    */
  sealed trait RunGitError
  case class BadExitValue(code: Int) extends RunGitError
  case object UnexpectedGitOutput    extends RunGitError
  case object RuntimeFailure         extends RunGitError

  /**
    * Small helper to run git commands and process the output
    */
  private def runGit(commands: List[String]): Either[RunGitError, String] = {
    val procDecl = Process(s"git ${commands.mkString(" ")}")
    val output   = procDecl.lineStream
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

  def gitHashShort_impl(c: Context): c.Expr[String] = {
    import c.universe._
    import sys.process._

    val response =
      runGit(List("rev-parse", "--short", "HEAD")) match {
        case Right(r) => r
        case Left(_)  => "UNKNOWN"
      }
    c.Expr(Literal(Constant(response)))
  }

  def gitHash_impl(c: Context): c.Expr[String] = {
    import c.universe._
    import sys.process._

    val response = runGit(List("rev-parse", "HEAD")) match {
      case Right(r) => r
      case Left(_)  => "UNKNOWN"
    }
    c.Expr(Literal(Constant(response)))
  }

}
