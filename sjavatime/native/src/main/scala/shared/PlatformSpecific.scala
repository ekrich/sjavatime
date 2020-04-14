package java.time

private[time] object PlatformSpecific extends PlatformCommon {
  private[time] def localDate(): (Int, Int, Int) = ???
  // {
  //   val d = new js.Date()
  //   (d.getFullYear.toInt, d.getMonth.toInt + 1, d.getDate.toInt)
  // }

  private[time] def chronoLocalDate(): (Int, Int, Int) = ???
  // {
  //   val d = new js.Date()
  //   (d.getFullYear.toInt, d.getMonth.toInt, d.getDate.toInt)
  // }

  private[time] def localTime(): (Int, Int, Int, Int) = ???
  // {
  //   val date = new js.Date()
  //   val nano = date.getMilliseconds.toInt * 1000000
  //   (date.getHours.toInt, date.getMinutes.toInt, date.getSeconds.toInt, nano)
  // }

  private[time] def minDay(day: Int, lastDayOfMonth: Int): Int = ???
  // {
  //   val dayOfMonth = js.Math.min(day, lastDayOfMonth)
  //   dayOfMonth
  // }
}
