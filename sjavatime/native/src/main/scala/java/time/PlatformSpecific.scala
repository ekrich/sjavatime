package java.time

private[time] object PlatformSpecific extends PlatformCommon {
  def localDate(): LocalDate = {
    val epochSecond = Instant.toEpochSecond(System.currentTimeMillis())
    val epochDay = Instant.toEpochDay(epochSecond)
    LocalDate.ofEpochDay(epochDay)
  }

  def chronoLocalDate(): (Int, Int, Int) = ???
  // {
  //   val d = new js.Date()
  //   (d.getFullYear.toInt, d.getMonth.toInt, d.getDate.toInt)
  // }

  def localTime(): LocalTime = ???
  // {
  //   val date = new js.Date()
  //   val nano = date.getMilliseconds.toInt * 1000000
  //   LocalTime.of(date.getHours.toInt, date.getMinutes.toInt, date.getSeconds.toInt, nano)
  // }

  def minDay(day: Int, lastDayOfMonth: Int): Int = {
    val dayOfMonth = Math.min(day, lastDayOfMonth)
    dayOfMonth
  }
}
