package com.dashagy.footballapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dashagy.domain.entities.Country
import com.dashagy.domain.entities.League
import com.dashagy.footballapp.databinding.RecyclerviewLeaguesBinding
import com.dashagy.footballapp.fragments.CountryListFragmentDirections
import com.dashagy.footballapp.fragments.LeagueListFragmentDirections

class LeaguesAdapter(
    private val listener: (League) -> Unit
) : RecyclerView.Adapter<LeaguesAdapter.LeagueViewHolder>() {

    private var dataset = mutableListOf<League>()
    private lateinit var context: Context

    fun updateDataset(dataset: List<League>){
        this.dataset = dataset as MutableList<League>
    }

    class LeagueViewHolder(
        val binding : RecyclerviewLeaguesBinding,
        val onItemClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueViewHolder {
        val binding = RecyclerviewLeaguesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LeagueViewHolder(binding){
            listener(dataset[it])
        }
    }

    override fun onBindViewHolder(holder: LeagueViewHolder, position: Int) {
        with (dataset[position]){
            holder.binding.leagueNameTextView.text = this.name
            holder.binding.leagueLogoImageView.load(
                this.logo
            )
        }
    }

    override fun getItemCount(): Int = dataset.size

}
