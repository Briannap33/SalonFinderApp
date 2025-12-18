package com.setu.salonfinderapp.views.salon

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.setu.salonfinderApp.R
import com.setu.salonfinderApp.databinding.ActivitySalonBinding
import com.setu.salonfinderapp.models.SalonModel
import com.squareup.picasso.Picasso
import timber.log.Timber

class SalonView : AppCompatActivity() {

    lateinit var binding: ActivitySalonBinding
    private lateinit var presenter: SalonPresenter
    var salonEntry = SalonModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySalonBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        presenter = SalonPresenter(this)


        binding.chooseImage.setOnClickListener {
            presenter.doSelectImage()


        }
        //  presenter.cacheSalon(
        //       binding.salonName.text.toString(),
        //       binding.description.text.toString(),
        //       binding.ratingBar.rating,
        //        binding.review.text.toString()
        //    )
        //   presenter.doSelectImage()
        //  }

        binding.salonLocation.setOnClickListener {
            presenter.doSetLocation()
        }

        //   presenter.cacheSalon(
        //       binding.salonName.text.toString(),
        //      binding.description.text.toString(),
        //      binding.ratingBar.rating,
        //      binding.review.text.toString()
        //   )
        //  presenter.doSetLocation()
        // }

        binding.btnAdd.setOnClickListener {
            if (binding.salonName.text.toString().isEmpty()) {
                Snackbar.make(binding.root, R.string.enter_salon_name, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                presenter.doAddOrSave(
                    binding.salonName.text.toString(),
                    binding.description.text.toString(),
                    binding.ratingBar.rating,
                    binding.review.text.toString()
                )
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_salon, menu)
        val deleteMenu: MenuItem = menu.findItem(R.id.item_delete)
        deleteMenu.isVisible = presenter.edit
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_delete -> {
                presenter.doDelete()
            }

            R.id.item_cancel -> {
                presenter.doCancel()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun showSalon(salonEntry: SalonModel) {
        binding.salonName.setText(salonEntry.name)
        binding.description.setText(salonEntry.description)
        binding.ratingBar.rating = salonEntry.rating
        binding.review.setText(salonEntry.review)
        binding.btnAdd.setText(R.string.save_salon)
        Picasso.get()
            .load(salonEntry.image)
            .into(binding.salonImage)
        if (salonEntry.image != Uri.EMPTY) {
            binding.chooseImage.setText(R.string.change_salon_image)
        }

    }

    fun updateImage(image: Uri) {
        Timber.Forest.i("Image updated")
        Picasso.get()
            .load(image)
            .into(binding.salonImage)
        binding.chooseImage.setText(R.string.change_salon_image)
    }

}