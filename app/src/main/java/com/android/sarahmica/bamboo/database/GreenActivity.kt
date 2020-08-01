package com.android.sarahmica.bamboo.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_green_activity_table")
data class GreenActivity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var entryId: Long = 0L,

    @ColumnInfo(name = "activity_id")
    var activityId: Int,

    @ColumnInfo(name = "day_completed")
    var dayCompleted: Long = System.currentTimeMillis()


)