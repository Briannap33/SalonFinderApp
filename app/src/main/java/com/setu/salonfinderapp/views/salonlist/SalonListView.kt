package com.setu.salonfinderapp.views.salonlist

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.setu.salonfinderApp.R
import com.setu.salonfinderApp.databinding.ActivitySalonListBinding
import com.setu.salonfinderapp.main.MainApp
import com.setu.salonfinderapp.models.SalonModel

class SalonListView : AppCompatActivity(), SalonListener {

    lateinit var app: MainApp
    private var position: Int = 0
    lateinit var presenter: SalonListPresenter

    private lateinit var binding: ActivitySalonListBinding
    private lateinit var adapter: SalonAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySalonListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp
        presenter = SalonListPresenter(this)


        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        adapter = SalonAdapter(
            app.salonList.findAll().toMutableList(),
            this
        )
        loadSalons()

        //    binding.recyclerView.layoutManager = LinearLayoutManager(this)

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
                presenter.doAddSalon()
            }

            R.id.item_map -> {
                presenter.doShowSalonsMap()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onSalonClick(salonEntry: SalonModel, pos: Int) {
        this.position = position
        presenter.doEditSalon(salonEntry, this.position)
    }

    private fun loadSalons() {
        binding.recyclerView.adapter =
            SalonAdapter(presenter.getSalonList() as MutableList<SalonModel>, this)
        onRefresh()
    }

    fun onRefresh() {
        binding.recyclerView.adapter?.notifyItemRangeChanged(0, presenter.getSalonList().size)
    }

    fun onDelete(position: Int) {
        binding.recyclerView.adapter?.notifyItemRemoved(position)
    }
}