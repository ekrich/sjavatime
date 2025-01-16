package org.scalajs.testsuite.utils

object Platform {

  /** Returns `true` if and only if the code is executing on a JVM. Note:
   *  Returns `false` when executing on Native.
   */
  final val executingInJVM = false

  final val executingInJVMOnJDK8 = false

  final val executingInJVMOnHigherThanJDK8 = false

  def executingInJVMLessThan(version: Int) = false

}
