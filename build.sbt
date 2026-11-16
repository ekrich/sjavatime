val scala212 = "2.12.21"
val scala213 = "2.13.18"
val scala3 = "3.9.0"

val versionsBase = Seq(scala212, scala213)
val versionsJVM = versionsBase :+ scala3
val versionsJS = versionsJVM
val versionsNative = versionsJVM

ThisBuild / scalaVersion := scala213
ThisBuild / versionScheme := Some("early-semver")
ThisBuild / mimaReportSignatureProblems := true

inThisBuild(
    List(
        version := dynverGitDescribeOutput.value.mkVersion(versionFmt, ""),
        dynver := sbtdynver.DynVer
          .getGitDescribeOutput(new java.util.Date)
          .mkVersion(versionFmt, ""),
        description :=
          "A version of java.time library for Scala.js and Scala Native",
        organization := "org.ekrich",
        homepage := Some(uri("https://github.com/ekrich/sjavatime")),
        licenses := List(
            "BSD 3-Clause" ->
              uri("https://github.com/ekrich/sjavatime/blob/master/LICENSE.txt")
        ),
        developers := List(
            // for this fork
            Developer(id = "ekrich",
                name = "Eric K Richardson",
                email = "ekrichardson@gmail.com",
                url = uri("http://github.ekrich.org/")),
            // original developers
            Developer(id = "sjrd",
                name = "Sébastien Doeraene",
                email = "",
                url = uri("https://github.com/sjrd/")),
            Developer(id = "gzm0",
                name = "Tobias Schlatter",
                email = "",
                url = uri("https://github.com/gzm0/")),
            Developer(id = "nicolasstucki",
                name = "Nicolas Stucki",
                email = "",
                url = uri("https://github.com/nicolasstucki/"))
        )
    )
)

// these functions from Scala Native
def patchVersion(prefix: String, scalaVersion: String): Int =
  scalaVersion.stripPrefix(prefix).takeWhile(_.isDigit).toInt

def canUseRelease(scalaVersion: String) = CrossVersion
  .partialVersion(scalaVersion)
  .fold(false) {
    case (2, 13) => patchVersion("2.13.", scalaVersion) > 8
    case (3, _)  => true // since 3.1.2
    case _       => false // unexpected version
  }

def targetJDKVersion(scalaVersion: String): Int =
  CrossVersion.partialVersion(scalaVersion) match {
    case Some((3, minor)) if minor >= 8 => 17
    case _                              => 8
  }

// Target version as a string, for -target flags - jdk 8 compatible
def targetJDKVersionString(jdkVersion: Int): String =
  jdkVersion match {
    case 8       => "1.8"
    case version => version.toString
  }

// stable snapshot is not great for publish local
def versionFmt(out: sbtdynver.GitDescribeOutput): String = {
  val tag = out.ref.dropPrefix
  if (out.isCleanAfterTag) tag
  else s"$tag-${out.commitSuffix.distance}-${out.commitSuffix.sha}-SNAPSHOT"
}

val depSettings = Def.setting {
  val jdkVersion = targetJDKVersion(scalaVersion.value)
  val targetOpt =
    if (canUseRelease(scalaVersion.value)) Seq(s"-release:$jdkVersion")
    else Seq(s"-target:jvm-${targetJDKVersionString(jdkVersion)}")
  val sourceOpt = CrossVersion.partialVersion(scalaVersion.value) match {
    case Some((2, _)) => Seq("-Xsource:3")
    case _            => Nil
  }
  targetOpt ++ sourceOpt
}

lazy val disabledDocsSettings = Def.settings(
    Compile / doc / sources := Nil
)

val commonSettings: Seq[Setting[?]] = Seq(
    scalacOptions ++= Seq("-unchecked", "-deprecation",
        "-feature") ++ depSettings.value
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
  .settings(skipPublish*)
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
  .settings(disabledDocsSettings)
  .settings(
      Test / sources := Nil,
      Compile / packageBin / mappings ~= {
        _.filter(t => !t._2.endsWith(".class") || !t._2.endsWith(".tasty"))
      }
  )
  .settings(
      // mima settings
      mimaFailOnNoPrevious := false,
      mimaPreviousArtifacts := {
        sbtdynver.DynVer
          .getGitDescribeOutput(new java.util.Date)
          .map { out =>
            organization.value %% moduleName.value % out.ref.dropPrefix
          }
          .toSet
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

lazy val sjavatimeJS = sjavatime.js
lazy val sjavatimeNative = sjavatime.native

lazy val testSuite = crossProject(JSPlatform, JVMPlatform, NativePlatform)
  .settings(commonSettings)
  .settings(skipPublish)
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
          ("org.scala-native" % "junit-plugin" % nativeVersion)
            .cross(CrossVersion.full)
      ),
      libraryDependencies +=
        "org.scala-native" %% "junit-runtime" % nativeVersion
  )
  .nativeConfigure(_.dependsOn(sjavatimeNative))

lazy val testSuiteJS = testSuite.js
lazy val testSuiteNative = testSuite.native
lazy val testSuiteJVM = testSuite.jvm

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
