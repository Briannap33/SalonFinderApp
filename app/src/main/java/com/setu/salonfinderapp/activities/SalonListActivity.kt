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
import com.setu.salonfinderapp.main.MainApp
import com.setu.salonfinderapp.models.SalonModel


class SalonListActivity : AppCompatActivity() {

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

        adapter = SalonAdapter(app.salonList.findAll().toMutableList(), app)
        binding.recyclerView.adapter?.notifyItemRangeChanged(0, app.salonList.findAll().size)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        binding.btnRemoveAll.setOnClickListener {
            adapter.removeAllItems()
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

    private val getResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                adapter.updateList(app.salonList.findAll())
            }
        }

    class SalonAdapter(private var salonList: MutableList<SalonModel>, private val app: MainApp) :
        RecyclerView.Adapter<SalonAdapter.MainHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
            val binding = CardSalonBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return MainHolder(binding) { position ->
                removeItem(position)
            }
        }

        fun updateList(newList: List<SalonModel>) {
            salonList.clear()
            salonList.addAll(newList)
            notifyDataSetChanged()
        }

        override fun onBindViewHolder(holder: MainHolder, position: Int) {
            val salonEntry = salonList[holder.adapterPosition]
            holder.bind(salonEntry)
            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, SalonActivity::class.java)
                intent.putExtra("salonIndex", holder.adapterPosition)
                intent.putExtra("salonName", salonEntry.name)
                intent.putExtra("salonDescription", salonEntry.description)
                (holder.itemView.context as Activity).startActivityForResult(intent, 100)
            }
        }

        override fun getItemCount(): Int = salonList.size

        fun removeItem(position: Int) {
            if (position < 0 || position >= salonList.size) return
            val salon = salonList[position]
            app.salonList.delete(salon)
            salonList.removeAt(position)
            notifyItemRemoved(position)


        }

        fun removeAllItems() {
            salonList.clear()
            notifyDataSetChanged()
        }

        class MainHolder(
            private val binding: CardSalonBinding,
            private val onDeleteClick: (Int) -> Unit
        ) :
            RecyclerView.ViewHolder(binding.root) {

            fun bind(salonEntry: SalonModel) {
                binding.salonName.text = salonEntry.name
                binding.description.text = salonEntry.description
                binding.btnDelete.setOnClickListener {
                    AlertDialog.Builder(binding.root.context)
                        .setTitle("Delete this Salon?")
                        .setMessage("Are you sure you want to delete this salon?")
                        .setPositiveButton("Delete") { _, _ ->
                            onDeleteClick(adapterPosition)
                        }
                        .setNegativeButton("Cancel", null)
                        .show()
                }
            }

        }
    }
}