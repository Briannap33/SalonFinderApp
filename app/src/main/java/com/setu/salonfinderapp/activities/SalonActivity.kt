package com.setu.salonfinderapp.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.setu.salonfinderApp.R
import com.setu.salonfinderApp.databinding.ActivitySalonBinding
import com.setu.salonfinderapp.main.MainApp
import com.setu.salonfinderapp.models.SalonModel
import timber.log.Timber.Forest.i


class SalonActivity : AppCompatActivity() {
    var salonEntry = SalonModel()
    lateinit var app: MainApp
    private lateinit var binding: ActivitySalonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var edit = false
        binding = ActivitySalonBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp

        i("Salon Activity started...")

        if (intent.hasExtra("salon_edit")) {
            edit = true
            salonEntry = intent.extras?.getParcelable("salon_edit")!!
            binding.salonName.setText(salonEntry.name)
            binding.description.setText(salonEntry.description)
            binding.btnAdd.setText(R.string.save_salon)

        }
        binding.btnAdd.setOnClickListener {
            salonEntry.name = binding.salonName.text.toString()
            salonEntry.description = binding.description.text.toString()

            if (salonEntry.name.isEmpty()) {
                Snackbar.make(it, getString(R.string.app_name), Snackbar.LENGTH_LONG)
                    .show()
            } else {
                if (edit) {
                    app.salonList.update(salonEntry.copy())
                } else {
                    app.salonList.create(salonEntry.copy())
                }
                i("add Button Pressed: $salonEntry")
                setResult(RESULT_OK)
                finish()
            }
            binding.chooseImage.setOnClickListener {
                i("Select Salon Image")
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_salon, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}


