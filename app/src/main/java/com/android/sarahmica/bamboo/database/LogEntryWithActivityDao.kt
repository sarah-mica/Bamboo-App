package com.android.sarahmica.bamboo.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface LogEntryWithActivityDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entryWithActivities: ActivityLogEntry)

    @Update
    fun update(entryWithActivities: ActivityLogEntry)


    @Query("SELECT * FROM log_entry_table")
    fun getLogEntriesWithActivities(): LiveData<List<LogEntryWithActivities>>



}