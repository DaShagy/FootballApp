package com.dashagy.footballapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dashagy.domain.entities.Team
import com.dashagy.domain.usecases.team_usecases.GetTeamByIdUseCase
import com.dashagy.domain.usecases.team_usecases.GetTeamByLeagueUseCase
import com.dashagy.domain.usecases.team_usecases.GetTeamByNameUseCase
import com.dashagy.domain.util.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TeamsViewModel(
    val getTeamByIdUseCase: GetTeamByIdUseCase,
    val getTeamByNameUseCase: GetTeamByNameUseCase,
    val getTeamByLeagueUseCase: GetTeamByLeagueUseCase,
) : ViewModel() {

    private var _teams: MutableLiveData<ResultWrapper<List<Team>>> = MutableLiveData()
    val teams: LiveData<ResultWrapper<List<Team>>> get() = _teams

    fun getTeamById(id: Int, fromRemote: Boolean = false) =
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _teams.postValue(getTeamByIdUseCase(id, fromRemote))
            }
        }

    fun getTeamByName(name: String, fromRemote: Boolean = false) =
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _teams.postValue(getTeamByNameUseCase(name, fromRemote))
            }
        }

    fun getTeamByLeague(leagueId: Int, season: Int, fromRemote: Boolean = false) =
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _teams.postValue(getTeamByLeagueUseCase(leagueId, season, fromRemote))
            }
        }
}