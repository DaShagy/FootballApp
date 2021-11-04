package com.dashagy.footballapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dashagy.data.database.AppDatabase
import com.dashagy.data.mapper.TeamMapperLocal
import com.dashagy.data.repositories.TeamsRepositoryImpl
import com.dashagy.data.service.services.TeamService
import com.dashagy.domain.entities.Team
import com.dashagy.domain.util.ResultWrapper
import com.dashagy.footballapp.databinding.ActivityMainBinding
import com.dashagy.footballapp.viewmodels.TeamsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val teamsViewModel by viewModel<TeamsViewModel>()

    private val database by lazy { AppDatabase.getDatabase(this) }

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        teamsViewModel.teams.observe(this, ::updateTeams)

        val button = binding.button.apply {
            setOnClickListener {
                //teamsViewModel.getTeamById(1)
                //teamsViewModel.getTeamByName("Belgium")
                //teamsViewModel.getTeamByLeague(39,2017, true)
                //teamsViewModel.getTeamByCountry("Argentina", true)
                teamsViewModel.getTeamBySearch("che")
            }
        }
    }

    private fun updateTeams(resultWrapper: ResultWrapper<List<Team>>) {
        when(resultWrapper){
            is ResultWrapper.Error -> binding.textView.text = "ERROR"
            is ResultWrapper.Success -> binding.textView.text = resultWrapper.data.toString()
        }
    }
}