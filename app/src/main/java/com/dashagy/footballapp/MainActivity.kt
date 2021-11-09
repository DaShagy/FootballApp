package com.dashagy.footballapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dashagy.data.database.AppDatabase
import com.dashagy.data.mapper.TeamMapperLocal
import com.dashagy.data.repositories.TeamsRepositoryImpl
import com.dashagy.data.service.services.TeamService
import com.dashagy.domain.entities.Country
import com.dashagy.domain.entities.Player
import com.dashagy.domain.entities.SquadPlayer
import com.dashagy.domain.entities.Team
import com.dashagy.domain.util.ResultWrapper
import com.dashagy.footballapp.databinding.ActivityMainBinding
import com.dashagy.footballapp.viewmodels.CountriesViewModel
import com.dashagy.footballapp.viewmodels.PlayersViewModel
import com.dashagy.footballapp.viewmodels.SquadPlayersViewModel
import com.dashagy.footballapp.viewmodels.TeamsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val teamsViewModel by viewModel<TeamsViewModel>()
    private val playersViewModel by viewModel<PlayersViewModel>()
    private val squadPlayersViewModel by viewModel<SquadPlayersViewModel>()
    private val countriesViewModel by viewModel<CountriesViewModel>()

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //teamsViewModel.teams.observe(this, ::updateTeams)
        //playersViewModel.players.observe(this, ::updatePlayers)
        //squadPlayersViewModel.players.observe(this, ::updateSquadPlayers)
        countriesViewModel.countries.observe(this, ::updateCountries)

        val button = binding.button.apply {
            setOnClickListener {
                //teamsViewModel.getTeamById(1)
                //teamsViewModel.getTeamByName("Belgium")
                //teamsViewModel.getTeamByLeague(39,2017, true)
                //teamsViewModel.getTeamByCountry("Argentina", true)
                //teamsViewModel.getTeamBySearch("che")

                //playersViewModel.getPlayerById(276, 2020,true)
                //playersViewModel.getPlayerByTeam(33, 2020, true)

                //squadPlayersViewModel.getSquadPlayerByTeam(455,true)

                countriesViewModel.getAllCountries()
            }
        }
    }

    private fun updateCountries(resultWrapper: ResultWrapper<List<Country>>) {
        when(resultWrapper){
            is ResultWrapper.Error -> binding.textView.text = "ERROR"
            is ResultWrapper.Success -> binding.textView.text = resultWrapper.data.toString()
        }
    }

    private fun updateSquadPlayers(resultWrapper: ResultWrapper<List<SquadPlayer>>?) {
        when(resultWrapper){
            is ResultWrapper.Error -> binding.textView.text = "ERROR"
            is ResultWrapper.Success -> binding.textView.text = resultWrapper.data.toString()
        }
    }

    /*private fun updatePlayers(resultWrapper: ResultWrapper<List<Player>>) {
        when(resultWrapper){
            is ResultWrapper.Error -> binding.textView.text = "ERROR"
            is ResultWrapper.Success -> binding.textView.text = resultWrapper.data.toString()
        }
    }*/

    /*private fun updateTeams(resultWrapper: ResultWrapper<List<Team>>) {
        when(resultWrapper){
            is ResultWrapper.Error -> binding.textView.text = "ERROR"
            is ResultWrapper.Success -> binding.textView.text = resultWrapper.data.toString()
        }
    }*/
}