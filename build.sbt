val scala211 = "2.11.12"
val scala212 = "2.12.13"
val scala213 = "2.13.4"
val scala300 = "3.0.0-M3"

val versionsBase   = Seq(scala212, scala211, scala213)
val versionsNative = versionsBase
val versionsJS     = versionsBase // :+ scala300

ThisBuild / scalaVersion := scala213

inThisBuild(
  List(
    description := "A version of java.time library for Scala.js and Scala Native",
    organization := "org.ekrich",
    homepage := Some(url("https://github.com/ekrich/sjavatime")),
    licenses := List(
      "BSD 3-Clause" ->
        url("https://github.com/ekrich/sjavatime/blob/master/LICENSE.txt")),
    developers := List(
      // for this fork
      Developer(id = "ekrich",
                name = "Eric K Richardson",
                email = "ekrichardson@gmail.com",
                url = url("http://github.ekrich.org/")),
      // original developers
      Developer(id = "sjrd",
                name = "Sébastien Doeraene",
                email = "",
                url = url("https://github.com/sjrd/")),
      Developer(id = "gzm0",
                name = "Tobias Schlatter",
                email = "",
                url = url("https://github.com/gzm0/")),
      Developer(id = "nicolasstucki",
                name = "Nicolas Stucki",
                email = "",
                url = url("https://github.com/nicolasstucki/"))
    )
  )
)

val commonSettings: Seq[Setting[_]] = Seq(
  version := "1.1.0-SNAPSHOT",
  scalacOptions ++= Seq("-deprecation", "-feature") // "-Xfatal-warnings"),
)

lazy val root = (project in file("."))
  .settings(name := "sjavatime-root")
  .settings(skipPublish: _*)
  .aggregate(
    sjavatimeJS,
    sjavatimeNative,
    testSuiteJVM,
    testSuiteJS,
    testSuiteNative
  )

lazy val sjavatime = crossProject(JSPlatform, NativePlatform)
  .crossType(CrossType.Full)
  .settings(commonSettings)
  .settings(
    mappings in (Compile, packageBin) ~= {
      _.filter(!_._2.endsWith(".class"))
    }
  )
  .jsSettings(
    crossScalaVersions := versionsJS
  )
  .jsConfigure(_.enablePlugins(ScalaJSPlugin))
  .nativeSettings(
    crossScalaVersions := versionsNative,
    nativeLinkStubs := true,
    logLevel := Level.Info // Info or Debug
  )
  .nativeConfigure(_.enablePlugins(ScalaNativePlugin))

lazy val sjavatimeJS     = sjavatime.js
lazy val sjavatimeNative = sjavatime.native

lazy val testSuite = crossProject(JSPlatform, JVMPlatform, NativePlatform)
  .settings(commonSettings: _*)
  .settings(skipPublish: _*)
  .settings(
    testOptions += Tests.Argument(TestFrameworks.JUnit, "-a", "-s", "-v"),
    scalacOptions += "-target:jvm-1.8"
  )
  .jvmSettings(
    name := "java.time testSuite on JVM",
    libraryDependencies +=
      "com.novocode" % "junit-interface" % "0.11" % Test
  )
  .jsSettings(
    name := "java.time testSuite on JS",
    scalaJSLinkerConfig ~= (_.withModuleKind(ModuleKind.CommonJSModule))
  )
  .jsConfigure(_.enablePlugins(ScalaJSJUnitPlugin))
  .jsConfigure(_.dependsOn(sjavatimeJS))
  .nativeSettings(
    name := "java.time testSuite on Native",
    addCompilerPlugin(
      "org.scala-native" % "junit-plugin" % nativeVersion cross CrossVersion.full
    ),
    libraryDependencies += "org.scala-native" %%% "junit-runtime" % nativeVersion
  )
  .nativeConfigure(_.dependsOn(sjavatimeNative))

lazy val testSuiteJS     = testSuite.js
lazy val testSuiteNative = testSuite.native
lazy val testSuiteJVM    = testSuite.jvm

val skipPublish = Seq(
  // no artifacts in this project
  publishArtifact := false,
  // make-pom has a more specific publishArtifact setting already
  // so needs specific override
  makePom / publishArtifact := false,
  // no docs to publish
  packageDoc / publishArtifact := false,
  publish / skip := true
)
