// versions
val crossVer           = "1.0.0"
val scalaJSVersion     = "1.5.1"
val scalaNativeVersion = "0.4.0"

// Scala.js support
addSbtPlugin("org.scala-js"       % "sbt-scalajs"              % scalaJSVersion)
addSbtPlugin("org.portable-scala" % "sbt-scalajs-crossproject" % crossVer)

// Scala Native support
addSbtPlugin("org.scala-native"   % "sbt-scala-native"              % scalaNativeVersion)
addSbtPlugin("org.portable-scala" % "sbt-scala-native-crossproject" % crossVer)

// Dotty - Scala 3
addSbtPlugin("ch.epfl.lamp" % "sbt-dotty" % "0.5.4")

// includes sbt-dynver sbt-pgp sbt-sonatype sbt-git
addSbtPlugin("com.geirsson" % "sbt-ci-release" % "1.5.7")
