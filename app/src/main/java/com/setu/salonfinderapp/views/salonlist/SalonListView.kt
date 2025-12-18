package com.setu.salonfinderapp.views.salonlist

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.widget.SearchView
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
        binding.recyclerView.adapter = adapter

        //     loadSalons()

        //    binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.btnRemoveAll.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Delete All Salons")
                .setMessage("Are you sure you want to delete all salons in your list?")
                .setPositiveButton("Delete") { _, _ ->
                    presenter.doDeleteAll()

                }
                .setNegativeButton("Cancel", null)
                .show()

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchItem = menu.findItem(R.id.item_search)
        val searchView = searchItem.actionView as SearchView

        searchView.queryHint = "Search salons..."

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.filter?.filter(query)
                return false
            }


            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter?.filter(newText)
                return true
            }
        })

        return true

        //   menuInflater.inflate(R.menu.menu_main, menu)
        // return super.onCreateOptionsMenu(menu)
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
        this.position = pos
        presenter.doEditSalon(salonEntry, pos)
    }


    fun onRefresh() {
        adapter.refresh(presenter.getSalonList())

    }

    fun onDelete(position: Int) {
        adapter.refresh(presenter.getSalonList())
    }
}