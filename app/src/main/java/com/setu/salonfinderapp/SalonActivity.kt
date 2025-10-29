package com.setu.salonfinderapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.setu.salonFinderApp.R
import com.setu.salonFinderApp.databinding.ActivitySalonBinding
import timber.log.Timber
import timber.log.Timber.Forest.i

class SalonActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySalonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySalonBinding.inflate(layoutInflater)
        setContentView(binding.root)


        Timber.plant(Timber.DebugTree())
        Timber.i("Salon Activity started..")

        binding.btnAdd.setOnClickListener() {
            val salonName = binding.salonName.text.toString()
            if (salonName.isNotEmpty()) {
                i("add Button Pressed: $salonName")
            }
            else {
                Snackbar
                    .make(it,"Please Enter a salon name", Snackbar.LENGTH_LONG)
                    .show()
            }
        }



    }

}



