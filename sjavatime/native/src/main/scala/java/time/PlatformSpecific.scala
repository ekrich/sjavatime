package java.time

import scala.scalanative.unsafe.extern
import scala.scalanative.unsafe.CLongLong

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
    val secPerDay = ((offsetSeconds % 31536000) % 86400)
    // sec/hr
    val currentHours = Math.floor((secPerDay / 3600).toDouble).toInt
    val secPerHour = secPerDay % 3600
    // sec/min
    val currentMinutes = Math.floor((secPerHour / 60).toDouble).toInt
    val currentSeconds = Math.floor((secPerHour % 60).toDouble).toInt
    LocalTime.of(currentHours, currentMinutes, currentSeconds, nanos)
  }

  def minDay(day: Int, lastDayOfMonth: Int): Int = {
    val dayOfMonth = Math.min(day, lastDayOfMonth)
    dayOfMonth
  }
}
