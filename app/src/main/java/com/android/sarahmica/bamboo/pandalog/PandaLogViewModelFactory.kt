package com.android.sarahmica.bamboo.pandalog

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.sarahmica.bamboo.database.LogEntryDao
import com.android.sarahmica.bamboo.database.LogEntryWithActivityDao
import java.lang.IllegalArgumentException

/**
 * This is pretty much boiler plate code for a ViewModel factory
 *
 * Provides the BambooDatabaseDao and context to the ViewModel
 */
class PandaLogViewModelFactory(
    private val logEntryDao: LogEntryDao,
    private val logEntryActivityDao: LogEntryWithActivityDao,
    private val application: Application) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(PandaLogViewModel::class.java)) {
            return PandaLogViewModel(logEntryDao, logEntryActivityDao, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}