package com.setu.salonfinderapp.main

import android.app.Application
import com.setu.salonfinderapp.models.SalonMemStore
import com.setu.salonfinderapp.models.SalonModel
import timber.log.Timber
import timber.log.Timber.Forest.i

class MainApp : Application() {

    val salonList = SalonMemStore()


    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Salon App started")
    }
}
