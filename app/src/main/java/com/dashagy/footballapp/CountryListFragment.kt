package com.dashagy.footballapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dashagy.domain.entities.Country
import com.dashagy.domain.util.ResultWrapper
import com.dashagy.footballapp.adapters.CountriesAdapter
import com.dashagy.footballapp.databinding.FragmentCountryListBinding
import com.dashagy.footballapp.viewmodels.CountriesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CountryListFragment : Fragment() {
    private var _binding: FragmentCountryListBinding? = null
    private val binding get() = _binding!!

    private val countriesAdapter = CountriesAdapter()

    private val countriesViewModel by viewModel<CountriesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentCountryListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.countriesRecyclerView
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = countriesAdapter

        countriesViewModel.countries.observe(viewLifecycleOwner, ::updateUI)
        countriesViewModel.getAllCountries()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateUI(resultWrapper: ResultWrapper<List<Country>>) {
        when (resultWrapper){
            is ResultWrapper.Error -> TODO()
            is ResultWrapper.Success -> updateList(resultWrapper.data)
        }
    }

    private fun updateList(dataset: List<Country>){
        countriesAdapter.updateDataset(dataset)
        countriesAdapter.notifyDataSetChanged()
    }
}