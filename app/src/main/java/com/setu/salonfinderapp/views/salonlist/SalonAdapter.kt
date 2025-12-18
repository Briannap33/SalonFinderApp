package com.setu.salonfinderapp.views.salonlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.setu.salonfinderApp.databinding.CardSalonBinding
import com.squareup.picasso.Picasso
import com.setu.salonfinderapp.models.SalonModel
import android.widget.Filter
import android.widget.Filterable

interface SalonListener {
    fun onSalonClick(salonEntry: SalonModel, position: Int)

}

class SalonAdapter(
    private var salonList: MutableList<SalonModel>,
    private val listener: SalonListener
) :
    RecyclerView.Adapter<SalonAdapter.MainHolder>(), Filterable {

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
    private var salonListFull: MutableList<SalonModel> = ArrayList(salonList)

    fun refresh(newList: List<SalonModel>) {
        salonList.clear()
        salonList.addAll(newList)
        salonListFull.clear()
        salonListFull.addAll(newList)

        notifyDataSetChanged()
    }

    override fun getFilter(): Filter? {
        return object : Filter() {

            override fun performFiltering(text: CharSequence?): FilterResults {
                val results = FilterResults()

                results.values = if (text.isNullOrEmpty()) {
                    salonListFull
                } else {
                    salonListFull.filter {
                        it.name.contains(text.toString(), true)
                    }
                }
                return results
            }

            override fun publishResults(text: CharSequence?, results: FilterResults?) {
                salonList.clear()
                salonList.addAll(results?.values as List<SalonModel>)
                notifyDataSetChanged()
            }
        }
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
