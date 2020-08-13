package com.android.sarahmica.bamboo.addActivities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.sarahmica.bamboo.database.GreenActivityDao
import com.android.sarahmica.bamboo.database.LogEntryDao
import com.android.sarahmica.bamboo.database.LogEntryWithActivityDao

class AddDailyActivitiesViewModelFactory(
    private val activityDao: GreenActivityDao,
    private val logEntryDao: LogEntryDao,
    private val logEntryActivityDao: LogEntryWithActivityDao) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddDailyActivitiesViewModel::class.java)) {
            return AddDailyActivitiesViewModel(activityDao, logEntryDao, logEntryActivityDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}