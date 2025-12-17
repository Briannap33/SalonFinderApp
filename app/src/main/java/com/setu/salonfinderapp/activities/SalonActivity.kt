package com.setu.salonfinderapp.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import com.setu.salonfinderApp.R
import com.setu.salonfinderApp.databinding.ActivitySalonBinding
import com.setu.salonfinderapp.helpers.showImagePicker
import com.setu.salonfinderapp.main.MainApp
import com.setu.salonfinderapp.models.Location
import com.setu.salonfinderapp.models.SalonModel
import com.squareup.picasso.Picasso
import timber.log.Timber.Forest.i


class SalonActivity : AppCompatActivity() {
    var salonEntry = SalonModel()
   // var location = Location(52.245696, -7.139102, 15f)

    lateinit var app: MainApp
    private lateinit var binding: ActivitySalonBinding
    private lateinit var imageIntentLauncher : ActivityResultLauncher<PickVisualMediaRequest>

    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var edit = false
        binding = ActivitySalonBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)
        registerImagePickerCallback()
        registerMapCallback()



        app = application as MainApp

        i("Salon Activity started...")

        binding.salonLocation.setOnClickListener {
            val location = Location(52.245696, -7.139102, 15f)
            if (salonEntry.zoom != 0f) {
                location.lat =  salonEntry.lat
                location.lng = salonEntry.lng
                location.zoom = salonEntry.zoom
            }
            val launcherIntent = Intent(this, MapActivity::class.java)
                .putExtra("location", location)
            mapIntentLauncher.launch(launcherIntent)
        }



        if (intent.hasExtra("salon_edit")) {
            edit = true
            salonEntry = intent.extras?.getParcelable("salon_edit")!!
            binding.salonName.setText(salonEntry.name)
            binding.description.setText(salonEntry.description)
            binding.btnAdd.setText(R.string.save_salon)
            Picasso.get()
                .load(salonEntry.image)
                .into(binding.salonImage)
            if (salonEntry.image != Uri.EMPTY) {
                binding.chooseImage.setText(R.string.change_salon_image)

            }
        }
        binding.chooseImage.setOnClickListener {
            // showImagePicker(imageIntentLauncher,this)
            val request = PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly)
                .build()
            imageIntentLauncher.launch(request)
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
    private fun registerImagePickerCallback() {
        imageIntentLauncher = registerForActivityResult(
            ActivityResultContracts.PickVisualMedia()
        ) {
            try{
                contentResolver
                    .takePersistableUriPermission(it!!,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION )
                salonEntry.image = it
                i("IMG :: ${salonEntry.image}")
                Picasso.get()
                    .load(salonEntry.image)
                    .into(binding.salonImage)
            }
            catch(e:Exception){
                e.printStackTrace()
            }
        }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Location ${result.data.toString()}")
                            val location = result.data!!.extras?.getParcelable<Location>("location")!!
                            i("Location == $location")
                            salonEntry.lat = location.lat
                            salonEntry.lng = location.lng
                            salonEntry.zoom = location.zoom
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }



}


