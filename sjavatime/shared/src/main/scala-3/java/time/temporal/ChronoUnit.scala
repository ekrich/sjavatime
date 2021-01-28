package java.time.temporal

import java.{lang => jl}
import java.time.Duration

enum ChronoUnit(duration: Duration,
    flags: Int) extends jl.Enum[ChronoUnit] with TemporalUnit {
  // TODO: find out way this doesn't work
  // private final val isTimeBasedFlag = 1
  // private final val isDateBasedFlag = 2

  case NANOS extends ChronoUnit(Duration.OneNano, 1)

  case MICROS extends ChronoUnit(Duration.OneMicro, 1)

  case MILLIS extends ChronoUnit(Duration.OneMilli, 1)

  case SECONDS extends ChronoUnit(Duration.OneSecond, 1)

  case MINUTES extends ChronoUnit(Duration.OneMinute, 1)

  case HOURS extends ChronoUnit(Duration.OneHour, 1)

  case HALF_DAYS extends ChronoUnit(Duration.ofHours(12), 1)

  case DAYS extends ChronoUnit(Duration.OneDay, 2)

  case WEEKS extends ChronoUnit(Duration.OneWeek, 2)

  case MONTHS extends ChronoUnit(Duration.OneMonth, 2)

  case YEARS extends ChronoUnit(Duration.OneYear, 2)

  case DECADES extends ChronoUnit(
      Duration.OneYear.multipliedBy(10), 2)

  case CENTURIES extends ChronoUnit(
      Duration.OneYear.multipliedBy(100), 2)

  case MILLENNIA extends ChronoUnit(
      Duration.OneYear.multipliedBy(1000), 2)

  case ERAS extends ChronoUnit(
      Duration.OneYear.multipliedBy(1000000000), 2)

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
}
