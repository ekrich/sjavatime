package org.scalajs.testsuite.javalib.time.chrono

import java.time.DateTimeException
import java.time.chrono.{IsoChronology, Chronology}

import org.junit.Test
import org.junit.Assert.assertEquals
import org.scalajs.testsuite.utils.AssertThrows.assertThrows

class ChronologyTest {
  import Chronology._

  @Test def test_of(): Unit = {
    assertEquals(of("ISO"), IsoChronology.INSTANCE)
    assertThrows(classOf[DateTimeException], of(""))
  }

  @Test def test_getAvailableChronologies(): Unit = {
    val chronologies = Chronology.getAvailableChronologies
    assert(chronologies.contains(IsoChronology.INSTANCE))
  }
}
