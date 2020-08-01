package com.android.sarahmica.bamboo.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ActivityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllActivities(activites: List<Activity>)

    @Query("SELECT * FROM activities")
    fun getAllActivities(): List<Activity>
}