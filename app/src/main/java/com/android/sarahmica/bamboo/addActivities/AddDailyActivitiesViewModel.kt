package com.android.sarahmica.bamboo.addActivities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.sarahmica.bamboo.database.*
import kotlinx.coroutines.*
import timber.log.Timber

class AddDailyActivitiesViewModel(
    private val activityDao: GreenActivityDao,
    private val logEntryDao: LogEntryDao,
    private val logEntryWithActivityDao: LogEntryWithActivityDao
) : ViewModel() {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    // Live Data properties
    private val _navigateToPandaLog = MutableLiveData<Boolean>()
    //private val _activitiesList = MutableLiveData<List<Activity>>()


    val navigateToPandaLog: LiveData<Boolean>
        get() = _navigateToPandaLog

    //val activitiesList: LiveData<List<Activity>>
    //    get() = _activitiesList

    val activitiesList: LiveData<List<GreenActivity>> = activityDao.getAllActivities()


    fun doneNavigating() {
        _navigateToPandaLog.value = false
    }

    fun onAddGreenActivities(activityIdList: List<Int>) {
        uiScope.launch{
            withContext(Dispatchers.IO) {
                val newEntry = LogEntry()
                logEntryDao.insert(newEntry)
                Timber.i("newEntry id: " + newEntry.id)
                activityIdList.forEach {activityId ->
                    val newEntryWithActivities = ActivityLogEntry(activityId, newEntry.id)
                    logEntryWithActivityDao.insert(newEntryWithActivities)
                }

            }
            _navigateToPandaLog.value = true
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}