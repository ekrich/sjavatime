package org.scalajs.testsuite.javalib.time.temporal

import java.time.temporal.ChronoField

import org.junit.Test
import org.junit.Assert._
import org.scalajs.testsuite.utils.AssertThrows._

class ChronoFieldTest {
  import ChronoField._

  @Test def test_isDateBased(): Unit = {
    assert(!NANO_OF_SECOND.isDateBased)
    assert(!NANO_OF_DAY.isDateBased)
    assert(!MICRO_OF_SECOND.isDateBased)
    assert(!MICRO_OF_DAY.isDateBased)
    assert(!MILLI_OF_SECOND.isDateBased)
    assert(!MILLI_OF_DAY.isDateBased)
    assert(!SECOND_OF_MINUTE.isDateBased)
    assert(!SECOND_OF_DAY.isDateBased)
    assert(!MINUTE_OF_HOUR.isDateBased)
    assert(!MINUTE_OF_DAY.isDateBased)
    assert(!HOUR_OF_AMPM.isDateBased)
    assert(!CLOCK_HOUR_OF_AMPM.isDateBased)
    assert(!HOUR_OF_DAY.isDateBased)
    assert(!CLOCK_HOUR_OF_DAY.isDateBased)
    assert(!AMPM_OF_DAY.isDateBased)
    assert(DAY_OF_WEEK.isDateBased)
    assert(ALIGNED_DAY_OF_WEEK_IN_MONTH.isDateBased)
    assert(ALIGNED_DAY_OF_WEEK_IN_YEAR.isDateBased)
    assert(DAY_OF_MONTH.isDateBased)
    assert(DAY_OF_YEAR.isDateBased)
    assert(EPOCH_DAY.isDateBased)
    assert(ALIGNED_WEEK_OF_MONTH.isDateBased)
    assert(ALIGNED_WEEK_OF_YEAR.isDateBased)
    assert(MONTH_OF_YEAR.isDateBased)
    assert(PROLEPTIC_MONTH.isDateBased)
    assert(YEAR_OF_ERA.isDateBased)
    assert(YEAR.isDateBased)
    assert(ERA.isDateBased)
    assert(!INSTANT_SECONDS.isDateBased)
    assert(!OFFSET_SECONDS.isDateBased)
  }

  @Test def test_isTimeBased(): Unit = {
    assert(NANO_OF_SECOND.isTimeBased)
    assert(NANO_OF_DAY.isTimeBased)
    assert(MICRO_OF_SECOND.isTimeBased)
    assert(MICRO_OF_DAY.isTimeBased)
    assert(MILLI_OF_SECOND.isTimeBased)
    assert(MILLI_OF_DAY.isTimeBased)
    assert(SECOND_OF_MINUTE.isTimeBased)
    assert(SECOND_OF_DAY.isTimeBased)
    assert(MINUTE_OF_HOUR.isTimeBased)
    assert(MINUTE_OF_DAY.isTimeBased)
    assert(HOUR_OF_AMPM.isTimeBased)
    assert(CLOCK_HOUR_OF_AMPM.isTimeBased)
    assert(HOUR_OF_DAY.isTimeBased)
    assert(CLOCK_HOUR_OF_DAY.isTimeBased)
    assert(AMPM_OF_DAY.isTimeBased)
    assert(!DAY_OF_WEEK.isTimeBased)
    assert(!ALIGNED_DAY_OF_WEEK_IN_MONTH.isTimeBased)
    assert(!ALIGNED_DAY_OF_WEEK_IN_YEAR.isTimeBased)
    assert(!DAY_OF_MONTH.isTimeBased)
    assert(!DAY_OF_YEAR.isTimeBased)
    assert(!EPOCH_DAY.isTimeBased)
    assert(!ALIGNED_WEEK_OF_MONTH.isTimeBased)
    assert(!ALIGNED_WEEK_OF_YEAR.isTimeBased)
    assert(!MONTH_OF_YEAR.isTimeBased)
    assert(!PROLEPTIC_MONTH.isTimeBased)
    assert(!YEAR_OF_ERA.isTimeBased)
    assert(!YEAR.isTimeBased)
    assert(!ERA.isTimeBased)
    assert(!INSTANT_SECONDS.isTimeBased)
    assert(!OFFSET_SECONDS.isTimeBased)
  }

