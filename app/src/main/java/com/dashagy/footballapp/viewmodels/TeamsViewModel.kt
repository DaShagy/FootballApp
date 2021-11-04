package com.dashagy.footballapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dashagy.domain.entities.Team
import com.dashagy.domain.usecases.GetTeamUseCases
import com.dashagy.domain.usecases.team_usecases.*
import com.dashagy.domain.util.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TeamsViewModel(
    private val getTeam: GetTeamUseCases
) : ViewModel() {

    private var _teams: MutableLiveData<ResultWrapper<List<Team>>> = MutableLiveData()
    val teams: LiveData<ResultWrapper<List<Team>>> get() = _teams

    fun getTeamById(id: Int, fromRemote: Boolean = false) =
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _teams.postValue(getTeam.ByIdUseCase(id, fromRemote))
            }
        }

    fun getTeamByName(name: String, fromRemote: Boolean = false) =
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _teams.postValue(getTeam.ByNameUseCase(name, fromRemote))
            }
        }

    fun getTeamByLeague(leagueId: Int, season: Int, fromRemote: Boolean = false) =
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _teams.postValue(getTeam.ByLeagueUseCase(leagueId, season, fromRemote))
            }
        }

    fun getTeamByCountry(country: String, fromRemote: Boolean = false) =
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _teams.postValue(getTeam.ByCountryUseCase(country, fromRemote))
            }
        }

    fun getTeamBySearch(search: String, fromRemote: Boolean = false) =
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _teams.postValue(getTeam.BySearchUseCase(search, fromRemote))
            }
        }
}