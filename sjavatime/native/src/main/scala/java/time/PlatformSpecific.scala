package java.time

private[time] object PlatformSpecific extends PlatformCommon {

  def localDate(): LocalDate = {
    val epochSecond = Instant.toEpochSecond(System.currentTimeMillis())
    val epochDay    = Instant.toEpochDay(epochSecond)
    LocalDate.ofEpochDay(epochDay)
  }

  // no test - gets called by Chronology trait dateNow()
  def chronoLocalDate(): (Int, Int, Int) = {
    val epochSecond = Instant.toEpochSecond(System.currentTimeMillis())
    val epochDay    = Instant.toEpochDay(epochSecond)
    val date        = LocalDate.ofEpochDay(epochDay)
    (date.getYear(), date.getMonthValue(), date.getDayOfMonth())
  }

  def localTime(): LocalTime = {
    val epochSeconds = Instant.toEpochSecond(System.currentTimeMillis())
    // sec/yr, sec/day, sec/hr, sec/min
    val currentSeconds =
      Math.floor((((epochSeconds % 31536000) % 86400) % 3600) % 60).toInt
    LocalTime.ofSecondOfDay(currentSeconds)
    // From https://github.com/akka-js/scalanative-java-time
    // val currentHours = Math.floor(((seconds % 31536000) % 86400) / 3600).toInt
    // val currentMinutes =
    //   Math.floor((((seconds % 31536000) % 86400) % 3600) / 60).toInt
    // val currentSeconds =
    //   Math.floor((((seconds % 31536000) % 86400) % 3600) % 60).toInt
    // LocalTime.of(currentHours, currentMinutes, currentSeconds)

  }

  def minDay(day: Int, lastDayOfMonth: Int): Int = {
    val dayOfMonth = Math.min(day, lastDayOfMonth)
    dayOfMonth
  }
}
