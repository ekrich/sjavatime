package org.scalajs.testsuite.javalib.time

import java.time.DateTimeException
import java.time.temporal._

import org.junit.Test
import org.junit.Assert.assertEquals
import org.scalajs.testsuite.utils.AssertThrows.assertThrows

abstract class TemporalAccessorTest[TempAcc <: TemporalAccessor] {
  val samples: Seq[TempAcc]

  def isSupported(field: ChronoField): Boolean

  @Test def isSupported_TemporalField(): Unit = {
    for {
      accessor <- samples
      field <- ChronoField.values
    } {
      if (isSupported(field))
        assert(accessor.isSupported(field))
      else
        assert(!accessor.isSupported(field))
    }
    for (accessor <- samples)
      assert(!accessor.isSupported(null))
  }

  def expectedRangeFor(accessor: TempAcc, field: TemporalField): ValueRange = field.range()

  @Test def range(): Unit = {
    for {
      accessor <- samples
      field <- ChronoField.values
    } {
      if (accessor.isSupported(field)) {
        val expected = expectedRangeFor(accessor, field)
        assertEquals(expected, accessor.range(field))
      } else {
        assertThrows(classOf[UnsupportedTemporalTypeException], accessor.range(field))
      }
    }
  }

  @Test def get(): Unit = {
    for {
      accessor <- samples
      field <- ChronoField.values
    } {
      if (accessor.isSupported(field) && field.range.isIntValue)
        assertEquals(accessor.getLong(field), accessor.get(field).toLong)
      else if (accessor.isSupported(field))
        assertThrows(classOf[DateTimeException], accessor.get(field))
      else
        assertThrows(classOf[UnsupportedTemporalTypeException], accessor.get(field))
    }
  }

  @Test def getLong_unsupported_field(): Unit = {
    for {
      accessor <- samples
      field <- ChronoField.values() if !accessor.isSupported(field)
    } {
      assertThrows(classOf[UnsupportedTemporalTypeException],
          accessor.getLong(field))
    }
  }
}
