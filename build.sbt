val scala211 = "2.11.12"
val scala212 = "2.12.12"
val scala213 = "2.13.4"
val scala300 = "3.0.0-M3"

val versionsBase = Seq(scala212, scala211, scala213)
val versionsNative = versionsBase
val versionsJS = versionsBase // :+ scala300

ThisBuild / scalaVersion := scala213

val commonSettings: Seq[Setting[_]] = Seq(
  version := "1.0.1-SNAPSHOT",
  organization := "org.scala-js",
  scalacOptions ++= Seq("-deprecation", "-feature"), // "-Xfatal-warnings"),
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
  .settings(name := "sjavatime-root")
  .aggregate(
    sjavatimeJS,
    sjavatimeNative,
    testSuiteJVM,
    testSuiteJS,
    testSuiteNative
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
  .jsSettings(
    crossScalaVersions := versionsJS
  )
  .nativeSettings(
    crossScalaVersions := versionsNative,
    nativeLinkStubs := true,
    logLevel := Level.Info // Info or Debug
  )

lazy val sjavatimeJS = sjavatime.js
lazy val sjavatimeNative = sjavatime.native
  .enablePlugins(ScalaNativePlugin)

lazy val testSuite = crossProject(JSPlatform, JVMPlatform, NativePlatform)
  .jsConfigure(_.enablePlugins(ScalaJSJUnitPlugin))
  .settings(commonSettings: _*)
  .settings(
    testOptions += Tests.Argument(TestFrameworks.JUnit, "-a", "-s", "-v"),
    scalacOptions += "-target:jvm-1.8"
  )
  .jsSettings(
    name := "java.time testSuite on JS",
    scalaJSLinkerConfig ~= (_.withModuleKind(ModuleKind.CommonJSModule))
  )
  .jsConfigure(_.dependsOn(sjavatimeJS))
  .jvmSettings(
    name := "java.time testSuite on JVM",
    libraryDependencies +=
      "com.novocode" % "junit-interface" % "0.11" % Test
  )
  .nativeSettings(
    name := "java.time testSuite on Native",
    addCompilerPlugin(
      "org.scala-native" % "junit-plugin" % nativeVersion cross CrossVersion.full
    ),
    libraryDependencies += "org.scala-native" %%% "junit-runtime" % nativeVersion
  )

lazy val testSuiteJS = testSuite.js
lazy val testSuiteNative = testSuite.native
  .dependsOn(sjavatimeNative)
lazy val testSuiteJVM = testSuite.jvm
