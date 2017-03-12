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
