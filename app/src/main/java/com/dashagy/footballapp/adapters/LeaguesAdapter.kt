package com.dashagy.footballapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dashagy.domain.entities.League
import com.dashagy.footballapp.databinding.RecyclerviewLeaguesBinding
import com.dashagy.footballapp.fragments.CountryListFragmentDirections
import com.dashagy.footballapp.fragments.LeagueListFragmentDirections

class LeaguesAdapter : RecyclerView.Adapter<LeaguesAdapter.LeagueViewHolder>() {

    private var dataset = mutableListOf<League>()
    private lateinit var context: Context

    fun updateDataset(dataset: List<League>){
        this.dataset = dataset as MutableList<League>
    }

    class LeagueViewHolder(
        val binding : RecyclerviewLeaguesBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueViewHolder {
        val binding = RecyclerviewLeaguesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LeagueViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LeagueViewHolder, position: Int) {
        with (dataset[position]){
            holder.binding.leagueNameTextView.text = this.name
            holder.binding.leagueLogoImageView.load(
                this.logo
            )

            holder.binding.leagueNameTextView.setOnClickListener {
                val action = LeagueListFragmentDirections.actionLeagueListFragmentToTeamListFragment(this.id, this.name)
                holder.binding.root.findNavController().navigate(action)
            }
        }
    }

    override fun getItemCount(): Int = dataset.size

}
