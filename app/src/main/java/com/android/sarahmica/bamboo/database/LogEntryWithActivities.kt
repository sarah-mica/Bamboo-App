package com.android.sarahmica.bamboo.database

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

/**
 * Use this class to get log entries with their list of associated activityId's (not objects)
 */
data class LogEntryWithActivities (
    @Embedded
    val logEntry: LogEntry,

    @Relation(
        parentColumn = "logEntryId",
        entity = GreenActivity::class,
        entityColumn = "activityId",
        associateBy = Junction(ActivityLogEntry::class)
    )
    val greenActivityList: List<GreenActivity>

)