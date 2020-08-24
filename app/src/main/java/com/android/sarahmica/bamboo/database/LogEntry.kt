package com.android.sarahmica.bamboo.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

/**
 * [LogEntry] represents all of the [GreenActivity]'s that a user has added to their
 * "panda log"
 */
@Entity(tableName = "log_entry_table")
data class LogEntry (
    @PrimaryKey(autoGenerate = true)
    var logEntryId: Long = 0L,

    @ColumnInfo(name = "day_completed")
    val dayCompleted: Calendar = Calendar.getInstance()

) {
    fun dateToString(): String {
        // TODO: convert to proper locale
        val format = SimpleDateFormat("EEEE, MMMM d, yyyy")
        return format.format(this.dayCompleted.time)
    }

}