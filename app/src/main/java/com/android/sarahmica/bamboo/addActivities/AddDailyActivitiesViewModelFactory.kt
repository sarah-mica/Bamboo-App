package com.android.sarahmica.bamboo.addActivities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.sarahmica.bamboo.database.GreenActivityDao
import com.android.sarahmica.bamboo.database.LogEntryRepository

class AddDailyActivitiesViewModelFactory(
    private val activityDao: GreenActivityDao,
    private val logEntryRepository: LogEntryRepository) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddDailyActivitiesViewModel::class.java)) {
            return AddDailyActivitiesViewModel(activityDao, logEntryRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}