package org.scalajs.testsuite.javalib.time.chrono

import java.time.{DateTimeException, LocalTime, LocalDate}
import java.time.chrono.ChronoLocalDate

import org.scalajs.testsuite.utils.AssertThrows._

class ChronoLocalDateTest extends munit.FunSuite {
  import ChronoLocalDate._

  test("test_timeLineOrder") {
    val ord = timeLineOrder
    val ds = Seq(LocalDate.MIN, LocalDate.of(2011, 2, 28), LocalDate.MAX)

    for {
      d1 <- ds
      d2 <- ds
    } {
      assertEquals(math.signum(d1.compareTo(d2)),
          math.signum(ord.compare(d1, d2)))
    }
  }

  test("test_from") {
    for (d <- Seq(LocalDate.MIN, LocalDate.of(2011, 2, 28), LocalDate.MAX))
      assertEquals(from(d), d)

    for (t <- Seq(LocalTime.MIN, LocalTime.NOON, LocalTime.MAX))
      expectThrows(classOf[DateTimeException], from(t))
  }
}
