package com.android.sarahmica.bamboo.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BambooDatabaseDao {
    @Insert
    fun insert(activity: GreenActivity)

    @Update
    fun update(activity: GreenActivity)

    /**
     * Selects and returns the row that matches the supplied primary key
     *
     * @param entryId the id value for the entry we want to look up
     */
    @Query("SELECT * FROM daily_green_activity_table WHERE id = :entryId")
    fun get(entryId: Long): GreenActivity?

    /**
     * Selects all the [GreenActivity]'s that are of the type that matches the input type
     * ie, if we want to grab all the plastic activities
     *
     * //@param activityType the group id of the activity's type we want to get
     */
    //@Query("SELECT * FROM daily_green_activity_table INNER JOIN activities ON daily_green_activity_table.activity_id = activities.activity_id WHERE activities.activity_type = :activityType")
    //fun getAllActivityEntriesOfType(activityType: Int): List<GreenActivity>

    /**
     * Selects and returns all rows in the table
     * Sorted by the time it was entered in descending order (we might want to change this later)
     */
    @Query("SELECT * FROM daily_green_activity_table ORDER BY id DESC")
    fun getAllActivities(): LiveData<List<GreenActivity>>

    /**
     * Deletes all values from the table.
     *
     * TODO: I don't think I'll actually need this in the final version, so remove this at some point
     */
    @Query("DELETE FROM daily_green_activity_table")
    fun clear()

}