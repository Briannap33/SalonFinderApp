package com.setu.salonfinderapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.setu.salonFinderApp.R
import timber.log.Timber

class SalonActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salon)

        Timber.plant(Timber.DebugTree())
        Timber.i("Salon Activity started..")
        }
    }



