package java.time

import java.{lang => jl}
import java.time.temporal._

enum DayOfWeek(value: Int) extends jl.Enum[DayOfWeek] with TemporalAccessor
    with TemporalAdjuster {

  case MONDAY extends DayOfWeek(1)

  case TUESDAY extends DayOfWeek(2)

  case WEDNESDAY extends DayOfWeek(3)

  case THURSDAY extends DayOfWeek(4)

  case FRIDAY extends DayOfWeek(5)

  case SATURDAY extends DayOfWeek(6)

  case SUNDAY extends DayOfWeek(7)

  def getValue(): Int = value

  // Not implemented
  // def getDisplayName(style: TextStyle, locale: ju.Locale): String

  def isSupported(field: TemporalField): Boolean = field match {
    case _: ChronoField => field == ChronoField.DAY_OF_WEEK
    case null           => false
    case _              => field.isSupportedBy(this)
  }

  // Implemented by TemporalAccessor
  // def range(field: TemporalField): ValueRange

  // Implemented by TemporalAccessor
  // def get(field: TemporalField): Int

  def getLong(field: TemporalField): Long = field match {
    case ChronoField.DAY_OF_WEEK => ordinal + 1

    case _: ChronoField =>
      throw new UnsupportedTemporalTypeException(s"Field not supported: $field")

    case _ => field.getFrom(this)
  }

  def plus(days: Long): DayOfWeek = {
    val offset = (days % 7 + 7) % 7
    DayOfWeek.of((ordinal + offset.toInt) % 7 + 1)
  }

  def minus(days: Long): DayOfWeek = plus(-(days % 7))

  // Not implemented
  // def query[R](query: TemporalQuery[R]): R

  def adjustInto(temporal: Temporal): Temporal =
    temporal.`with`(ChronoField.DAY_OF_WEEK, ordinal + 1)
}

object DayOfWeek {

  def of(dayOfWeek: Int): DayOfWeek = values.lift(dayOfWeek - 1).getOrElse {
    throw new DateTimeException(s"Invalid value for weekday: $dayOfWeek")
  }

  def from(temporal: TemporalAccessor): DayOfWeek =
    DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK))
}
