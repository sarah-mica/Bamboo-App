package com.android.sarahmica.bamboo.addActivities

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.sarahmica.bamboo.database.BambooDatabaseDao
import kotlinx.coroutines.*

class AddDailyActivitiesViewModel(
    val database: BambooDatabaseDao) : ViewModel() {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _navigateToPandaLog = MutableLiveData<Boolean>()
    val navigateToPandaLog: LiveData<Boolean>
        get() = _navigateToPandaLog

    fun doneNavigating() {
        _navigateToPandaLog.value = false
    }

    //TODO: figure out how I want to add and keep track of activities in the DB
    fun onAddGreenActivity(activityId: Int) {
        uiScope.launch{
            withContext(Dispatchers.IO) {
                //TODO

            }
            _navigateToPandaLog.value = true
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}