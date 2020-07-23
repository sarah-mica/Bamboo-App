package com.android.sarahmica.bamboo.pandalog

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.android.sarahmica.bamboo.database.BambooDatabaseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext

class PandaLogViewModel(
    val database: BambooDatabaseDao,
    application: Application) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    //TODO: what things do I need to show on the for the main view?

    // I need to calculate the "panda score" by pulling all the activities they've done for the day

    // I need a list of all the activities they've done today and I should display them i
    // in the recyclerView list

    //TODO: how do I get headings for the dates?

    init {}


    suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}