  @Test def test_values(): Unit = {
    val fields = Array[AnyRef](NANO_OF_SECOND, NANO_OF_DAY, MICRO_OF_SECOND,
        MICRO_OF_DAY, MILLI_OF_SECOND, MILLI_OF_DAY, SECOND_OF_MINUTE,
        SECOND_OF_DAY, MINUTE_OF_HOUR, MINUTE_OF_DAY, HOUR_OF_AMPM,
        CLOCK_HOUR_OF_AMPM, HOUR_OF_DAY, CLOCK_HOUR_OF_DAY, AMPM_OF_DAY,
        DAY_OF_WEEK, ALIGNED_DAY_OF_WEEK_IN_MONTH, ALIGNED_DAY_OF_WEEK_IN_YEAR,
        DAY_OF_MONTH, DAY_OF_YEAR, EPOCH_DAY, ALIGNED_WEEK_OF_MONTH,
        ALIGNED_WEEK_OF_YEAR, MONTH_OF_YEAR, PROLEPTIC_MONTH, YEAR_OF_ERA, YEAR,
        ERA, INSTANT_SECONDS, OFFSET_SECONDS)
    assertArrayEquals(fields, values.asInstanceOf[Array[AnyRef]])
  }

  @Test def test_valueOf(): Unit = {
    assertEquals(NANO_OF_SECOND, valueOf("NANO_OF_SECOND"))
    assertEquals(NANO_OF_DAY, valueOf("NANO_OF_DAY"))
    assertEquals(MICRO_OF_SECOND, valueOf("MICRO_OF_SECOND"))
    assertEquals(MICRO_OF_DAY, valueOf("MICRO_OF_DAY"))
    assertEquals(MILLI_OF_SECOND, valueOf("MILLI_OF_SECOND"))
    assertEquals(MILLI_OF_DAY, valueOf("MILLI_OF_DAY"))
    assertEquals(SECOND_OF_MINUTE, valueOf("SECOND_OF_MINUTE"))
    assertEquals(SECOND_OF_DAY, valueOf("SECOND_OF_DAY"))
    assertEquals(MINUTE_OF_HOUR, valueOf("MINUTE_OF_HOUR"))
    assertEquals(MINUTE_OF_DAY, valueOf("MINUTE_OF_DAY"))
    assertEquals(HOUR_OF_AMPM, valueOf("HOUR_OF_AMPM"))
    assertEquals(CLOCK_HOUR_OF_AMPM, valueOf("CLOCK_HOUR_OF_AMPM"))
    assertEquals(HOUR_OF_DAY, valueOf("HOUR_OF_DAY"))
    assertEquals(CLOCK_HOUR_OF_DAY, valueOf("CLOCK_HOUR_OF_DAY"))
    assertEquals(AMPM_OF_DAY, valueOf("AMPM_OF_DAY"))
    assertEquals(DAY_OF_WEEK, valueOf("DAY_OF_WEEK"))
    assertEquals(ALIGNED_DAY_OF_WEEK_IN_MONTH,
        valueOf("ALIGNED_DAY_OF_WEEK_IN_MONTH"))
    assertEquals(ALIGNED_DAY_OF_WEEK_IN_YEAR,
        valueOf("ALIGNED_DAY_OF_WEEK_IN_YEAR"))
    assertEquals(DAY_OF_MONTH, valueOf("DAY_OF_MONTH"))
    assertEquals(DAY_OF_YEAR, valueOf("DAY_OF_YEAR"))
    assertEquals(EPOCH_DAY, valueOf("EPOCH_DAY"))
    assertEquals(ALIGNED_WEEK_OF_MONTH, valueOf("ALIGNED_WEEK_OF_MONTH"))
    assertEquals(ALIGNED_WEEK_OF_YEAR, valueOf("ALIGNED_WEEK_OF_YEAR"))
    assertEquals(MONTH_OF_YEAR, valueOf("MONTH_OF_YEAR"))
    assertEquals(PROLEPTIC_MONTH, valueOf("PROLEPTIC_MONTH"))
    assertEquals(YEAR_OF_ERA, valueOf("YEAR_OF_ERA"))
    assertEquals(YEAR, valueOf("YEAR"))
    assertEquals(ERA, valueOf("ERA"))
    assertEquals(INSTANT_SECONDS, valueOf("INSTANT_SECONDS"))
    assertEquals(OFFSET_SECONDS, valueOf("OFFSET_SECONDS"))
  }
}
