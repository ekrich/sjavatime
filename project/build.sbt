// versions
val crossVer = "0.6.1"
val scalaJSVersion =
  Option(System.getenv("SCALAJS_VERSION")).getOrElse("1.0.0")
val scalaNativeVersion = "0.4.0-M2"

// Scala.js support
addSbtPlugin("org.scala-js" % "sbt-scalajs" % scalaJSVersion)
addSbtPlugin("org.portable-scala" % "sbt-scalajs-crossproject" % crossVer)

// Scala Native support
addSbtPlugin("org.scala-native" % "sbt-scala-native" % scalaNativeVersion)
addSbtPlugin("org.portable-scala" % "sbt-scala-native-crossproject" % crossVer)

addSbtPlugin("org.scalastyle" % "scalastyle-sbt-plugin" % "1.0.0")
