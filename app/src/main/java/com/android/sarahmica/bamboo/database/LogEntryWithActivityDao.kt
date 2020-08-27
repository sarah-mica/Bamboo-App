package com.android.sarahmica.bamboo.database

import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.*

@Dao
interface LogEntryWithActivityDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entryWithActivities: ActivityLogEntry)

    @Update
    suspend fun update(entryWithActivities: ActivityLogEntry)

    /**
     * Get a list of all logEntries and their associated activities
     */
    @Transaction
    @Query("SELECT * FROM log_entry_table ORDER BY day_completed DESC")
    fun getAllLogEntriesWithActivities(): LiveData<List<LogEntryWithActivities>>

    /**
     * Get a logEntry and its associated activities for the given date
     *
     * @param dayStart The start of the day
     * @param dayEnd The end of the day
     */
    @Transaction
    @Query("SELECT * FROM log_entry_table WHERE day_completed BETWEEN :dayStart AND :dayEnd")
    fun getLogEntryWithActivities(dayStart: Calendar, dayEnd: Calendar): LogEntryWithActivities?

    /**
     * Get a logEntry and its associated activities for the given id
     *
     * @param logEntryId the id associated with this particular entry
     */
    @Transaction
    @Query("SELECT * FROM log_entry_table WHERE logEntryId = :logEntryId")
    fun getLogEntryWithActivities(logEntryId: Long): LogEntryWithActivities?

}