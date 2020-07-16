package java.time

import scala.scalajs.js

private[time] object PlatformSpecific extends PlatformCommon {
  def localDate(): LocalDate = {
    val d = new js.Date()
    LocalDate.of(d.getFullYear.toInt, d.getMonth.toInt + 1, d.getDate.toInt)
  }

  def chronoLocalDate(): (Int, Int, Int) = {
    val d = new js.Date()
    (d.getFullYear.toInt, d.getMonth.toInt + 1, d.getDate.toInt)
  }

  def localTime(): LocalTime = {
    val date = new js.Date()
    val nano = date.getMilliseconds.toInt * 1000000
    LocalTime.of(date.getHours.toInt, date.getMinutes.toInt, date.getSeconds.toInt, nano)
  }

  def minDay(day: Int, lastDayOfMonth: Int): Int = {
    val dayOfMonth = js.Math.min(day, lastDayOfMonth)
    dayOfMonth
  }
}
