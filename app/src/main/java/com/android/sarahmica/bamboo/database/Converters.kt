package com.android.sarahmica.bamboo.database

import androidx.room.TypeConverter
import com.android.sarahmica.bamboo.ActivityType
import timber.log.Timber
import java.util.*


/**
 * Type converters to allow Room to reference complex data types.
 */
class Converters {
    @TypeConverter
    fun calendarToDatestamp(calendar: Calendar): Long = calendar.timeInMillis

    @TypeConverter
    fun datestampToCalendar(value: Long): Calendar =
        Calendar.getInstance().apply { timeInMillis = value }

    @TypeConverter
    fun toActivityType(activityType: Int): ActivityType {
        return if (activityType == ActivityType.WASTE.ordinal) {
            ActivityType.WASTE
        } else if (activityType == ActivityType.ENERGY.ordinal) {
            ActivityType.ENERGY
        } else if (activityType == ActivityType.WATER.ordinal) {
            ActivityType.WATER
        } else if (activityType == ActivityType.ACTIVISM.ordinal) {
            ActivityType.ACTIVISM
        } else {
            Timber.w("Could not identify the ActivityType: %s", activityType)
            ActivityType.UNKNOWN
        }
    }

    @TypeConverter
    fun fromActivityTypeToInt(activityType: ActivityType): Int? {
        return activityType.ordinal
    }
}