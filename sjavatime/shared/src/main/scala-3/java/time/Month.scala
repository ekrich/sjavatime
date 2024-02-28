package java.time

import java.{lang => jl}
import java.time.temporal._

enum Month(value: Int, defaultLength: Int) extends jl.Enum[Month]
    with TemporalAccessor
    with TemporalAdjuster {

  case JANUARY extends Month(1, 31)

  case FEBRUARY extends Month(2, 28)

  case MARCH extends Month(3, 31)

  case APRIL extends Month(4, 30)

  case MAY extends Month(5, 31)

  case JUNE extends Month(6, 30)

  case JULY extends Month(7, 31)

  case AUGUST extends Month(8, 31)

  case SEPTEMBER extends Month(9, 30)

  case OCTOBER extends Month(10, 31)

  case NOVEMBER extends Month(11, 30)

  case DECEMBER extends Month(12, 31)

  import Month._

  private lazy val defaultFirstDayOfYear =
    values.take(value - 1).foldLeft(1)(_ + _.minLength())

  def getValue(): Int = value

  // Not implemented
  // def getDisplayName(style: TextStyle, locale: Locale): Locale

  def isSupported(field: TemporalField): Boolean = field match {
    case _: ChronoField => field == ChronoField.MONTH_OF_YEAR
    case null           => false
    case _              => field.isSupportedBy(this)
  }

  // Implemented by TemporalAccessor
  // def range(field: TemporalField): ValueRange

  // Implemented by TemporalAccessor
  // def get(field: TemporalField): Int

  def getLong(field: TemporalField): Long = field match {
    case ChronoField.MONTH_OF_YEAR => ordinal + 1

    case _: ChronoField =>
      throw new UnsupportedTemporalTypeException(s"Field not supported: $field")

    case _ => field.getFrom(this)
  }

  def plus(months: Long): Month = {
    val offset = if (months < 0) months % 12 + 12 else months % 12
    of((ordinal + offset.toInt) % 12 + 1)
  }

  def minus(months: Long): Month = {
    val offset = if (months < 0) months % 12 else months % 12 - 12
    of((ordinal - offset.toInt) % 12 + 1)
  }

  def length(leapYear: Boolean): Int =
    if (leapYear) maxLength() else minLength()

  def minLength(): Int = defaultLength

  def maxLength(): Int =
    if (value == 2) defaultLength + 1 else defaultLength

  def firstDayOfYear(leapYear: Boolean): Int =
    if (leapYear && value > 2) defaultFirstDayOfYear + 1
    else defaultFirstDayOfYear

  def firstMonthOfQuarter(): Month = of((ordinal / 3) * 3 + 1)

  // Not implemented
  // def query[R](query: TemporalQuery[R]): R

  def adjustInto(temporal: Temporal): Temporal =
    temporal.`with`(ChronoField.MONTH_OF_YEAR, value)
}

object Month {

  def of(month: Int): Month = values.lift(month - 1).getOrElse {
    throw new DateTimeException(s"Invalid value for month: $month")
  }

  def from(temporal: TemporalAccessor): Month =
    Month.of(temporal.get(ChronoField.MONTH_OF_YEAR))
}
