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

package com.soundcloud.gitrev
import org.scalatest._
import scala.sys.process._

class GitRevTest extends FlatSpec with Matchers {

  it should "create the same hash as 'git rev-parse HEAD'" in {
    val hashMacro    = gitHash
    val computedHash = ("git rev-parse HEAD" !!).dropRight(1)

    hashMacro should be(computedHash)
  }

  it should "create the same hash as 'git rev-parse --short HEAD'" in {
    val hashShortMacro    = gitHashShort
    val computedHashShort = ("git rev-parse --short HEAD" !!).dropRight(1)

    hashShortMacro should be(computedHashShort)
  }
}
