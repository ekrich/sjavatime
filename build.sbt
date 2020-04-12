import sbtcrossproject.crossProject

val scala211 = "2.11.12"
val scala212 = "2.12.11"
val scala213 = "2.13.1"

val versionsBase   = Seq(scala212, scala211, scala213)
val versionsJS     = versionsBase
val versionsNative = Seq(scala211)
crossScalaVersions in ThisBuild := versionsBase

scalaVersion in ThisBuild := (crossScalaVersions in ThisBuild).value.head

val commonSettings: Seq[Setting[_]] = Seq(
  version := "1.0.1-SNAPSHOT",
  organization := "org.scala-js",
  scalacOptions ++= Seq("-deprecation", "-feature", "-Xfatal-warnings"),
  homepage := Some(url("http://scala-js.org/")),
  licenses += ("BSD New",
  url("https://github.com/scala-js/scala-js-java-time/blob/master/LICENSE")),
  scmInfo := Some(
    ScmInfo(
      url("https://github.com/scala-js/scala-js-java-time"),
      "scm:git:git@github.com:scala-js/scala-js-java-time.git",
      Some("scm:git:git@github.com:scala-js/scala-js-java-time.git")
    )
  )
)
lazy val root = (project in file("."))
  .aggregate(
    sjavatimeJS,
    sjavatimeNative,
    testSuiteJVM,
    testSuiteJS
  )

lazy val sjavatime = crossProject(JSPlatform, NativePlatform)
  .crossType(CrossType.Full)
  .enablePlugins(ScalaJSPlugin)
  .settings(commonSettings)
  .settings(
    mappings in (Compile, packageBin) ~= {
      _.filter(!_._2.endsWith(".class"))
    },
    exportJars := true,
    publishMavenStyle := true,
    publishTo := {
      val nexus = "https://oss.sonatype.org/"
      if (isSnapshot.value)
        Some("snapshots" at nexus + "content/repositories/snapshots")
      else
        Some("releases" at nexus + "service/local/staging/deploy/maven2")
    },
    pomExtra := (
      <developers>
          <developer>
            <id>sjrd</id>
            <name>Sébastien Doeraene</name>
            <url>https://github.com/sjrd/</url>
          </developer>
          <developer>
            <id>gzm0</id>
            <name>Tobias Schlatter</name>
            <url>https://github.com/gzm0/</url>
          </developer>
          <developer>
            <id>nicolasstucki</id>
            <name>Nicolas Stucki</name>
            <url>https://github.com/nicolasstucki/</url>
          </developer>
        </developers>
    ),
    pomIncludeRepository := { _ => false }
  )
  .nativeSettings(
    crossScalaVersions := versionsNative,
    scalaVersion := scala211, // allows to compile if scalaVersion set not 2.11
    nativeLinkStubs := true,
    logLevel := Level.Info, // Info or Debug
    //libraryDependencies += "com.github.lolgab" %%% "minitest" % "2.5.0-5f3852e" % Test,
    //testFrameworks += new TestFramework("minitest.runner.Framework")
  )

lazy val sjavatimeJS = sjavatime.js
lazy val sjavatimeNative = sjavatime.native

lazy val testSuite = crossProject(JSPlatform, JVMPlatform)
  .jsConfigure(_.enablePlugins(ScalaJSJUnitPlugin))
  .settings(commonSettings: _*)
  .settings(
    testOptions +=
      Tests.Argument(
        TestFramework("com.novocode.junit.JUnitFramework"),
        "-v",
        "-a"
      ),
    scalacOptions += "-target:jvm-1.8"
  )
  .jsSettings(
    name := "java.time testSuite on JS"
  )
  .jsConfigure(_.dependsOn(sjavatime.js))
  .jvmSettings(
    name := "java.time testSuite on JVM",
    libraryDependencies +=
      "com.novocode" % "junit-interface" % "0.9" % "test"
  )

lazy val testSuiteJS = testSuite.js
lazy val testSuiteJVM = testSuite.jvm
