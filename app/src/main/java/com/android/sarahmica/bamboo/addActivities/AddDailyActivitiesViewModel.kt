package com.android.sarahmica.bamboo.addActivities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.sarahmica.bamboo.ActivityType
import com.android.sarahmica.bamboo.database.*
import kotlinx.coroutines.*
import timber.log.Timber

class AddDailyActivitiesViewModel(
    private val activityDao: GreenActivityDao,
    private val logEntryRepository: LogEntryRepository
) : ViewModel() {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    // Live Data properties
    private val _navigateToPandaLog = MutableLiveData<Boolean>()


    val navigateToPandaLog: LiveData<Boolean>
        get() = _navigateToPandaLog

    val wasteActivitiesList: LiveData<List<GreenActivity>> = activityDao.getAllActivitiesByType(ActivityType.WASTE)
    val energyActivitiesList: LiveData<List<GreenActivity>> = activityDao.getAllActivitiesByType(ActivityType.ENERGY)
    val waterActivitiesList: LiveData<List<GreenActivity>> = activityDao.getAllActivitiesByType(ActivityType.WATER)
    val activismActivitiesList: LiveData<List<GreenActivity>> = activityDao.getAllActivitiesByType(ActivityType.ACTIVISM)

    fun doneNavigating() {
        _navigateToPandaLog.value = false
    }

    fun onAddGreenActivities(activityIdList: List<Int>) {
        uiScope.launch{
            withContext(Dispatchers.IO) {
                logEntryRepository.insertLogEntry(activityIdList)
            }
            _navigateToPandaLog.value = true
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}