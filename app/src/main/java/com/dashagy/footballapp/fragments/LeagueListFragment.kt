package com.dashagy.footballapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.dashagy.domain.entities.League
import com.dashagy.domain.util.ResultWrapper
import com.dashagy.footballapp.adapters.LeaguesAdapter
import com.dashagy.footballapp.databinding.FragmentLeagueListBinding
import com.dashagy.footballapp.viewmodels.LeaguesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class LeagueListFragment : Fragment() {

    private var _binding: FragmentLeagueListBinding? = null
    private val binding get() = _binding!!

    private val leaguesAdapter = LeaguesAdapter()

    private val leaguesViewModel by viewModel<LeaguesViewModel>()

    private lateinit var country: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            country = it.getString("country").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentLeagueListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.leaguesRecyclerView
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = leaguesAdapter

        leaguesViewModel.leagues.observe(viewLifecycleOwner, ::updateUI)
        leaguesViewModel.getLeaguesByCountry(country, true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateUI(resultWrapper: ResultWrapper<List<League>>) {
        when (resultWrapper){
            is ResultWrapper.Error -> TODO()
            is ResultWrapper.Success -> {
                hideProgress()
                updateList(resultWrapper.data)
            }
            is ResultWrapper.Loading -> showProgress()
        }
    }

    private fun updateList(dataset: List<League>){
        leaguesAdapter.updateDataset(dataset)
        leaguesAdapter.notifyDataSetChanged()
    }
    private fun showProgress() {
        binding.progress.visibility = View.VISIBLE
        binding.leaguesRecyclerView.visibility = View.GONE
    }

    private fun hideProgress() {
        binding.progress.visibility = View.GONE
        binding.leaguesRecyclerView.visibility = View.VISIBLE
    }
}