package com.android.sarahmica.bamboo.database

import androidx.room.*
import java.util.*

/**
 * [LogEntry] represents all of the [GreenActivity]'s that a user has added to their
 * "panda log"
 */
@Entity(tableName = "log_entry_table")
data class LogEntry (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "day_completed")
    val dayCompleted: Calendar = Calendar.getInstance()

)