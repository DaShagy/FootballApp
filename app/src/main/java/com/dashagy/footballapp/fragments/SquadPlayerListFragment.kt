package com.dashagy.footballapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dashagy.domain.entities.SquadPlayer
import com.dashagy.domain.util.ResultWrapper
import com.dashagy.footballapp.adapters.SquadPlayersAdapter
import com.dashagy.footballapp.databinding.FragmentPlayerListBinding
import com.dashagy.footballapp.viewmodels.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SquadPlayerListFragment : Fragment() {

    private var _binding: FragmentPlayerListBinding? = null
    private val binding get() = _binding!!

    private val playersAdapter = SquadPlayersAdapter()

    private val mainViewModel by viewModel<MainViewModel>()

    private var teamId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            teamId = it.getInt("teamId")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPlayerListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.playersRecyclerView
        recyclerView.adapter = playersAdapter

        mainViewModel.squadPlayers.observe(viewLifecycleOwner, ::updateUI)
        mainViewModel.getPlayerByTeam(teamId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateUI(resultWrapper: ResultWrapper<List<SquadPlayer>>) {
        when (resultWrapper){
            is ResultWrapper.Error -> TODO()
            is ResultWrapper.Success -> {
                hideProgress()
                updateList(resultWrapper.data)
            }
            is ResultWrapper.Loading -> showProgress()
        }
    }

    private fun updateList(dataset: List<SquadPlayer>){
        playersAdapter.updateDataset(dataset)
        playersAdapter.notifyDataSetChanged()
    }

    private fun showProgress() {
        binding.progress.visibility = View.VISIBLE
        binding.playersRecyclerView.visibility = View.GONE
    }

    private fun hideProgress() {
        binding.progress.visibility = View.GONE
        binding.playersRecyclerView.visibility = View.VISIBLE
    }
}