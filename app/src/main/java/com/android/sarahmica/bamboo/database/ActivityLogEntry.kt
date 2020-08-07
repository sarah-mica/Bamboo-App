package com.android.sarahmica.bamboo.database

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * This entity represents the relationship between a [LogEntry] and a [GreenActivity]
 * It is a many-to-many relationship, so a given log entry can have multiple activities associated with it,
 * and any given "green activity" can be be associated with multiple log entries!
 */
@Entity
data class ActivityLogEntry (
    @PrimaryKey(autoGenerate = true)
    val id: Long,

    val greenActivityId: Long,

    val logEntryId: Long
)