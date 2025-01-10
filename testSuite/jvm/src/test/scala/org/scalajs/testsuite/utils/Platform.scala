package org.scalajs.testsuite.utils

object Platform {

  /** Returns `true` if and only if the code is executing on a JVM. Note:
   *  Returns `false` when executing on any JS VM.
   */
  final val executingInJVM = true

  def executingInJVMOnJDK8 = jdkVersion == 8

  def executingInJVMOnHigherThanJDK8 = jdkVersion > 8

  def executingInJVMLessThan(version: Int) = jdkVersion < version

  private lazy val jdkVersion = {
    val v = System.getProperty("java.version")
    if (v.startsWith("1.")) Integer.parseInt(v.drop(2).takeWhile(_.isDigit))
    else Integer.parseInt(v.takeWhile(_.isDigit))
  }
}
