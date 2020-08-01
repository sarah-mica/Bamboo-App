package com.android.sarahmica.bamboo.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "activities")
data class Activity (

    @PrimaryKey
    @ColumnInfo(name = "activity_id")
    val activityId: Int,

    // What is the name of the activity that we'll display to the user?
    @ColumnInfo(name = "activity_name")
    val activityName: String,

    // What group of activities does this belong to?
    @ColumnInfo(name = "activity_type")
    val activityType: Int,

    // How much many "panda points" is this activity worth?
    @ColumnInfo(name = "point_value")
    val pointValue: Int

)