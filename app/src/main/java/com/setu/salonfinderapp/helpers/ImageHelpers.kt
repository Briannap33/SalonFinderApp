package com.setu.salonfinderapp.helpers

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.setu.salonfinderApp.R

fun showImagePicker(context: Context, launcher: ActivityResultLauncher<Intent>) {
    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
    intent.type = "image/*"

    launcher.launch(
        Intent.createChooser(
            intent,
            context.getString(R.string.select_salon_image)
        )
    )
}