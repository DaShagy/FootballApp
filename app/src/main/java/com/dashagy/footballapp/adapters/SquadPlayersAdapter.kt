package com.dashagy.footballapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dashagy.domain.entities.SquadPlayer
import com.dashagy.footballapp.databinding.RecyclerviewSquadPlayersBinding

class SquadPlayersAdapter : RecyclerView.Adapter<SquadPlayersAdapter.SquadPlayerViewHolder>() {

    private var dataset = mutableListOf<SquadPlayer>()
    private lateinit var context: Context

    fun updateDataset(dataset: List<SquadPlayer>){
        this.dataset = dataset as MutableList<SquadPlayer>
    }

    class SquadPlayerViewHolder(
        val binding : RecyclerviewSquadPlayersBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SquadPlayerViewHolder {
        val binding = RecyclerviewSquadPlayersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SquadPlayerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SquadPlayerViewHolder, position: Int) {
        with (dataset[position]){
            holder.binding.squadPlayerNameTextView.text = this.name
            holder.binding.squadPlayerPhotoImageView.load(
                this.photo
            )
        }
    }

    override fun getItemCount(): Int = dataset.size
}
