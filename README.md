# !! WARNING: THIS IS NOT PUBLIC YET !! #

# scala-gitrev #

Welcome to `scala-gitrev`! Inspired by the [gitrev](https://hackage.haskell.org/package/gitrev) haskell library, **scala-gitrev** provides some simple macros that will capture git information at compile time. This will empower you to bundle compile-time git-info with your artifacts, which can be extremely useful for debugging.

To use `scala-gitrev` in your scala project with SBT, simply add the following line to your `build.sbt` file:

```scala
libraryDependencies += "com.soundcloud" %% "gitrev" % "0.1.0"
```

Example usage:

```scala
object MyCoolProject {

  import com.soundcloud.gitrev.gitHash

  val theGitHash: String = gitHash

  // ... other super cool code ...

  def main(args: Array[String]): Unit = {

    println(s"This artifact was compiled at revision $theGitHash")

    // ... do super cool stuff ...
  }
}
```

## Contribution policy ##

Contributions via GitHub pull requests are gladly accepted from their original
author. Along with any pull requests, please state that the contribution is your
original work and that you license the work to the project under the project's
open source license. Whether or not you state this explicitly, by submitting any
copyrighted material via pull request, email, or other means you agree to
license the material under the project's open source license and warrant that
you have the legal authority to do so.

## License ##

This code is open source software licensed under the
[Apache 2.0](http://www.apache.org/licenses/LICENSE-2.0) license.
