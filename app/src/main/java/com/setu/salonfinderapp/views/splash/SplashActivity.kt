package com.setu.salonfinderapp.views.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.setu.salonfinderApp.R
import com.setu.salonfinderapp.views.salonlist.SalonListView

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, SalonListView::class.java))
            finish()
        }, 5000)
    }
}

