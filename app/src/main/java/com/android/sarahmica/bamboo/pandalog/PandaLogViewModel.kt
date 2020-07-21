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