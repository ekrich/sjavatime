// versions
val crossVer           = "1.1.0"
val scalaJSVersion     = "1.7.1"
val scalaNativeVersion = "0.4.1"

// Scala.js support
addSbtPlugin("org.scala-js"       % "sbt-scalajs"              % scalaJSVersion)
addSbtPlugin("org.portable-scala" % "sbt-scalajs-crossproject" % crossVer)

// Scala Native support
addSbtPlugin("org.scala-native"   % "sbt-scala-native"              % scalaNativeVersion)
addSbtPlugin("org.portable-scala" % "sbt-scala-native-crossproject" % crossVer)

// includes sbt-dynver sbt-pgp sbt-sonatype sbt-git
addSbtPlugin("com.github.sbt" % "sbt-ci-release" % "1.5.10")

// Workaround until Scala.js 1.8.0 released
// See https://github.com/scala-js/scala-js-js-envs/issues/12
libraryDependencies += "org.scala-js" %% "scalajs-env-nodejs" % "1.2.1"
