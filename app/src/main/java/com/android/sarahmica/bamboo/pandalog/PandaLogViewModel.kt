package com.android.sarahmica.bamboo.pandalog

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.sarahmica.bamboo.database.LogEntryDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import timber.log.Timber

class PandaLogViewModel(
    val database: LogEntryDao,
    application: Application) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    //TODO: what things do I need to show on the for the main view?

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

    // I need to calculate the "panda score" by pulling all the activities they've done for the day

    // I need a list of all the activities they've done today and I should display them i
    // in the recyclerView list

    //TODO: how do I get headings for the dates?

    init {
        initializeProgressBars()
        Timber.i("Hey I believe you just init.. right?");
    }

    private fun initializeProgressBars() {
        //TODO: these set values are just to visualize for now, set to 0 later
        _pandaScore.value = 100
        _plasticScore.value = 0
        _energyScore.value = 0
        _waterScore.value = 20
        _activismScore.value = 0
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