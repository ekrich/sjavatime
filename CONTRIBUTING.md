# Contributing guidelines

See the [contributing guidelines of Scala.js core](https://github.com/scala-js/scala-js/blob/master/CONTRIBUTING.md).
The same guidelines apply to this repository.

## Very important notice

`sjavatime` contains a reimplementation of part of the JDK in Scala.

***To contribute to this code, it is strictly forbidden to even look at the source code of the Oracle JDK or OpenJDK!***

This is for license considerations: these JDKs are under a GPL-based license, which is not compatible with our BSD 3-clause license.

The only existing JDK source code that we can look at is the dead Apache Harmony project.

## Test help

Here are some command to help with testing since you may want to test specific platforms. This library support Scala.js and Scala Native but also can run its tests against the JVM.

There is an alias to run the examples in `sbt`. All of the following are run via the `sbt` prompt. In order to run only one version of the test, set `sbt` to the version of Scala needed otherwise the default version of 2.13 will be used.

```sh
sbt:sjavatime-root> ++3.3.6
```

Now when you run the command, that version of Scala will be used. Use the following to run `JVM` tests. Replace `JVM` with `Native` or `JS` to run the tests for that platform.

```sh
sbt:sjavatime-root> testSuiteJVM/test
```

To run all the tests which is used in Continuous Integration (CI) use the following:

```sh
sbt:sjavatime-root> +test
```

To run a specific test please use the following:

```sh
sbt:sjavatime-root> testSuiteJVM/testOnly *LocalDateTest
```
