package shared

import java.time.Month



object PlatformSpecific extends PlatformCommon {
  def epochMilli(): Long = ???
  // {
  //   val date = new js.Date()
  //   date.getTime.toLong
  // }

  def localDate(): (Int, Int, Int) = ???
  // {
  //   val d = new js.Date()
  //   (d.getFullYear.toInt, d.getMonth.toInt + 1, d.getDate.toInt)
  // }

  def chronoLocalDate(): (Int, Int, Int) = ???
  // {
  //   val d = new js.Date()
  //   (d.getFullYear.toInt, d.getMonth.toInt, d.getDate.toInt)
  // }

  def localTime(): (Int, Int, Int, Int) = ???
  // {
  //   val date = new js.Date()
  //   val nano = date.getMilliseconds.toInt * 1000000
  //   (date.getHours.toInt, date.getMinutes.toInt, date.getSeconds.toInt, nano)
  // }

  def minDay(day: Int, lastDayOfMonth: Int): Int = ???
  // {
  //   val dayOfMonth = js.Math.min(day, lastDayOfMonth)
  //   dayOfMonth
  // }
}
