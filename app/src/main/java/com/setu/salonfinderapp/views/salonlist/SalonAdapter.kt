package com.setu.salonfinderapp.views.salonlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.setu.salonfinderApp.databinding.CardSalonBinding
import com.squareup.picasso.Picasso
import com.setu.salonfinderapp.models.SalonModel

interface SalonListener {
    fun onSalonClick(salonEntry: SalonModel, position: Int)

}

class SalonAdapter(
    private var salonList: MutableList<SalonModel>,
    private val listener: SalonListener
) :
    RecyclerView.Adapter<SalonAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardSalonBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val salonEntry = salonList[holder.adapterPosition]
        holder.bind(salonEntry, listener)
    }

    override fun getItemCount(): Int = salonList.size
    fun refresh(newList: List<SalonModel>) {
        salonList.clear()
        salonList.addAll(newList)
        notifyDataSetChanged()
    }

    class MainHolder(private val binding: CardSalonBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(salonEntry: SalonModel, listener: SalonListener) {
            binding.salonName.text = salonEntry.name
            binding.description.text = salonEntry.description
            Picasso.get().load(salonEntry.image).resize(200, 200).into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onSalonClick(salonEntry, adapterPosition) }

        }

    }
}
