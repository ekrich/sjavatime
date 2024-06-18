package java.time.temporal

import java.{lang => jl}
import java.time.Year

import ChronoUnit._

enum ChronoField(_range: ValueRange, baseUnit: ChronoUnit,
    rangeUnit: ChronoUnit, flags: Int)
    extends jl.Enum[ChronoField] with TemporalField {

  //   private final val isTimeBasedFlag = 1
  //   private final val isDateBasedFlag = 2

  case NANO_OF_SECOND extends ChronoField(
          ValueRange.of(0, 999999999),
          NANOS,
          SECONDS,
          1
      )

  case NANO_OF_DAY extends ChronoField(
          ValueRange.of(0, 86399999999999L),
          NANOS,
          DAYS,
          1
      )

  case MICRO_OF_SECOND extends ChronoField(
          ValueRange.of(0, 999999),
          MICROS,
          SECONDS,
          1
      )

  case MICRO_OF_DAY extends ChronoField(
          ValueRange.of(0, 86399999999L),
          MICROS,
          DAYS,
          1
      )

  case MILLI_OF_SECOND extends ChronoField(
          ValueRange.of(0, 999),
          MILLIS,
          SECONDS,
          1
      )

  case MILLI_OF_DAY extends ChronoField(
          ValueRange.of(0, 86399999),
          MILLIS,
          DAYS,
          1
      )

  case SECOND_OF_MINUTE extends ChronoField(
          ValueRange.of(0, 59),
          SECONDS,
          MINUTES,
          1
      )

  case SECOND_OF_DAY extends ChronoField(
          ValueRange.of(0, 86399),
          SECONDS,
          DAYS,
          1
      )

  case MINUTE_OF_HOUR extends ChronoField(
          ValueRange.of(0, 59),
          MINUTES,
          HOURS,
          1
      )

  case MINUTE_OF_DAY extends ChronoField(
          ValueRange.of(0, 1439),
          MINUTES,
          DAYS,
          1
      )

  case HOUR_OF_AMPM extends ChronoField(
          ValueRange.of(0, 11),
          HOURS,
          HALF_DAYS,
          1
      )

  case CLOCK_HOUR_OF_AMPM extends ChronoField(
          ValueRange.of(1, 12),
          HOURS,
          HALF_DAYS,
          1
      )

  case HOUR_OF_DAY extends ChronoField(
          ValueRange.of(0, 23),
          HOURS,
          DAYS,
          1
      )

  case CLOCK_HOUR_OF_DAY extends ChronoField(
          ValueRange.of(1, 24),
          HOURS,
          DAYS,
          1
      )

  case AMPM_OF_DAY extends ChronoField(
          ValueRange.of(0, 1),
          HALF_DAYS,
          DAYS,
          1
      )

  case DAY_OF_WEEK extends ChronoField(
          ValueRange.of(1, 7),
          DAYS,
          WEEKS,
          2
      )

  case ALIGNED_DAY_OF_WEEK_IN_MONTH extends ChronoField(
          ValueRange.of(1, 7),
          DAYS,
          WEEKS,
          2
      )

  case ALIGNED_DAY_OF_WEEK_IN_YEAR extends ChronoField(
          ValueRange.of(1, 7),
          DAYS,
          WEEKS,
          2
      )

  case DAY_OF_MONTH extends ChronoField(
          ValueRange.of(1, 28, 31),
          DAYS,
          MONTHS,
          2
      )

  case DAY_OF_YEAR extends ChronoField(
          ValueRange.of(1, 365, 366),
          DAYS,
          YEARS,
          2
      )

  case EPOCH_DAY extends ChronoField(
          ValueRange.of(-365249999634L, 365249999634L),
          DAYS,
          FOREVER,
          2
      )

  case ALIGNED_WEEK_OF_MONTH extends ChronoField(
          ValueRange.of(1, 4, 5),
          WEEKS,
          MONTHS,
          2
      )

  case ALIGNED_WEEK_OF_YEAR extends ChronoField(
          ValueRange.of(1, 53),
          WEEKS,
          YEARS,
          2
      )

  case MONTH_OF_YEAR extends ChronoField(
          ValueRange.of(1, 12),
          MONTHS,
          YEARS,
          2
      )

  case PROLEPTIC_MONTH extends ChronoField(
          ValueRange.of(-11999999988L, 11999999999L),
          MONTHS,
          FOREVER,
          2
      )

  case YEAR_OF_ERA extends ChronoField(
          ValueRange.of(1, 999999999, 1000000000),
          YEARS,
          ERAS,
          2
      )

  case YEAR extends ChronoField(
          ValueRange.of(Year.MIN_VALUE, Year.MAX_VALUE),
          YEARS,
          FOREVER,
          2
      )

  case ERA extends ChronoField(ValueRange.of(0, 1), ERAS, FOREVER, 2)

  case INSTANT_SECONDS extends ChronoField(
          ValueRange.of(Long.MinValue, Long.MaxValue),
          SECONDS,
          FOREVER,
          0
      )

  case OFFSET_SECONDS extends ChronoField(
          ValueRange.of(-64800, 64800),
          SECONDS,
          FOREVER,
          0
      )

  // Not implemented:
  // def String getDisplayName(locale: java.util.Locale)

  def getBaseUnit(): TemporalUnit = baseUnit

  def getRangeUnit(): TemporalUnit = rangeUnit

  def range(): ValueRange = _range

  def isDateBased(): Boolean = (flags & 2) != 0

  def isTimeBased(): Boolean = (flags & 1) != 0

  def checkValidValue(value: Long): Long =
    _range.checkValidValue(value, this)

  def checkValidIntValue(value: Long): Int =
    _range.checkValidIntValue(value, this)

  def isSupportedBy(temporal: TemporalAccessor): Boolean =
    temporal.isSupported(this)

  def rangeRefinedBy(temporal: TemporalAccessor): ValueRange =
    temporal.range(this)

  def getFrom(temporal: TemporalAccessor): Long = temporal.getLong(this)

  def adjustInto[R <: Temporal](temporal: R, newValue: Long): R =
    temporal.`with`(this, newValue).asInstanceOf[R]
}
