val scala211 = "2.11.12"
val scala212 = "2.12.15"
val scala213 = "2.13.6"
val scala300 = "3.0.2"

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

val commonSettings: Seq[Setting[_]] = Seq(
  scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-Xsource:3")
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
    },
    sharedScala2or3Source
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
    testOptions += Tests.Argument(TestFrameworks.JUnit, "-a", "-s", "-v"),
    scalacOptions += "-target:jvm-1.8"
  )
  .jvmSettings(
    name := "java.time testSuite on JVM",
    crossScalaVersions := versionsJVM,
    libraryDependencies +=
      "com.github.sbt" % "junit-interface" % "0.13.2" % Test
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

lazy val sharedScala2or3Source: Seq[Setting[_]] = Def.settings(
  Compile / unmanagedSourceDirectories ++= {
    val projectDir = baseDirectory.value.getParentFile()
    sourceDir(projectDir, scalaVersion.value)
  }
)

// For Scala 2/3 enums
def sourceDir(projectDir: File, scalaVersion: String): Seq[File] = {
  def versionDir(versionDir: String): File =
    projectDir / "shared" / "src" / "main" / versionDir

  CrossVersion.partialVersion(scalaVersion) match {
    case Some((3, _)) => Seq(versionDir("scala-3"))
    case Some((2, _)) => Seq(versionDir("scala-2"))
    case _            => Seq() // unknown version
  }
}

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
