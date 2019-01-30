package ir.easazade.dailynotes.utils

import java.sql.Timestamp
import java.util.*

class DateUtils {

  companion object {
    private val notDefinedDate: Timestamp by lazy {
      val cal = GregorianCalendar(1990, 1, 1)
      Timestamp(cal.timeInMillis)
    }

    fun isNotSet(time: Timestamp): Boolean =
        time.time == notDefinedDate.time

    fun notDefinedDate(): Timestamp = notDefinedDate

    /***
     * just if you were wondering month starts from 1(= jan) like normal programming languages
     */
    fun getTimestampOf(
      year: Int, month: Int, day: Int,
      hour: Int? = null, minute: Int? = null, second: Int? = null
    ): Timestamp {
      val javaMonthLOL = month - 1
      return if (hour != null && minute != null && second != null)
        GregorianCalendar(year, javaMonthLOL, day, hour, minute, second).run {
          Timestamp(timeInMillis)
        }
      else
        GregorianCalendar(year, javaMonthLOL, day).run { Timestamp(timeInMillis) }
    }

    fun currentTime(): Timestamp = Timestamp(System.currentTimeMillis())

    fun formatDate(timestamp: Timestamp): String {
      val now = currentTime().time / (24 * 60 * 60 * 1000)
      val date = timestamp.time / (24 * 60 * 60 * 1000)
      val delta = now - date
      return when (delta.toInt()) {
        in -1..1 -> "today"
        in 1..2 -> "yesterday"
        in 2..7 -> "this week"
        in 7..14 -> "last weeks"
        in 14..30 -> "this month"
        in 30..45 -> "last month"
        else -> "long time ago"
      }
    }

  }

}