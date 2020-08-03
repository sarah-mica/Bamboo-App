package com.android.sarahmica.bamboo

import android.app.Application
import timber.log.Timber

class BambooApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}