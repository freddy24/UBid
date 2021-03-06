package react.freddy.com.ubid.vo

import androidx.room.TypeConverter
import timber.log.Timber
import java.lang.NumberFormatException
import java.util.*

/**
 * data :2020/7/16
 * auth :wjp
 * Description :
 */
class UBidConverters {

    @TypeConverter fun calendarToDateStamp(calendar: Calendar): Long = calendar.timeInMillis

    @TypeConverter fun dateStampToCalendar(value: Long): Calendar = Calendar.getInstance().apply {
        timeInMillis = value
    }

    @TypeConverter fun stringToIntList(data: String?): List<Int>? {
        return data?.let {
            it.split(",").map {
                try {
                    it.toInt()
                } catch (ex: NumberFormatException) {
                    Timber.e(ex, "Cannot convert $it to number")
                    null
                }
            }
        }?.filterNotNull()
    }

    @TypeConverter fun intListToString(ints: List<Int>?): String?{
        return ints?.joinToString ( "," )
    }
}