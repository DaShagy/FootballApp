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

    fun getAllCountries() =
        viewModelScope.launch {
            _countries.postValue(ResultWrapper.Loading)
            getDataFromRepository(_countries, getAllCountriesUseCase)
        }

    fun getLeaguesByCountry(country: String) =
        viewModelScope.launch {
            _leagues.postValue(ResultWrapper.Loading)
            getDataFromRepository(_leagues, getLeaguesByCountryUseCase, country)
        }

    fun getTeamByLeague(leagueId: Int, season: Int) =
        viewModelScope.launch {
            _teams.postValue(ResultWrapper.Loading)
            getDataFromRepository(
                _teams,
                getTeamByLeagueUseCase,
                leagueId.toString(),
                season.toString()
            )
        }

    fun getPlayerByTeam(teamId: Int) =
        viewModelScope.launch {
            _squadPlayers.postValue(ResultWrapper.Loading)
            getDataFromRepository(_squadPlayers, getSquadPlayersByTeamUseCase, teamId.toString())
        }

    private suspend fun <T> getDataFromRepository(
        _data: MutableLiveData<ResultWrapper<List<T>>>,
        useCase: UseCase,
        vararg params: String
    ) = withContext(Dispatchers.IO){
        var result = useCase(fromRemote = false, *params)
                as ResultWrapper<List<T>>

        when (result){
            is ResultWrapper.Error -> {
                result = useCase(fromRemote = true, *params)
                        as ResultWrapper<List<T>>
            }
            is ResultWrapper.Success -> {
                if (result.data.isEmpty()){
                    result = useCase(fromRemote = true, *params)
                            as ResultWrapper<List<T>>
                }
            }
        }

        result = if (result is ResultWrapper.Success){
            ResultWrapper.Success(
                result.data.filterNotNull()
            )
        } else {
            ResultWrapper.Error(Exception("Un error ha ocurrido"))
        }
        _data.postValue(result)
    }
}