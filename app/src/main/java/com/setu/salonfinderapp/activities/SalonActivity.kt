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
    val salons = ArrayList<salonModel>()
    var salon = salonModel()

    private lateinit var binding: ActivitySalonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySalonBinding.inflate(layoutInflater)
        setContentView(binding.root)


        Timber.Forest.plant(Timber.DebugTree())
        i("Salon Activity started..")

        binding.btnAdd.setOnClickListener() {
            salon.name = binding.salonName.text.toString()
            salon.description = binding.description.text.toString()
            if (salon.name.isNotEmpty()) {
                salons.add(salon)
                i("add Button Pressed: ${salon}")
                for (i in salons.indices)
                { i("Salon[$i]:${this.salons[i]}") }
            }
            else {
                Snackbar
                    .make(it,"Please Enter a salon name", Snackbar.LENGTH_LONG)
                    .show()
            }
        }



    }

}