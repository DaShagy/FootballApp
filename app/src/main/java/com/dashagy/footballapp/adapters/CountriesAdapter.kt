package com.dashagy.footballapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.load
import com.dashagy.domain.entities.Country
import com.dashagy.footballapp.databinding.RecyclerviewCountriesBinding
import com.dashagy.footballapp.fragments.CountryListFragmentDirections

class CountriesAdapter : RecyclerView.Adapter<CountriesAdapter.CountriesViewHolder>() {

    private var dataset = mutableListOf<Country>()
    private lateinit var context: Context

    fun updateDataset(dataset: List<Country>){
        this.dataset = dataset as MutableList<Country>
    }

    class CountriesViewHolder(
        val binding : RecyclerviewCountriesBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountriesViewHolder {
        val binding = RecyclerviewCountriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountriesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountriesViewHolder, position: Int) {
        with (dataset[position]){
            val imageLoader = ImageLoader.Builder(context)
                .componentRegistry {
                    add(SvgDecoder(context))
                }
                .build()

            holder.binding.countryNameTextView.text = this.name
            holder.binding.countryFlagImageView.load(this.flag?: "", imageLoader)

            holder.binding.countryNameTextView.setOnClickListener {
                val action = CountryListFragmentDirections.actionCountryListFragmentToLeagueListFragment(this.name)
                holder.binding.root.findNavController().navigate(action)
            }
        }
    }

    override fun getItemCount(): Int = dataset.size
}