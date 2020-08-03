package com.android.sarahmica.bamboo.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ActivityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllActivities(activities: List<Activity>)

    @Query("SELECT * FROM activities ORDER BY activity_type")
    fun getAllActivities(): LiveData<List<Activity>>

    @Query("SELECT * FROM activities WHERE activity_type = :type ORDER BY activity_name")
    fun getAllActivitiesByType(type: Int): LiveData<List<Activity>>
}