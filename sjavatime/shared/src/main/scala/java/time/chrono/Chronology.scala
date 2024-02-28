package java.time.chrono

import java.time.{Period, DateTimeException, PlatformSpecific}
import java.time.temporal.{ValueRange, ChronoField, TemporalAccessor}
import java.{util => ju}

trait Chronology extends Comparable[Chronology] {
  def getId(): String

  def getCalendarType(): String

  def date(
      era: Era, yearOfEra: Int, month: Int, dayOfMonth: Int): ChronoLocalDate =
    date(prolepticYear(era, yearOfEra), month, dayOfMonth)

  def date(prolepticYear: Int, month: Int, dayOfMonth: Int): ChronoLocalDate

  def dateYearDay(era: Era, yearOfEra: Int, dayOfYear: Int): ChronoLocalDate =
    dateYearDay(prolepticYear(era, yearOfEra), dayOfYear)

  def dateYearDay(prolepticYear: Int, dayOfYear: Int): ChronoLocalDate

  def dateEpochDay(epochDay: Long): ChronoLocalDate

  def dateNow(): ChronoLocalDate = {
    val (year, month, day) = PlatformSpecific.chronoLocalDate()
    date(year, month, day)
  }

  // Not implemented
  // def dateNow(zone: ZoneId): ChronoLocalDate
  // def dateNow(clock: Clock): ChronoLocalDate

  def date(temporal: TemporalAccessor): ChronoLocalDate

  // TODO
  // def localDateTime(temporal: TemporalAccessor): ChronoLocalDateTime[_]

  // Not implemented
  // def zonedDateTime(temporal: TemporalAccessor): ChronoZonedDateTime[_]
  // def zonedDateTime(instant: Instant, zone: ZoneId): ChronoZonedDateTime[_]

  def isLeapYear(prolepticYear: Long): Boolean

  def prolepticYear(era: Era, yearOfEra: Int): Int

  def eraOf(eraValue: Int): Era

  def eras(): ju.List[Era]

  def range(field: ChronoField): ValueRange

  // Not implemented
  // def getDisplayName(style: java.time.format.TextStyle,
  //     locale: ju.Locale): String

  // Not implemented
  // def resolveDate(fieldValues: ju.Map[TemporalField, Long],
  //     resolverStyle: java.time.format.ResolverStyle): ChronoLocalDate

  def period(years: Int, months: Int, days: Int): ChronoPeriod =
    Period.of(years, months, days)
}

object Chronology {
  // Not implemented
  // def from(temporal: TemporalAccessor): Chronology
  // def ofLocale(locale: ju.Locale): Chronology

  def of(id: String): Chronology = {
    // scalastyle:off return
    val iter = getAvailableChronologies().iterator()
    while (iter.hasNext()) {
      val chronology = iter.next()
      if (chronology.getId() == id)
        return chronology
    }
    throw new DateTimeException(s"Unknown chronology: $id")
    // scalastyle:on return
  }

  def getAvailableChronologies(): ju.Set[Chronology] =
    ju.Collections.singleton(IsoChronology.INSTANCE)
}
