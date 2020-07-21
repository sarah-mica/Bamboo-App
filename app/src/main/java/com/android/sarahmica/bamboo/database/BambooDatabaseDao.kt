package com.android.sarahmica.bamboo.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface BambooDatabaseDao {
    @Insert
    fun insert(activity: GreenActivity)

    @Update
    fun update(activity: GreenActivity)

    /**
     * Selects and returns the row that matches the supplied primary key
     */
    @Query("SELECT * FROM daily_green_activity_table WHERE activityId = :key")
    fun get(key: Long): GreenActivity

    /**
     * Deletes all values from the table.
     *
     * TODO: I don't think I'll actually need this in the final version, so remove this at some point
     */
    @Query("DELETE FROM daily_green_activity_table")
    fun clear()

}