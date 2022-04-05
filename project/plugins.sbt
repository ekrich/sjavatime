// To test snapshots
resolvers += Resolver.sonatypeRepo("snapshots")

// versions
val crossVer           = "1.2.0"
val scalaJSVersion     = "1.10.0"
val scalaNativeVersion = "0.4.4"

// Scala.js support
addSbtPlugin("org.scala-js"       % "sbt-scalajs"              % scalaJSVersion)
addSbtPlugin("org.portable-scala" % "sbt-scalajs-crossproject" % crossVer)

// Scala Native support
addSbtPlugin("org.scala-native"   % "sbt-scala-native"              % scalaNativeVersion)
addSbtPlugin("org.portable-scala" % "sbt-scala-native-crossproject" % crossVer)

// includes sbt-dynver sbt-pgp sbt-sonatype sbt-git
addSbtPlugin("com.github.sbt" % "sbt-ci-release" % "1.5.10")

