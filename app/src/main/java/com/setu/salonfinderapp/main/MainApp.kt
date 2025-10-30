package com.setu.salonfinderapp.main

import android.app.Application
import com.setu.salonfinderapp.models.SalonModel
import timber.log.Timber
import timber.log.Timber.Forest.i

class MainApp : Application() {

    val salonList = ArrayList<SalonModel>()


    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Salon started")
    }
}
