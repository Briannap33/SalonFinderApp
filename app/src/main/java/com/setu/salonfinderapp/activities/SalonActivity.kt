package com.setu.salonfinderapp.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.room.util.copy
import com.google.android.material.snackbar.Snackbar
import com.setu.salonFinderApp.databinding.ActivitySalonBinding
import com.setu.salonfinderapp.models.salonModel
import timber.log.Timber.Forest.i



class SalonActivity : AppCompatActivity() {
    val salonList = ArrayList<salonModel>()
    val salonEntry = salonModel()

    private lateinit var binding: ActivitySalonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySalonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAdd.setOnClickListener() {
            salonEntry.name = binding.salonName.text.toString()
            salonEntry.description = binding.description.text.toString()
            if (salonEntry.name.isNotEmpty()) {
                salonList.add(salonEntry.copy())
                i("add Button Pressed: ${salonEntry}")
                for (i in salonList.indices)
                { i("Salon[$i]:${this.salonList[i]}") }
            }
            else {
                Snackbar
                    .make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }



    }

}

