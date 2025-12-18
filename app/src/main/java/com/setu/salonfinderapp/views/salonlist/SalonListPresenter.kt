package com.setu.salonfinderapp.views.salonlist

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.setu.salonfinderapp.main.MainApp
import com.setu.salonfinderapp.models.SalonModel
import com.setu.salonfinderapp.views.map.SalonMapsView
import com.setu.salonfinderapp.views.salon.SalonView

class SalonListPresenter(val view: SalonListView) {

    var app: MainApp
    private lateinit var refreshIntentLauncher: ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher: ActivityResultLauncher<Intent>
    private var position: Int = 0

    init {
        app = view.application as MainApp
        registerMapCallback()
        registerRefreshCallback()
    }

    fun getSalonList() = app.salonList.findAll()

    fun doAddSalon() {
        val launcherIntent = Intent(view, SalonView::class.java)
        refreshIntentLauncher.launch(launcherIntent)
    }

    fun doEditSalon(salonEntry: SalonModel, pos: Int) {
        val launcherIntent = Intent(view, SalonView::class.java)
        launcherIntent.putExtra("salon_edit", salonEntry)
        position = pos
        refreshIntentLauncher.launch(launcherIntent)
    }

    fun doShowSalonsMap() {
        val launcherIntent = Intent(view, SalonMapsView::class.java)
        mapIntentLauncher.launch(launcherIntent)
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            view.registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()
            ) {
                if (it.resultCode == Activity.RESULT_OK) view.onRefresh()
                else // Deleting
                    if (it.resultCode == 99) view.onDelete(position)
            }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { }
    }
}