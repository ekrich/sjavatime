package org.scalajs.testsuite.utils

import org.junit.Assert
import org.junit.function.ThrowingRunnable

object AssertThrows {
  def assertThrows[T <: Throwable, U](
      expectedThrowable: Class[T],
      code: => U
  ): T = {
    Assert.assertThrows(
        expectedThrowable,
        new ThrowingRunnable {
          def run(): Unit = code
        }
    )
  }
}
