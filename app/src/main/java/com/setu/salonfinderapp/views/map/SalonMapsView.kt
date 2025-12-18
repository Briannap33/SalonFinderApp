package com.setu.salonfinderapp.views.map

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.setu.salonfinderApp.databinding.ActivitySalonMapsBinding
import com.setu.salonfinderApp.databinding.ContentSalonMapsBinding
import com.setu.salonfinderapp.main.MainApp
import com.setu.salonfinderapp.models.SalonModel
import com.squareup.picasso.Picasso

class SalonMapsView : AppCompatActivity(), GoogleMap.OnMarkerClickListener {

    private lateinit var binding: ActivitySalonMapsBinding
    private lateinit var contentBinding: ContentSalonMapsBinding
    lateinit var map: GoogleMap

    lateinit var app: MainApp
    lateinit var presenter: SalonMapPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = application as MainApp

        binding = ActivitySalonMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        presenter = SalonMapPresenter(this)

        contentBinding = ContentSalonMapsBinding.bind(binding.root)
        contentBinding.mapView.onCreate(savedInstanceState)
        contentBinding.mapView.getMapAsync {
            map = it
            configureMap()
        }
    }

    fun showSalon(salonEntry: SalonModel) {
        contentBinding.currentTitle.text = salonEntry.name
        contentBinding.currentDescription.text = salonEntry.description
        Picasso.get()
            .load(salonEntry.image)
            .into(contentBinding.currentImage)
    }

    override fun onDestroy() {
        super.onDestroy()
        contentBinding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        contentBinding.mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        contentBinding.mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        contentBinding.mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        contentBinding.mapView.onSaveInstanceState(outState)
    }

    private fun configureMap() {
        map.uiSettings.isZoomControlsEnabled = true
        app.salonList.findAll().forEach {
            val loc = LatLng(it.lat, it.lng)
            val options = MarkerOptions().title(it.name).position(loc)
            map.addMarker(options)?.tag = it.id
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.zoom))
            map.setOnMarkerClickListener(this)


        }


    }

    override fun onMarkerClick(p0: Marker): Boolean {
        //val salonEntry = marker.tag as SalonModel
        val tag = p0.tag as Long
        val salonEntry = app.salonList.findById(tag)
        contentBinding.currentTitle.text = salonEntry!!.name
        contentBinding.currentDescription.text = salonEntry.description
        Picasso.get().load(salonEntry.image).into(contentBinding.currentImage)
        return false
    }
}