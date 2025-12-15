package com.setu.salonfinderapp.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SalonModel(var id: Long = 0,
                          var name: String = "",
                          var description: String = "",
                          var image: Uri = Uri.EMPTY) : Parcelable


