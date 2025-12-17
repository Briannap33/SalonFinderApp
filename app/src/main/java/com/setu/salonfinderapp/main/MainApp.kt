package com.setu.salonfinderapp.main

import android.app.Application
import com.setu.salonfinderapp.models.SalonJSONStore
import com.setu.salonfinderapp.models.SalonMemStore
import com.setu.salonfinderapp.models.SalonStore
import timber.log.Timber
import timber.log.Timber.Forest.i

class MainApp : Application() {

    lateinit var salonList: SalonStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        salonList = SalonJSONStore(applicationContext)
        i("Salon started")
    }
}
