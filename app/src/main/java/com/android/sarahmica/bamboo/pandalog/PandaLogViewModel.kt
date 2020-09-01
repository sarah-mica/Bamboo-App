package com.android.sarahmica.bamboo.pandalog

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.sarahmica.bamboo.ActivityType
import com.android.sarahmica.bamboo.database.LogEntryDao
import com.android.sarahmica.bamboo.database.LogEntryRepository
import com.android.sarahmica.bamboo.database.LogEntryWithActivities
import com.android.sarahmica.bamboo.database.LogEntryWithActivityDao
import kotlinx.coroutines.*
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class PandaLogViewModel(
    private val logEntryRepository: LogEntryRepository,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val logEntries: LiveData<List<LogEntryWithActivities>> = logEntryRepository.getAllLogEntries()

    private val _today = MutableLiveData<LogEntryWithActivities?>()
    val today: LiveData<LogEntryWithActivities?>
        get() = _today

    private val _navigateToAddScreen = MutableLiveData<Boolean>()
    val navigateToAddScreen: LiveData<Boolean>
        get() = _navigateToAddScreen

    private val _pandaScore = MutableLiveData<Int>()
    val pandaScore: LiveData<Int>
        get() = _pandaScore

    private val _plasticScore = MutableLiveData<Int>()
    val plasticScore: LiveData<Int>
        get() = _plasticScore

    private val _energyScore = MutableLiveData<Int>()
    val energyScore: LiveData<Int>
        get() = _energyScore

    private val _waterScore = MutableLiveData<Int>()
    val waterScore: LiveData<Int>
        get() = _waterScore

    private val _activismScore = MutableLiveData<Int>()
    val activismScore: LiveData<Int>
        get() = _activismScore

    init {
        _pandaScore.value = 0
        initializeToday()
    }

    private fun initializeToday() {
        uiScope.launch {
            _today.value = getTodayFromDatabase()
            _plasticScore.value = calculateScoreForCategory(ActivityType.WASTE)
            _energyScore.value = calculateScoreForCategory(ActivityType.ENERGY)
            _waterScore.value = calculateScoreForCategory(ActivityType.WATER)
            _activismScore.value = calculateScoreForCategory(ActivityType.ACTIVISM)
            _pandaScore.value = _plasticScore.value!! + _energyScore.value!! + _waterScore.value!! + _activismScore.value!!
        }
    }

    private suspend fun getTodayFromDatabase(): LogEntryWithActivities? {
        return withContext(Dispatchers.IO) {
            logEntryRepository.getToday()
        }
    }

    /*
     * Simply add up the score for each activity to calculate the plastic Score
     */
    private fun calculateScoreForCategory(type: ActivityType): Int {
        var total = 0

        if (_today.value == null) {
            Timber.i("today is null")
        }

        _today.value?.greenActivityList?.forEach { activity ->
            if (activity.activityType == type) {
                total += activity.pointValue
            }
        }
        Timber.i("Point Value = %s", total)
        return total
    }

    fun onFabClicked() {
        _navigateToAddScreen.value = true
    }

    fun onNavigatedToAddScreen() {
        _navigateToAddScreen.value = false
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}