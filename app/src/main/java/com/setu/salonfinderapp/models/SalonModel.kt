package com.setu.salonfinderapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlacemarkModel(var id: Long = 0,
                          var name: String = "",
                          var description: String = "") : Parcelable


