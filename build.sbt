val scala211 = "2.11.12"
val scala212 = "2.12.15"
val scala213 = "2.13.7"
val scala300 = "3.1.0"

val versionsBase   = Seq(scala212, scala211, scala213)
val versionsJVM    = versionsBase :+ scala300
val versionsJS     = versionsJVM
val versionsNative = versionsBase

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
val depSettings = Def.setting {
  CrossVersion.partialVersion(scalaVersion.value) match {
    case Some((3, _))  => Nil
    case Some((2, 11)) => Seq("-target:jvm-1.8")
    case Some((2, 12)) => Seq("-target:jvm-1.8", "-Xsource:3")
    case Some((2, 13)) => Seq("-Xsource:3")
    case _             => Nil
  }
}

val commonSettings: Seq[Setting[_]] = Seq(
  scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature") ++ depSettings.value
)

lazy val root = (project in file("."))
  .settings(
    name := "sjavatime-root",
    crossScalaVersions := Nil,
    doc / aggregate := false,
    doc := (sjavatimeJS / Compile / doc).value,
    packageDoc / aggregate := false,
    packageDoc := (sjavatimeJS / Compile / packageDoc).value
  )
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
    Test / test := {},
    Compile / packageBin / mappings ~= {
      _.filter(!_._2.endsWith(".class"))
    }
  )
  .jsSettings(
    crossScalaVersions := versionsJS
  )
  .nativeSettings(
    Compile / run := {},
    crossScalaVersions := versionsNative,
    logLevel := Level.Info // Info or Debug
  )

lazy val sjavatimeJS     = sjavatime.js
lazy val sjavatimeNative = sjavatime.native

lazy val testSuite = crossProject(JSPlatform, JVMPlatform, NativePlatform)
  .settings(commonSettings: _*)
  .settings(skipPublish: _*)
  .settings(
    testOptions += Tests.Argument(TestFrameworks.JUnit, "-a", "-s", "-v")
  )
  .jvmSettings(
    name := "java.time testSuite on JVM",
    crossScalaVersions := versionsJVM,
    libraryDependencies +=
      "com.github.sbt" % "junit-interface" % "0.13.3" % Test
  )
  .jsSettings(
    name := "java.time testSuite on JS",
    crossScalaVersions := versionsJS,
    scalaJSLinkerConfig ~= (_.withModuleKind(ModuleKind.CommonJSModule))
  )
  .jsConfigure(_.enablePlugins(ScalaJSJUnitPlugin))
  .jsConfigure(_.dependsOn(sjavatimeJS))
  .nativeSettings(
    name := "java.time testSuite on Native",
    crossScalaVersions := versionsNative,
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
