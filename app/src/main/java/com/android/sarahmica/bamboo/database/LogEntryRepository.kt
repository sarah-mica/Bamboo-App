package com.android.sarahmica.bamboo.database

import androidx.lifecycle.LiveData
import timber.log.Timber
import java.util.*

class LogEntryRepository private constructor(
    private val logEntryDao: LogEntryDao,
    private val logEntryWithActivityDao: LogEntryWithActivityDao
) {
    suspend fun insertLogEntry(activityList: List<Int>) {

        // don't insert a null entry
        if (activityList.isEmpty()) {
            return
        }

        // first insert a new log entry
        var logEntry: LogEntry? = LogEntry()
        val logEntryId: Long = logEntryDao.insert(logEntry!!)
        Timber.i("logEntry id: %s", logEntryId)

        // Now insert a logEntry with all its associated activities
        activityList.forEach { activityId ->
            val logEntryWithActivities = ActivityLogEntry(activityId, logEntryId)
            logEntryWithActivityDao.insert(logEntryWithActivities)
        }
    }

    suspend fun updateLogEntry(dayKey: Long, activityList: List<Int>) {
        val currentEntries: List<GreenActivity>? = logEntryWithActivityDao.getLogEntryWithActivities(dayKey)?.greenActivityList

        if (currentEntries == null) {
            // This should not happen
            insertLogEntry(activityList)
            return
        }

        val currentIds: List<Int> = currentEntries.map { activity -> activity.activityId }
        val activitiesToRemove = currentEntries.filter { greenActivity ->  !activityList.contains(greenActivity.activityId) }
        val entriesToRemove = activitiesToRemove.map { activity -> ActivityLogEntry(activity.activityId, dayKey) }

        // remove entries that are no longer selected
        logEntryWithActivityDao.removeAll(entriesToRemove)

        // Now insert a logEntry with all its associated activities
        activityList.forEach { activityId ->
            // only insert a new entry if it doesn't already exist
            if (!currentIds.contains(activityId)) {
                val logEntryWithActivities = ActivityLogEntry(activityId, dayKey)
                logEntryWithActivityDao.insert(logEntryWithActivities)
            }
        }

        if (activityList.isEmpty()) {
            // if there are no longer any activities then remove the log entry as well
            logEntryDao.delete(dayKey)
        }
    }

    fun getEntry(logEntryId: Long): LogEntryWithActivities? {
        return logEntryWithActivityDao.getLogEntryWithActivities(logEntryId)
    }

    fun getToday(): LogEntryWithActivities? {
        // These variables are to specify the Calendar start and end of day so we
        // can search for the entry within that time frame
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
        return logEntryWithActivityDao.getLogEntryWithActivities(dayStart, dayEnd)
    }

    fun getAllLogEntries(): LiveData<List<LogEntryWithActivities>> {
        return logEntryWithActivityDao.getAllLogEntriesWithActivities()
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
