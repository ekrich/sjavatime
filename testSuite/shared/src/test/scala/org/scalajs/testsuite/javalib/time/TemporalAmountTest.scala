package org.scalajs.testsuite.javalib.time

import java.time.temporal.{UnsupportedTemporalTypeException, ChronoUnit, TemporalAmount}

import org.scalajs.testsuite.utils.AssertThrows._

abstract class TemporalAmountTest extends munit.FunSuite {
  val samples: Seq[TemporalAmount]

  val units: Seq[ChronoUnit]

  test("test_get_unsupported_unit") {
    val illegalUnits = ChronoUnit.values.filterNot(units.contains)
    for {
      amount <- samples
      unit <- illegalUnits
    } {
      expectThrows(classOf[UnsupportedTemporalTypeException], amount.get(unit))
    }
  }

  test("test_getUnits") {
    for (amount <- samples)
      assertEquals(units.toArray[AnyRef], amount.getUnits.toArray())
  }
}
