package com.android.sarahmica.bamboo.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GreenActivityDao {
    //TODO: add suspend back?
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllActivities(greenActivities: List<GreenActivity>)

    @Query("SELECT * FROM green_activities ORDER BY activity_type")
    fun getAllActivities(): LiveData<List<GreenActivity>>

    @Query("SELECT * FROM green_activities WHERE activity_type = :type ORDER BY activity_name")
    fun getAllActivitiesByType(type: String): LiveData<List<GreenActivity>>

    @Query("SELECT COUNT(*) FROM green_activities")
    fun getNumActivities(): Int
}