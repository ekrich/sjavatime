package java.time

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
    val nanos = now.getNano()
    // sec/yr, sec/day, sec/hr, sec/min
    val secPerDay = ((epochSeconds % 31536000) % 86400)
    val currentHours = Math.floor((secPerDay / 3600).toDouble).toInt
    val secPerHour = secPerDay % 3600
    val currentMinutes = Math.floor((secPerHour / 60).toDouble).toInt
    val currentSeconds = Math.floor((secPerHour % 60).toDouble).toInt
    // this would need a timezone offset - currently UTC
    LocalTime.of(currentHours, currentMinutes, currentSeconds, nanos)
  }

  def minDay(day: Int, lastDayOfMonth: Int): Int = {
    val dayOfMonth = Math.min(day, lastDayOfMonth)
    dayOfMonth
  }
}
