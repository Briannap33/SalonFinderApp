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
        binding = ActivitySalonBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp

        if (intent.hasExtra("salon_edit")) {
            salonEntry = intent.extras?.getParcelable("salon_edit")!!
            binding.salonName.setText(salonEntry.name)
            binding.description.setText(salonEntry.description)
        }
        binding.btnAdd.setOnClickListener {
            salonEntry.name = binding.salonName.text.toString()
            salonEntry.description = binding.description.text.toString()

            if (salonEntry.name.isNotEmpty()) {
                if (intent.hasExtra("salon_edit")) {
                    // Editing: directly update the actual model
                    app.salonList.update(salonEntry)
                } else {
                    // Adding: use copy() so a new ID is generated
                    app.salonList.create(salonEntry.copy())
                }
                setResult(RESULT_OK)
                finish()
            } else {
                Snackbar.make(it, "Please Enter a Name", Snackbar.LENGTH_LONG).show()
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

