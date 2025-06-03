// To test snapshots
resolvers ++= Resolver.sonatypeOssRepos("snapshots")

// versions
val crossVer           = "1.3.2"
val scalaJSVersion     = "1.19.0"
val scalaNativeVersion = "0.5.7"

// Scala.js support
addSbtPlugin("org.scala-js"       % "sbt-scalajs"              % scalaJSVersion)
addSbtPlugin("org.portable-scala" % "sbt-scalajs-crossproject" % crossVer)

// Scala Native support
addSbtPlugin("org.scala-native"   % "sbt-scala-native"              % scalaNativeVersion)
addSbtPlugin("org.portable-scala" % "sbt-scala-native-crossproject" % crossVer)

// includes sbt-dynver sbt-pgp sbt-sonatype sbt-git
addSbtPlugin("com.github.sbt" % "sbt-ci-release" % "1.11.1")

