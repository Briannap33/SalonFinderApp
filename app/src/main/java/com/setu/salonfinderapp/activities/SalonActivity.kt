package com.setu.salonfinderapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.setu.salonfinderApp.databinding.ActivitySalonBinding
import com.setu.salonfinderapp.main.MainApp
import com.setu.salonfinderapp.models.SalonModel
import timber.log.Timber.Forest.i



class SalonActivity : AppCompatActivity() {
    val salonEntry = SalonModel()
    lateinit var app: MainApp
    private lateinit var binding: ActivitySalonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySalonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp
        i("Salon Activity Started")
        binding.btnAdd.setOnClickListener() {
            salonEntry.name = binding.salonName.text.toString()
            salonEntry.description = binding.description.text.toString()
            if (salonEntry.name.isNotEmpty()) {
                app.salonList.add(salonEntry.copy())
                i("add Button Pressed: ${salonEntry}")
                for (i in app.salonList.indices) {
                    i("Placemark[$i]:${this.app.salonList[i]}")
                }
                setResult(RESULT_OK)
                finish()
            } else {
                Snackbar.make(it, "Please Enter a salon name", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}

