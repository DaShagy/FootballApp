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
                _teams.postValue(getTeam.byId(id, fromRemote))
            }
        }

    fun getTeamByName(name: String, fromRemote: Boolean = false) =
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _teams.postValue(getTeam.byName(name, fromRemote))
            }
        }

    fun getTeamByLeague(leagueId: Int, season: Int) =
        viewModelScope.launch {
            _teams.postValue(ResultWrapper.Loading)
            withContext(Dispatchers.IO){

                var result = getTeam.byLeague(leagueId, season, false)

                if (result is ResultWrapper.Success){
                    if (result.data.isEmpty()){
                        result = getTeam.byLeague(leagueId, season, true)
                    }
                }

                result = if (result is ResultWrapper.Success){
                    ResultWrapper.Success(
                        result.data.filterNotNull()
                    )
                } else {
                    ResultWrapper.Error(Exception("Un error ha ocurrido"))
                }
                _teams.postValue(result)
            }
        }

    fun getTeamByCountry(country: String, fromRemote: Boolean = false) =
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _teams.postValue(getTeam.byCountry(country, fromRemote))
            }
        }

    fun getTeamBySearch(search: String, fromRemote: Boolean = false) =
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _teams.postValue(getTeam.bySearch(search, fromRemote))
            }
        }
}