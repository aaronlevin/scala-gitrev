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
