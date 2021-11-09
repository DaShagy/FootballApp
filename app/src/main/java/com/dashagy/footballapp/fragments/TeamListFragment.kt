package com.dashagy.footballapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dashagy.domain.entities.Team
import com.dashagy.domain.util.ResultWrapper
import com.dashagy.footballapp.adapters.TeamsAdapter
import com.dashagy.footballapp.databinding.FragmentTeamListBinding
import com.dashagy.footballapp.viewmodels.TeamsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class TeamListFragment : Fragment() {

    private var _binding: FragmentTeamListBinding? = null
    private val binding get() = _binding!!

    private val teamsAdapter = TeamsAdapter()

    private val teamsViewModel by viewModel<TeamsViewModel>()

    private var leagueId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            leagueId = it.getInt("leagueId")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentTeamListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.teamsRecyclerView
        recyclerView.adapter = teamsAdapter

        teamsViewModel.teams.observe(viewLifecycleOwner, ::updateUI)
        teamsViewModel.getTeamByLeague(leagueId, 2021)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateUI(resultWrapper: ResultWrapper<List<Team>>) {
        when (resultWrapper){
            is ResultWrapper.Error -> TODO()
            is ResultWrapper.Success -> {
                hideProgress()
                updateList(resultWrapper.data)
            }
            is ResultWrapper.Loading -> showProgress()
        }
    }

    private fun updateList(dataset: List<Team>){
        teamsAdapter.updateDataset(dataset)
        teamsAdapter.notifyDataSetChanged()
    }

    private fun showProgress() {
        binding.progress.visibility = View.VISIBLE
        binding.teamsRecyclerView.visibility = View.GONE
    }

    private fun hideProgress() {
        binding.progress.visibility = View.GONE
        binding.teamsRecyclerView.visibility = View.VISIBLE
    }
}