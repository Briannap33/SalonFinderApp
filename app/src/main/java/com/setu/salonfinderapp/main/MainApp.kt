package com.setu.salonfinderapp.main

import android.app.Application
import timber.log.Timber
import timber.log.Timber.Forest.i

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Salon started")
    }
}
