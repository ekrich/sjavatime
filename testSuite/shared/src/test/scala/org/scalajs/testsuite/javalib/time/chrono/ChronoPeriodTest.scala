package org.scalajs.testsuite.javalib.time.chrono

import java.time.LocalDate
import java.time.chrono.ChronoPeriod


class ChronoPeriodTest extends munit.FunSuite {
  test("test_between") {
    val ds = Seq(LocalDate.MIN, LocalDate.of(2011, 2, 28), LocalDate.MAX)
    for {
      d1 <- ds
      d2 <- ds
    } {
      assertEquals(ChronoPeriod.between(d1, d2), d1.until(d2))
    }
  }
}
