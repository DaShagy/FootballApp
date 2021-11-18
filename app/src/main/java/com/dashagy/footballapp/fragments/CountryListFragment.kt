package com.dashagy.footballapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.dashagy.domain.entities.Country
import com.dashagy.domain.util.ResultWrapper
import com.dashagy.footballapp.R
import com.dashagy.footballapp.AppUtil
import com.dashagy.footballapp.adapters.CountriesAdapter
import com.dashagy.footballapp.databinding.FragmentCountryListBinding
import com.dashagy.footballapp.viewmodels.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CountryListFragment : Fragment() {
    private var _binding: FragmentCountryListBinding? = null
    private val binding get() = _binding!!

    private lateinit var countriesAdapter : CountriesAdapter

    private val mainViewModel by viewModel<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCountryListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        countriesAdapter = CountriesAdapter {
            navigation(it)
        }

        val recyclerView = binding.countriesRecyclerView
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = countriesAdapter

        mainViewModel.countries.observe(viewLifecycleOwner, ::updateUI)
        mainViewModel.getAllCountries()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateUI(resultWrapper: ResultWrapper<List<Country>>) {
        when (resultWrapper){
            is ResultWrapper.Error -> {
                hideProgress()
                AppUtil.showMessage(
                    requireActivity(),
                    resultWrapper.exception.message ?:
                        "Error desconocido"
                )
            }
            is ResultWrapper.Success -> {
                hideProgress()
                updateList(resultWrapper.data)
            }
            is ResultWrapper.Loading -> showProgress()
        }
    }

    private fun updateList(dataset: List<Country>){
        countriesAdapter.updateDataset(dataset)
        countriesAdapter.notifyDataSetChanged()
    }

    private fun showProgress() {
        binding.progress.visibility = View.VISIBLE
        binding.countriesRecyclerView.visibility = View.GONE
    }

    private fun hideProgress() {
        binding.progress.visibility = View.GONE
        binding.countriesRecyclerView.visibility = View.VISIBLE
    }

    private fun navigation(country: Country){
        val bundle = Bundle()
        bundle.putString("country", country.name)
        val leagueListFragment = LeagueListFragment()
        leagueListFragment.arguments = bundle

        parentFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayoutFragment, leagueListFragment)
            this.addToBackStack("leagueFragment")
            commit()
        }
    }
}