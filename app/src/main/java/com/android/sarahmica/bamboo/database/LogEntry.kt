package com.android.sarahmica.bamboo.database

import androidx.room.*

/**
 * [LogEntry] represents all of the [GreenActivity]'s that a user has added to their
 * "pandalog"
 */
@Entity(tableName = "log_entry_table")
data class LogEntry (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "day_completed")
    val dayCompleted: Long = System.currentTimeMillis()

)