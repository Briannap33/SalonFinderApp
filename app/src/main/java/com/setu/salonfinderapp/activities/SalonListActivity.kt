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
import com.setu.salonfinderApp.R
import com.setu.salonfinderApp.databinding.ActivitySalonListBinding
import com.setu.salonfinderApp.databinding.CardSalonBinding
import com.setu.salonfinderapp.main.MainApp
import com.setu.salonfinderapp.models.SalonModel

class SalonListActivity : AppCompatActivity() {

    lateinit var app: MainApp
    private lateinit var binding: ActivitySalonListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySalonListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = SalonAdapter(app.salonList)
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

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                binding.recyclerView.adapter?.notifyItemRangeChanged(0, app.salonList.size)
            }
        }

    class SalonAdapter(private var salonList: List<SalonModel>) :
        RecyclerView.Adapter<SalonAdapter.MainHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
            val binding = CardSalonBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return MainHolder(binding)
        }

        override fun onBindViewHolder(holder: MainHolder, position: Int) {
            val salonEntry = salonList[holder.adapterPosition]
            holder.bind(salonEntry)
        }

        override fun getItemCount(): Int = salonList.size

        class MainHolder(private val binding: CardSalonBinding) :
            RecyclerView.ViewHolder(binding.root) {

            fun bind(salonEntry: SalonModel) {
                binding.salonName.text = salonEntry.name
                binding.description.text = salonEntry.description
            }
        }
    }
}
