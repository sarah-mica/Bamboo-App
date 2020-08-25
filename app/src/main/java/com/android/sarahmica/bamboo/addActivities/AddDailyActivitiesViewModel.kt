package com.android.sarahmica.bamboo.addActivities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.sarahmica.bamboo.ActivityType
import com.android.sarahmica.bamboo.database.*
import kotlinx.coroutines.*
import timber.log.Timber

class AddDailyActivitiesViewModel(
    private val dayKey: Long,
    private val activityDao: GreenActivityDao,
    private val logEntryRepository: LogEntryRepository
) : ViewModel() {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    // Live Data properties
    private val _navigateToPandaLog = MutableLiveData<Boolean>()


    val navigateToPandaLog: LiveData<Boolean>
        get() = _navigateToPandaLog

    private val selectedActivities = MutableLiveData<List<GreenActivity>>()


    /*
    private val _wasteActivitiesList = MutableLiveData<List<GreenActivity>>()
    val wasteActivitiesList: LiveData<List<GreenActivity>>
        get() = _wasteActivitiesList

    private val _energyActivitiesList = MutableLiveData<List<GreenActivity>>()
    val energyActivitiesList: LiveData<List<GreenActivity>>
        get() = _energyActivitiesList

    private val _waterActivitiesList = MutableLiveData<List<GreenActivity>>()
    val waterActivitiesList: LiveData<List<GreenActivity>>
        get() = _waterActivitiesList

    private val _activismActivitiesList = MutableLiveData<List<GreenActivity>>()
    val activismActivitiesList: LiveData<List<GreenActivity>>
        get() = _activismActivitiesList

     */

    val wasteActivitiesList: LiveData<List<GreenActivity>> = activityDao.getAllActivitiesByType(ActivityType.WASTE)
    val energyActivitiesList: LiveData<List<GreenActivity>> = activityDao.getAllActivitiesByType(ActivityType.ENERGY)
    val waterActivitiesList: LiveData<List<GreenActivity>> = activityDao.getAllActivitiesByType(ActivityType.WATER)
    val activismActivitiesList: LiveData<List<GreenActivity>> = activityDao.getAllActivitiesByType(ActivityType.ACTIVISM)

    init {
        //getAllActivitiesFromDb()
        if (dayKey != -1L) {
            getPreSelectedActivities()
        }
    }

    /*
    private fun getAllActivitiesFromDb() {
        uiScope.launch {
            _wasteActivitiesList.value = getActivityListFromDatabase(ActivityType.WASTE)
            _energyActivitiesList.value = getActivityListFromDatabase(ActivityType.ENERGY)
            _waterActivitiesList.value = getActivityListFromDatabase(ActivityType.WATER)
            _activismActivitiesList.value = getActivityListFromDatabase(ActivityType.ACTIVISM)
        }
    }
    */

    private fun getPreSelectedActivities() {
        uiScope.launch {
            Timber.i("getting preselected activities")
            selectedActivities.value = getPreSelectedActivitiesFromDb()
        }
    }

    private suspend fun getPreSelectedActivitiesFromDb(): List<GreenActivity>? {
        return withContext(Dispatchers.IO) {
            Timber.i("dayKey: %s", dayKey)
            val day = logEntryRepository.getEntry(dayKey)
            if (day == null) {
                Timber.i("day is null")
            }
            if (day?.greenActivityList == null) {
                Timber.i("green activity list is null")
            }
            if (day?.greenActivityList!!.isEmpty()) {
                Timber.i("size of activity list is 0")
            }
            day?.greenActivityList!!.forEach {
                Timber.i("activity: %s", it.activityName)
            }
            day?.greenActivityList
        }
    }

    /*private suspend fun getActivityListFromDatabase(type: ActivityType): List<GreenActivity> {
        return withContext(Dispatchers.IO) {
            activityDao.getAllActivitiesByType(type)
        }
    }*/

    fun isChipSelected(activity: GreenActivity?): Boolean {
        if (activity != null) {
            Timber.i("trying to set check state for chip: %s", activity.activityName)
        }
        if (activity == null || selectedActivities.value == null) {
            Timber.i("selectedActivities is null for some reason")
            return false
        }

        val isChecked = selectedActivities.value!!.contains(activity)
        Timber.i("isChecked? %s", isChecked)
        return isChecked
    }

    fun doneNavigating() {
        _navigateToPandaLog.value = false
    }

    fun onAddGreenActivities(activityIdList: List<Int>) {

        //TODO: if the day is the same, perform an update instead
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