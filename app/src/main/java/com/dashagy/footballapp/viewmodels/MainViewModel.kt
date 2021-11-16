package com.dashagy.footballapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dashagy.domain.entities.Country
import com.dashagy.domain.entities.League
import com.dashagy.domain.entities.SquadPlayer
import com.dashagy.domain.entities.Team
import com.dashagy.domain.usecases.GetAllCountriesUseCase
import com.dashagy.domain.usecases.GetLeaguesByCountryUseCase
import com.dashagy.domain.usecases.UseCase
import com.dashagy.domain.usecases.player_usecases.GetSquadPlayersByTeamUseCase
import com.dashagy.domain.usecases.team_usecases.GetTeamByLeagueUseCase
import com.dashagy.domain.util.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val getAllCountriesUseCase: GetAllCountriesUseCase,
    private val getLeaguesByCountryUseCase: GetLeaguesByCountryUseCase,
    private val getTeamByLeagueUseCase: GetTeamByLeagueUseCase,
    private val getSquadPlayersByTeamUseCase: GetSquadPlayersByTeamUseCase
) : ViewModel() {

    private var _countries: MutableLiveData<ResultWrapper<List<Country>>> = MutableLiveData()
    val countries: LiveData<ResultWrapper<List<Country>>> get() = _countries

    private var _leagues: MutableLiveData<ResultWrapper<List<League>>> = MutableLiveData()
    val leagues: LiveData<ResultWrapper<List<League>>> get() = _leagues

    private var _teams: MutableLiveData<ResultWrapper<List<Team>>> = MutableLiveData()
    val teams: LiveData<ResultWrapper<List<Team>>> get() = _teams

    private var _squadPlayers: MutableLiveData<ResultWrapper<List<SquadPlayer>>> = MutableLiveData()
    val squadPlayers: LiveData<ResultWrapper<List<SquadPlayer>>> get() = _squadPlayers

    fun getAllCountries() = getDataFromRepository(_countries, getAllCountriesUseCase)

    fun getLeaguesByCountry(country: String) =
        getDataFromRepository(_leagues, getLeaguesByCountryUseCase, country)

    fun getTeamByLeague(leagueId: Int, season: Int) =
        getDataFromRepository(
            _teams,
            getTeamByLeagueUseCase,
            leagueId.toString(),
            season.toString()
        )

    fun getPlayerByTeam(teamId: Int) =
        getDataFromRepository(_squadPlayers, getSquadPlayersByTeamUseCase, teamId.toString())

    private fun <T> getDataFromRepository(
        _data: MutableLiveData<ResultWrapper<List<T>>>,
        useCase: UseCase<T>,
        vararg params: String
    ) = viewModelScope.launch {
        _data.postValue(ResultWrapper.Loading)
        withContext(Dispatchers.IO) {
            var result = useCase(fromRemote = false, *params)

            when (result) {
                is ResultWrapper.Error -> {
                    result = useCase(fromRemote = true, *params)
                }
                is ResultWrapper.Success -> {
                    if (result.data.isEmpty()) {
                        result = useCase(fromRemote = true, *params)
                    }
                }
            }

            if (result is ResultWrapper.Success) {
                result = ResultWrapper.Success(result.data.filterNotNull())
            }
            _data.postValue(result)
        }
    }
}