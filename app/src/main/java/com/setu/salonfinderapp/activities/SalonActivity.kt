package com.setu.salonfinderapp

import android.os.Bundle
import android.util.Log.i
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.setu.salonFinderApp.databinding.ActivitySalonBinding
import com.setu.salonfinderapp.models.salonModel
import timber.log.Timber
import timber.log.Timber.Forest.i

class SalonActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySalonBinding
    var salon = salonModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySalonBinding.inflate(layoutInflater)
        setContentView(binding.root)


        Timber.Forest.plant(Timber.DebugTree())
        Timber.Forest.i("Salon Activity started..")

        binding.btnAdd.setOnClickListener() {
            salon.name = binding.salonName.text.toString()
            if (salon.name.isNotEmpty()) {
                i("add Button Pressed: $salon.name")
            }
            else {
                Snackbar
                    .make(it,"Please Enter a salon name", Snackbar.LENGTH_LONG)
                    .show()
            }
        }



    }

}