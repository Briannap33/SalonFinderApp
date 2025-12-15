package com.setu.salonfinderapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.setu.salonfinderApp.databinding.CardSalonBinding
import com.setu.salonfinderapp.main.MainApp
import com.squareup.picasso.Picasso
import com.setu.salonfinderapp.models.SalonModel

interface SalonListener {
    fun onSalonClick(salonentry: SalonModel)
    fun onSalonDelete(salonentry: SalonModel)
}

class SalonAdapter(private var salonlist: MutableList<SalonModel>,
                   private val listener: SalonListener) :
    RecyclerView.Adapter<SalonAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardSalonBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val salonentry = salonlist[holder.adapterPosition]
        holder.bind(salonentry, listener)
    }

    override fun getItemCount(): Int = salonlist.size

    fun refresh(newList: List<SalonModel>) {
        salonlist.clear()
        salonlist.addAll(newList)
        notifyDataSetChanged()
    }
    class MainHolder(private val binding : CardSalonBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(salonentry: SalonModel, listener: SalonListener) {
            binding.salonName.text = salonentry.name
            binding.description.text = salonentry.description
            Picasso.get().load(salonentry.image).resize(200,200).into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onSalonClick(salonentry)
            }
            binding.btnDelete.setOnClickListener {
                listener.onSalonDelete(salonentry)
            }
        }
    }
}
