package com.android.sarahmica.bamboo.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "activities")
data class Activity (

    @PrimaryKey
    @ColumnInfo(name = "activity_id")
    val activityId: Int,

    @ColumnInfo(name = "activity_name")
    val activityName: String,

    @ColumnInfo(name = "activity_type")
    val activityType: Int

)