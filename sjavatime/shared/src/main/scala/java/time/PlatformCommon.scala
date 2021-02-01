package java.time

private[time] trait PlatformCommon {

  /**
   * The local date for now
   *
   * @return now as a LocalDate
   */
  def localDate(): LocalDate

  /**
   * The local date tuple with month values 0 to 11
   *
   * @return year, month, day
   */
  def chronoLocalDate(): (Int, Int, Int)

  /**
   * The local time tuple
   *
   * @return hour, minute, second, nano
   */
  def localTime(): LocalTime

  /**
   * The min of the day or the last day of the month
   *
   * @param day of month
   * @param lastDayOfMonth or max day in this month
   * @return dayOfMonth
   */
  def minDay(day: Int, lastDayOfMonth: Int): Int
}
