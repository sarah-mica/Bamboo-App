package com.android.sarahmica.bamboo.addActivities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.sarahmica.bamboo.database.Activity
import com.android.sarahmica.bamboo.database.ActivityDao
import kotlinx.coroutines.*

class AddDailyActivitiesViewModel(
    val database: ActivityDao) : ViewModel() {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    // Live Data properties
    private val _navigateToPandaLog = MutableLiveData<Boolean>()
    //private val _activitiesList = MutableLiveData<List<Activity>>()


    val navigateToPandaLog: LiveData<Boolean>
        get() = _navigateToPandaLog

    //val activitiesList: LiveData<List<Activity>>
    //    get() = _activitiesList

    val activitiesList: LiveData<List<Activity>> = database.getAllActivities()

    fun doneNavigating() {
        _navigateToPandaLog.value = false
    }

    //TODO: figure out how I want to add and keep track of activities in the DB
    /*fun onAddGreenActivity(activityId: Int) {
        uiScope.launch{
            withContext(Dispatchers.IO) {
                //TODO

            }
            _navigateToPandaLog.value = true
        }
    }*/


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}