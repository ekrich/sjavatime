package org.scalajs.testsuite.javalib.time

import java.time.DateTimeException
import java.time.temporal.{
  ChronoUnit,
  Temporal,
  UnsupportedTemporalTypeException
}

import org.junit.Assert.assertEquals
import org.scalajs.testsuite.utils.AssertThrows.assertThrows

object DateTimeTestUtil {

  val dateBasedUnits = ChronoUnit.values.filter(_.isDateBased)

  val timeBasedUnits = ChronoUnit.values.filter(_.isTimeBased)

  def testDateTime(actual: => Any)(expected: => Any): Unit = {
    try {
      val e = expected
      assertEquals(e, actual)
    } catch {
      case _: UnsupportedTemporalTypeException =>
        assertThrows(classOf[UnsupportedTemporalTypeException], actual)

      case _: DateTimeException =>
        assertThrows(classOf[DateTimeException], actual)

      case _: ArithmeticException =>
        assertThrows(classOf[ArithmeticException], actual)
    }
  }
}
