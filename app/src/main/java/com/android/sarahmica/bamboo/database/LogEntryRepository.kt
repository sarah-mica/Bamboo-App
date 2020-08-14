package com.android.sarahmica.bamboo.database

import timber.log.Timber
import java.util.*

class LogEntryRepository private constructor(
    private val logEntryDao: LogEntryDao,
    private val logEntryWithActivityDao: LogEntryWithActivityDao
) {
    suspend fun insertLogEntry(activityList: List<Int>) {

        // first insert a new log entry
        var logEntry: LogEntry? = LogEntry()
        logEntryDao.insert(logEntry!!)
        Timber.i("logEntry id: %s", logEntry.id)

        val dayStart: Calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            set(Calendar.AM_PM, Calendar.AM)
        }
        val dayEnd: Calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR, 11)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
            set(Calendar.AM_PM, Calendar.PM)
        }

        logEntry = logEntryDao.getEntryForDay(dayStart, dayEnd)
        Timber.i("logEntry id: %s", logEntry!!.id)

        // Now insert a logEntry with all its associated activities
        activityList.forEach { activityId ->
            val logEntryWithActivities = ActivityLogEntry(activityId, logEntry.id)
            logEntryWithActivityDao.insert(logEntryWithActivities)
        }

    }

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: LogEntryRepository? = null

        fun getInstance(logEntryDao: LogEntryDao, logEntryWithActivityDao: LogEntryWithActivityDao) =
            instance ?: synchronized(this) {
                instance ?: LogEntryRepository(logEntryDao, logEntryWithActivityDao).also { instance = it }
            }
    }
}
