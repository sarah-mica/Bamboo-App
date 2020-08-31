package com.android.sarahmica.bamboo.database

import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.*

@Dao
interface LogEntryDao {
    @Insert (onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(logEntry: LogEntry): Long

    @Update
    suspend fun update(logEntry: LogEntry)

    @Query("DELETE FROM log_entry_table WHERE logEntryId = :logEntryId")
    suspend fun delete(logEntryId: Long)

    /**
     * Selects and returns the row that matches the supplied primary key
     *
     * @param entryId the id value for the entry we want to look up
     */
    @Query("SELECT * FROM log_entry_table WHERE logEntryId = :entryId")
    fun get(entryId: Long): LogEntry?

    /**
     * Selects and returns the entry for the supplied date.
     * There can only be one entry per day, so we just SELECT 1
     *
     * @param dayStart the start time of the [Calendar] date
     * @param dayEnd the end time of the [Calendar] date
     */
    @Query("SELECT * FROM log_entry_table WHERE day_completed BETWEEN :dayStart AND :dayEnd")
    fun getEntryForDay(dayStart: Calendar, dayEnd: Calendar): LogEntry?

    /**
     * Selects all the [LogEntry]'s that are of the type that matches the input type
     * ie, if we want to grab all the plastic activities
     *
     * //@param activityType the group id of the activity's type we want to get
     */
    //@Query("SELECT * FROM daily_green_activity_table INNER JOIN activities ON daily_green_activity_table.activity_id = activities.activity_id WHERE activities.activity_type = :activityType")
    //fun getAllActivityEntriesOfType(activityType: Int): List<GreenActivity>

    /**
     * Selects and returns all rows in the table
     * Sorted by the time it was entered in descending order (we might want to change this later)
     */
    @Query("SELECT * FROM log_entry_table ORDER BY logEntryId DESC")
    fun getAllEntries(): LiveData<List<LogEntry>>


}