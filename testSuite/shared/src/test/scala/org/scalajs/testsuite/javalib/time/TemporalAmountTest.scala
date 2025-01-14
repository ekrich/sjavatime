package org.scalajs.testsuite.javalib.time

import java.time.temporal.{
  UnsupportedTemporalTypeException, ChronoUnit, TemporalAmount
}

import org.junit.Test
import org.junit.Assert.{assertEquals, assertArrayEquals}
import org.scalajs.testsuite.utils.AssertThrows.assertThrows

abstract class TemporalAmountTest[TempAmt <: TemporalAmount] {
  val samples: Seq[TempAmt]

  val units: Seq[ChronoUnit]

  @Test def test_get_unsupported_unit(): Unit = {
    val illegalUnits = ChronoUnit.values.filterNot(units.contains)
    for {
      amount <- samples
      unit <- illegalUnits
    } {
      assertThrows(classOf[UnsupportedTemporalTypeException], amount.get(unit))
    }
  }

  @Test def test_getUnits(): Unit = {
    for (amount <- samples)
      assertArrayEquals(units.toArray[AnyRef], amount.getUnits.toArray())
  }
}
