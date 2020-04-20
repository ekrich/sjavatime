package org.scalajs.testsuite.javalib.time.chrono

import java.time.DateTimeException
import java.time.chrono.{IsoChronology, Chronology}

import org.scalajs.testsuite.utils.AssertThrows._

class ChronologyTest extends munit.FunSuite {
  import Chronology._

  test("test_of") {
    assertEquals(of("ISO"), IsoChronology.INSTANCE)
    expectThrows(classOf[DateTimeException], of(""))
  }

  test("test_getAvailableChronologies") {
    val chronologies = Chronology.getAvailableChronologies
    assert(chronologies.contains(IsoChronology.INSTANCE))
  }
}
