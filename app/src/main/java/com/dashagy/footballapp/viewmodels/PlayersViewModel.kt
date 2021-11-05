package com.dashagy.footballapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dashagy.domain.entities.Player
import com.dashagy.domain.usecases.player_usecases.GetPlayerByIdUseCase
import com.dashagy.domain.usecases.player_usecases.GetPlayerByTeamUseCase
import com.dashagy.domain.util.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PlayersViewModel(
    private val getPlayerByIdUseCase: GetPlayerByIdUseCase,
    private val getPlayerByTeamUseCase: GetPlayerByTeamUseCase
) : ViewModel() {

    private var _players: MutableLiveData<ResultWrapper<List<Player>>> = MutableLiveData()
    val players: LiveData<ResultWrapper<List<Player>>> get() = _players

    fun getPlayerById(id: Int, season: Int, fromRemote: Boolean = false) =
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _players.postValue(getPlayerByIdUseCase(id, season, fromRemote))
            }
        }

    fun getPlayerByTeam(teamId: Int, season: Int, fromRemote: Boolean = false) =
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _players.postValue(getPlayerByTeamUseCase(teamId, season, fromRemote))
            }
        }

}