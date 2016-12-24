/*
 * Copyright (c) 2016 Aaron Levin
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
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
      runGit(List("rev-parse", "--short", "HEAD")).getOrElse("UNKNOWN")
    c.Expr(Literal(Constant(response)))
  }

  def gitHash_impl(c: Context): c.Expr[String] = {
    import c.universe._
    import sys.process._

    val response = runGit(List("rev-parse", "HEAD")).getOrElse("UNKNOWN")
    c.Expr(Literal(Constant(response)))
  }

}
