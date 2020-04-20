package org.scalajs.testsuite.javalib.time

import java.time.temporal._

import org.scalajs.testsuite.utils.AssertThrows._

abstract class TemporalTest[Temp <: Temporal] extends TemporalAccessorTest[Temp] {
  import DateTimeTestUtil._

  def isSupported(unit: ChronoUnit): Boolean

  val sampleLongs = Seq(
      Long.MinValue, Int.MinValue.toLong, -1000000000L, -86400L,
      -3600L, -366L, -365L, -60L, -24L, -7L, -1L, 0L,
      1L, 7L, 24L, 60L, 365L, 366L, 3600L, 86400L, 1000000000L,
      Int.MaxValue.toLong, Long.MaxValue)

  test("isSupported_TemporalUnit") {
    for {
      temporal <- samples
      unit <- ChronoUnit.values
    } {
      if (isSupported(unit))
        assert(temporal.isSupported(unit))
      else
        assert(!temporal.isSupported(unit))
    }
    for (temporal <- samples)
      assert(!temporal.isSupported(null: TemporalUnit))
  }

  test("with_unsupported_field") {
    for {
      temporal <- samples
      field <- ChronoField.values if !temporal.isSupported(field)
      n <- sampleLongs.filter(field.range.isValidValue)
    } {
      expectThrows(classOf[UnsupportedTemporalTypeException],
          temporal.`with`(field, n))
    }
  }

  test("plus_unsupported_unit") {
    for {
      temporal <- samples
      unit <- ChronoUnit.values if !temporal.isSupported(unit)
      n <- sampleLongs
    } {
      expectThrows(classOf[UnsupportedTemporalTypeException],
          temporal.plus(n, unit))
    }
  }

  test("minus") {
    for {
      temporal <- samples
      unit <- ChronoUnit.values if temporal.isSupported(unit)
      n <- sampleLongs
    } {
      testDateTime(temporal.minus(n, unit)) {
        if (n != Long.MinValue) temporal.plus(-n, unit)
        else temporal.plus(Long.MaxValue, unit).plus(1, unit)
      }
    }
  }

  test("minus_unsupported_unit") {
    for {
      temporal <- samples
      unit <- ChronoUnit.values if !temporal.isSupported(unit)
      n <- sampleLongs
    } {
      expectThrows(classOf[UnsupportedTemporalTypeException],
          temporal.minus(n, unit))
    }
  }

  test("until_unsupported_unit") {
    for {
      temporal1 <- samples
      temporal2 <- samples
      unit <- ChronoUnit.values if !temporal1.isSupported(unit)
    } {
      expectThrows(classOf[UnsupportedTemporalTypeException],
          temporal1.until(temporal2, unit))
    }
  }
}
