# sjavatime

![CI](https://github.com/ekrich/sjavatime/workflows/CI/badge.svg)
[![Scala.js](https://www.scala-js.org/assets/badges/scalajs-1.0.0.svg)](https://www.scala-js.org)

`sjavatime` is a BSD-licensed reimplementation of the `java.time` API
included in JDK8 for Scala.js and Scala Native projects. This project was forked from
[scala-js-java-time](https://github.com/scala-js/scala-js-java-time)
to allow for an alternative implementation to support
[Scala Native](https://scala-native.readthedocs.io/).

## Alternative choices

This project is incomplete so consider using one of the following alternatives instead:

* [scala-java-time](https://github.com/cquiroz/scala-java-time) (recommended): like this project, but better
* [scalajs-jsjoda](https://github.com/zoepepper/scalajs-jsjoda): implementation of `java.time` on top of the JavaScript library [js-joda](https://github.com/js-joda/js-joda)

## Usage
[![Maven Central](https://img.shields.io/maven-central/v/org.ekrich/sjavatime_2.11.svg)](https://maven-badges.herokuapp.com/maven-central/org.ekrich/sjavatime_2.11)

Simply add the following line to your `sbt` settings:

```scala
libraryDependencies += "org.ekrich" %%% "sjavatime" % "x.y.z"
```

To use in `sbt`, replace `x.y.z` with the version from Maven Central badge above.
All available versions can be seen at the [Maven Repository](https://mvnrepository.com/artifact/org.ekrich/sjavatime).

If you have a `crossProject`, the setting must be used only in the JS and/or Native part:

```scala
lazy val myCross = crossProject
  ...
  .jsSettings(
    libraryDependencies += "org.ekrich" %%% "sjavatime" % "1.0.0"
  )
  .nativeSettings(
    libraryDependencies += "org.ekrich" %%% "sjavatime" % "1.0.0"
  )
```

**Requirement**: you must use a host JDK8 to *build* your project, i.e., to
launch sbt. `sjavatime` does not work on earlier JDKs.

## Work in Progress / linking errors

This library is a work in progress.
There are still many classes and methods that have not been implemented yet.
If you use any of those, you will get linking errors.

Feel free to [contribute](./CONTRIBUTING.md) to extend the set of supported
classes and methods!

## License

`sjavatime` is distributed under the
[BSD 3-Clause license](./LICENSE.txt).

## Contributing

Follow the [contributing guide](./CONTRIBUTING.md).
