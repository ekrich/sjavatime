package java.time

import scala.scalanative.unsafe.extern
import scala.scalanative.unsafe.CLongLong

import java.time.Constants._

@extern object UseFFI {
  def scalanative_time_zone_offset(): CLongLong = extern
}

private[time] object PlatformSpecific extends PlatformCommon {

  def localDate(): LocalDate = {
    val epochSecond = Instant.toEpochSecond(System.currentTimeMillis())
    val epochDay = Instant.toEpochDay(epochSecond)
    LocalDate.ofEpochDay(epochDay)
  }

  // no test - gets called by Chronology trait dateNow()
  def chronoLocalDate(): (Int, Int, Int) = {
    val epochSecond = Instant.toEpochSecond(System.currentTimeMillis())
    val epochDay = Instant.toEpochDay(epochSecond)
    val date = LocalDate.ofEpochDay(epochDay)
    (date.getYear(), date.getMonthValue(), date.getDayOfMonth())
  }

  def localTime(): LocalTime = {
    val now = Instant.now()
    val epochSeconds = now.getEpochSecond()
    // UTC to local time - private api
    val offset = UseFFI.scalanative_time_zone_offset()
    val offsetSeconds = epochSeconds + offset
    val nanos = now.getNano()
    // sec/yr, sec/day
    // Bug: this needs to adjust for leap year ?
    val secPerDay = ((offsetSeconds % SECONDS_IN_YEAR) % SECONDS_IN_DAY)
    // sec/hr
    val currentHours = Math.floor((secPerDay / SECONDS_IN_HOUR).toDouble).toInt
    val secPerHour = secPerDay % SECONDS_IN_HOUR
    // sec/min
    val currentMinutes =
      Math.floor((secPerHour / SECONDS_IN_MINUTE).toDouble).toInt
    val currentSeconds =
      Math.floor((secPerHour % SECONDS_IN_MINUTE).toDouble).toInt
    LocalTime.of(currentHours, currentMinutes, currentSeconds, nanos)
  }

  def minDay(day: Int, lastDayOfMonth: Int): Int = {
    val dayOfMonth = Math.min(day, lastDayOfMonth)
    dayOfMonth
  }
}
