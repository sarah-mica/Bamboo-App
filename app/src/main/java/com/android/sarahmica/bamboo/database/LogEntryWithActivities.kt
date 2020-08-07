package com.android.sarahmica.bamboo.database

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

/**
 * Use this class to get log entries with their list of associated activityId's (not objects)
 */
data class LogEntryWithActivities (
    @Embedded
    val logEntry:LogEntry,

    @Relation(
        parentColumn = "id",
        entity = GreenActivity::class,
        entityColumn = "id",
        associateBy = Junction(
            value = ActivityLogEntry::class,
            parentColumn = "logEntryId",
            entityColumn = "greenActivityId"
        )
    )
    val greenActivityList: List<GreenActivity>

)