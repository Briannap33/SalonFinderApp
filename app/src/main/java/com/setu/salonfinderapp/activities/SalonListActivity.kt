package com.setu.salonfinderapp.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.app.AlertDialog
import com.setu.salonfinderApp.R
import com.setu.salonfinderApp.databinding.ActivitySalonListBinding
import com.setu.salonfinderApp.databinding.CardSalonBinding
import com.setu.salonfinderapp.adapters.SalonAdapter
import com.setu.salonfinderapp.adapters.SalonListener
import com.setu.salonfinderapp.main.MainApp
import com.setu.salonfinderapp.models.SalonModel


class SalonListActivity : AppCompatActivity(), SalonListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivitySalonListBinding
    private lateinit var adapter: SalonAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySalonListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        adapter = SalonAdapter(app.salonList.findAll().toMutableList(), this)

        binding.recyclerView.adapter = adapter

        binding.btnRemoveAll.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Delete All Salons")
                .setMessage("Are you sure you want to delete all salons in your list?")
                .setPositiveButton("Delete") { _, _ ->
                    app.salonList.deleteAll()
                    adapter.refresh(app.salonList.findAll())
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, SalonActivity::class.java)
                getResult.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    val getResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                adapter.refresh(app.salonList.findAll())

            }
}
    override fun onSalonClick(salonentry: SalonModel) {
        val launcherIntent = Intent(this, SalonActivity::class.java)
        launcherIntent.putExtra("salon_edit", salonentry)
        getClickResult.launch(launcherIntent)
    }

    private val getClickResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == RESULT_OK) {
                (binding.recyclerView.adapter)?.
                notifyItemRangeChanged(0,app.salonList.findAll().size)
            }
        }
    override fun onSalonDelete(salonentry: SalonModel) {
        AlertDialog.Builder(this)
            .setTitle("Delete Salon")
            .setMessage("Are you sure you want to delete this salon?")
            .setPositiveButton("Delete") { _, _ ->
                app.salonList.delete(salonentry)
                adapter.refresh(app.salonList.findAll())
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}