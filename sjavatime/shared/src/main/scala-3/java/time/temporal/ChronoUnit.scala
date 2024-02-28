package java.time.temporal

import java.{lang => jl}
import java.time.Duration

enum ChronoUnit(duration: Duration,
    flags: Int) extends jl.Enum[ChronoUnit] with TemporalUnit:

  case NANOS extends ChronoUnit(Duration.OneNano, ChronoUnit.isTimeBasedFlag)

  case MICROS extends ChronoUnit(Duration.OneMicro, ChronoUnit.isTimeBasedFlag)

  case MILLIS extends ChronoUnit(Duration.OneMilli, ChronoUnit.isTimeBasedFlag)

  case SECONDS extends ChronoUnit(Duration.OneSecond, ChronoUnit.isTimeBasedFlag)

  case MINUTES extends ChronoUnit(Duration.OneMinute, ChronoUnit.isTimeBasedFlag)

  case HOURS extends ChronoUnit(Duration.OneHour, ChronoUnit.isTimeBasedFlag)

  case HALF_DAYS extends ChronoUnit(Duration.ofHours(12),
          ChronoUnit.isTimeBasedFlag)

  case DAYS extends ChronoUnit(Duration.OneDay, ChronoUnit.isDateBasedFlag)

  case WEEKS extends ChronoUnit(Duration.OneWeek, ChronoUnit.isDateBasedFlag)

  case MONTHS extends ChronoUnit(Duration.OneMonth, ChronoUnit.isDateBasedFlag)

  case YEARS extends ChronoUnit(Duration.OneYear, ChronoUnit.isDateBasedFlag)

  case DECADES extends ChronoUnit(
          Duration.OneYear.multipliedBy(10), ChronoUnit.isDateBasedFlag)

  case CENTURIES extends ChronoUnit(
          Duration.OneYear.multipliedBy(100), ChronoUnit.isDateBasedFlag)

  case MILLENNIA extends ChronoUnit(
          Duration.OneYear.multipliedBy(1000), ChronoUnit.isDateBasedFlag)

  case ERAS extends ChronoUnit(
          Duration.OneYear.multipliedBy(1000000000), ChronoUnit.isDateBasedFlag)

  case FOREVER extends ChronoUnit(Duration.Max, 0)

  def getDuration(): Duration = duration

  def isDurationEstimated(): Boolean = (flags & 1) == 0

  def isDateBased(): Boolean = (flags & 2) != 0

  def isTimeBased(): Boolean = (flags & 1) != 0

  override def isSupportedBy(temporal: Temporal): Boolean =
    temporal.isSupported(this)

  def addTo[R <: Temporal](temporal: R, amount: Long): R =
    temporal.plus(amount, this).asInstanceOf[R]

  def between(start: Temporal, end: Temporal): Long = start.until(end, this)

object ChronoUnit:
  private final val isTimeBasedFlag = 1
  private final val isDateBasedFlag = 2
