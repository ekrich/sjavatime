# sjavatime

![CI](https://github.com/ekrich/sjavatime/workflows/CI/badge.svg)
[![Scala.js](https://www.scala-js.org/assets/badges/scalajs-1.0.0.svg)](https://www.scala-js.org)

`sjavatime` is a BSD-licensed reimplementation of the `java.time` API
included in JDK8 for Scala.js and Scala Native projects. This project was forked from
[scala-js-java-time](https://github.com/scala-js/scala-js-java-time) which is deprecated.

Now the library supports both [Scala.js](https://www.scala-js.org/) and [Scala Native](https://scala-native.readthedocs.io/).

The library is an incomplete implementation of Java's `java.time` but could be used for minimal API support.
Please refer to the
[Javadoc](https://docs.oracle.com/javase/8/docs/api/java/time/package-summary.html) for API info.


## Other choices
This project is ready today and cross compiled for all current Scala versions on Scala.js and Scala Native. This project was initial started because no alternative existed to support `sconfig`. `sconfig` uses `"provided"` so you are not locked into a `java.time` provider.

The following projects could be considered as alternatives, the second one for Scala.js only:

* [scala-java-time](https://github.com/cquiroz/scala-java-time) (recommended for production)
* [scalajs-jsjoda](https://github.com/zoepepper/scalajs-jsjoda): implementation of `java.time` on top of the JavaScript library [js-joda](https://github.com/js-joda/js-joda)

## Cross Build Versions

| Scala Version          | Scala.js (1.x)        | Native (0.5.0-RC1) |
| ---------------------- | :-------------------: | :----------------: |
| 2.12.x                 |          ✅           |         ✅         |
| 2.13.x                 |          ✅           |         ✅         |
| 3.x.x                  |          ✅           |         ✅         |

Note: Scala 3 support on Scala Native `0.4.3-RC1` or greater. Scala Native `0.4.0` support up to version `1.1.9`.

## Usage
[![Maven Central](https://img.shields.io/maven-central/v/org.ekrich/sjavatime_native0.4_2.13.svg)](https://maven-badges.herokuapp.com/maven-central/org.ekrich/sjavatime_native0.4_2.13)

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
    libraryDependencies += "org.ekrich" %%% "sjavatime" % "x.y.z"
  )
  .nativeSettings(
    libraryDependencies += "org.ekrich" %%% "sjavatime" % "x.y.z"
  )
```

**Requirement**: You must use a host JDK 8, 11 or 17 to *build* your project, i.e., to
launch sbt. `sjavatime` does not work on earlier JDKs.

## Linking errors

This library is not a complete implementation of `java.time` so if you use missing classes and methods you will get linking errors.

Feel free to [contribute](./CONTRIBUTING.md) to extend the set of supported
classes and methods!

## License

`sjavatime` is distributed under the
[BSD 3-Clause license](./LICENSE.txt).

## Contributing

Follow the [contributing guide](./CONTRIBUTING.md).

## Versions

Release [1.2.0](https://github.com/ekrich/sjavatime/releases/tag/v1.2.0) - (2024-02-28)<br/>
Release [1.1.9](https://github.com/ekrich/sjavatime/releases/tag/v1.1.9) - (2022-01-25)<br/>
Release [1.1.8](https://github.com/ekrich/sjavatime/releases/tag/v1.1.8) - (2022-01-11)<br/>
Release [1.1.7](https://github.com/ekrich/sjavatime/releases/tag/v1.1.7) - (2022-01-04)<br/>
Release [1.1.6](https://github.com/ekrich/sjavatime/releases/tag/v1.1.6) - (2021-12-30)<br/>
Release [1.1.5](https://github.com/ekrich/sjavatime/releases/tag/v1.1.5) - (2021-05-13)<br/>
Release [1.1.4](https://github.com/ekrich/sjavatime/releases/tag/v1.1.4) - (2021-05-12)<br/>
Release [1.1.3](https://github.com/ekrich/sjavatime/releases/tag/v1.1.3) - (2021-04-01)<br/>
Release [1.1.2](https://github.com/ekrich/sjavatime/releases/tag/v1.1.2) - (2021-02-23)<br/>
Release [1.1.1](https://github.com/ekrich/sjavatime/releases/tag/v1.1.1) - (2021-02-01)<br/>
Release [1.1.0](https://github.com/ekrich/sjavatime/releases/tag/v1.1.0) - (2021-01-26)<br/>
