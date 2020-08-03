package com.android.sarahmica.bamboo.addActivities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.sarahmica.bamboo.database.ActivityDao

class AddDailyActivitiesViewModelFactory(
    private val dataSource: ActivityDao) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddDailyActivitiesViewModel::class.java)) {
            return AddDailyActivitiesViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}