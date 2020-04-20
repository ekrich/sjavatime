package org.scalajs.testsuite.javalib.time.chrono

import java.time.DateTimeException
import java.time.chrono.IsoEra
import java.time.temporal.ChronoField

import munit.Assertions._

import org.scalajs.testsuite.javalib.time.TemporalAccessorTest
import org.scalajs.testsuite.utils.AssertThrows._

class IsoEraTest extends TemporalAccessorTest[IsoEra] {
  import IsoEra._

  val samples = values.toSeq

  def isSupported(field: ChronoField): Boolean =
    field == ChronoField.ERA

  test("test_getValue") {
    assertEquals(0, BCE.getValue)
    assertEquals(1, CE.getValue)
  }

  test("test_getLong") {
    for (era <- samples)
      assertEquals(era.getValue.toLong, era.getLong(ChronoField.ERA))
  }

  test("test_compareTo") {
    assertEquals(0, BCE.compareTo(BCE))
    assert(BCE.compareTo(CE) < 0)
    assert(CE.compareTo(BCE) > 0)
    assertEquals(0, CE.compareTo(CE))
  }

  test("test_values") {
    val eras = Array[AnyRef](BCE, CE)
    assertEquals(eras, values.asInstanceOf[Array[AnyRef]])
  }

  test("test_valueOf") {
    assertEquals(BCE, valueOf("BCE"))
    assertEquals(CE, valueOf("CE"))
    expectThrows(classOf[IllegalArgumentException], valueOf(""))
  }

  test("test_of") {
    assertEquals(BCE, of(0))
    assertEquals(CE, of(1))

    for (n <- Seq(Int.MinValue, -1, 2, Int.MaxValue))
      expectThrows(classOf[DateTimeException], of(n))
  }
}
