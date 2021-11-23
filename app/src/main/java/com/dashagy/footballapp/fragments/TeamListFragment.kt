package com.dashagy.footballapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dashagy.domain.entities.Team
import com.dashagy.domain.util.ResultWrapper
import com.dashagy.footballapp.util.AppUtil
import com.dashagy.footballapp.R
import com.dashagy.footballapp.adapters.TeamsAdapter
import com.dashagy.footballapp.databinding.FragmentTeamListBinding
import com.dashagy.footballapp.viewmodels.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class TeamListFragment : Fragment() {

    private var _binding: FragmentTeamListBinding? = null
    private val binding get() = _binding!!

    private lateinit var teamsAdapter : TeamsAdapter

    private val mainViewModel by viewModel<MainViewModel>()

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
    ): View {
        _binding = FragmentTeamListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        teamsAdapter = TeamsAdapter {
            navigate(it)
        }

        val recyclerView = binding.teamsRecyclerView
        recyclerView.adapter = teamsAdapter

        mainViewModel.teams.observe(viewLifecycleOwner, ::updateUI)
        mainViewModel.getTeamByLeague(leagueId, 2021)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateUI(resultWrapper: ResultWrapper<List<Team>>) {
        when (resultWrapper){
            is ResultWrapper.Error -> {
                hideProgress()
                AppUtil.showMessage(
                    requireActivity(),
                    resultWrapper.exception.message ?:
                    "Error desconocido"
                )
                parentFragmentManager.popBackStack()
            }
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

    private fun navigate(team: Team){
        val bundle = Bundle()
        bundle.putInt("teamId", team.id)
        val squadPlayerListFragment = SquadPlayerListFragment()
        squadPlayerListFragment.arguments = bundle

        parentFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayoutFragment, squadPlayerListFragment)
            parentFragmentManager.popBackStack()
            addToBackStack("squadPlayerFragment")
            commit()
        }
    }
}