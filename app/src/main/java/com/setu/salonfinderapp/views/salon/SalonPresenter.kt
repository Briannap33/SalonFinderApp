package com.setu.salonfinderapp.views.salon

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.setu.salonfinderapp.views.salon.SalonView
import com.setu.salonfinderapp.main.MainApp
import com.setu.salonfinderapp.models.Location
import com.setu.salonfinderapp.models.SalonModel
import com.setu.salonfinderapp.views.editlocation.EditLocationView
import timber.log.Timber

class SalonPresenter(private val view: SalonView) {


    var salonEntry = SalonModel()
    var app: MainApp = view.application as MainApp
    private lateinit var imageIntentLauncher: ActivityResultLauncher<PickVisualMediaRequest>
    private lateinit var mapIntentLauncher: ActivityResultLauncher<Intent>
    var edit = false

    init {
        if (view.intent.hasExtra("salon_edit")) {
            edit = true
            //placemark = view.intent.getParcelableExtra("placemark_edit",PlacemarkModel::class.java)!!
            salonEntry = view.intent.extras?.getParcelable("salon_edit")!!
            view.showSalon(salonEntry)
        }
        registerImagePickerCallback()
        registerMapCallback()
    }

    fun doAddOrSave(name: String, description: String) {
        salonEntry.name = name
        salonEntry.description = description
        if (edit) {
            app.salonList.update(salonEntry)
        } else {
            app.salonList.create(salonEntry)
        }
        view.setResult(Activity.RESULT_OK)
        view.finish()
    }

    fun doCancel() {
        view.finish()
    }

    fun doDelete() {
        view.setResult(99)
        app.salonList.delete(salonEntry)
        view.finish()
    }

    fun doSelectImage() {
        //   showImagePicker(imageIntentLauncher,view)
        val request = PickVisualMediaRequest.Builder()
            .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly)
            .build()
        imageIntentLauncher.launch(request)
    }

    fun doSetLocation() {
        val location = Location(52.245696, -7.139102, 15f)
        if (salonEntry.zoom != 0f) {
            location.lat = salonEntry.lat
            location.lng = salonEntry.lng
            location.zoom = salonEntry.zoom
        }
        val launcherIntent = Intent(view, EditLocationView::class.java)
            .putExtra("location", location)
        mapIntentLauncher.launch(launcherIntent)
    }

    fun cacheSalon(name: String, description: String) {
        salonEntry.name = name
        salonEntry.description = description
    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher = view.registerForActivityResult(
            ActivityResultContracts.PickVisualMedia()
        ) {
            try {
                view.contentResolver
                    .takePersistableUriPermission(
                        it!!,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                salonEntry.image = it // The returned Uri
                Timber.Forest.i("IMG :: ${salonEntry.image}")
                view.updateImage(salonEntry.image)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.Forest.i("Got Location ${result.data.toString()}")
                            //val location = result.data!!.extras?.getParcelable("location",Location::class.java)!!
                            val location =
                                result.data!!.extras?.getParcelable<Location>("location")!!
                            Timber.Forest.i("Location == $location")
                            salonEntry.lat = location.lat
                            salonEntry.lng = location.lng
                            salonEntry.zoom = location.zoom
                        } // end of if
                    }

                    AppCompatActivity.RESULT_CANCELED -> {}
                    else -> {}
                }
            }
    }
}