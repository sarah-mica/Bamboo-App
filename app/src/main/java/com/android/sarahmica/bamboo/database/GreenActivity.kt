package com.android.sarahmica.bamboo.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_green_activity_table")
data class GreenActivity (
    @PrimaryKey(autoGenerate = true)
    var activityId: Long = 0L,

    @ColumnInfo(name = "activity_name")
    var activityName: String,

    @ColumnInfo(name = "activity_type")
    var activityType: Int,

    @ColumnInfo(name = "day_completed")
    var dayCompleted: Long = System.currentTimeMillis()

)