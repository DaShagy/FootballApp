package com.dashagy.footballapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dashagy.domain.entities.Team
import com.dashagy.footballapp.databinding.RecyclerviewTeamsBinding

class TeamsAdapter(
    private val listener: (Team) -> Unit
) : RecyclerView.Adapter<TeamsAdapter.TeamViewHolder>() {

    private var dataset = mutableListOf<Team>()
    private lateinit var context: Context

    fun updateDataset(dataset: List<Team>){
        this.dataset = dataset as MutableList<Team>
    }

    class TeamViewHolder(
        val binding : RecyclerviewTeamsBinding,
        val onItemClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener {
                onItemClick(adapterPosition)
            }
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val binding = RecyclerviewTeamsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TeamViewHolder(binding){
            listener(dataset[it])
        }
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        with (dataset[position]){
            holder.binding.teamNameTextView.text = this.name
            holder.binding.teamLogoImageView.load(
                this.logo
            )
        }
    }

    override fun getItemCount(): Int = dataset.size
}