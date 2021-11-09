package com.dashagy.footballapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dashagy.domain.entities.SquadPlayer
import com.dashagy.domain.usecases.player_usecases.GetSquadPlayersByTeamUseCase
import com.dashagy.domain.util.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SquadPlayersViewModel(
    private val getSquadPlayersByTeamUseCase: GetSquadPlayersByTeamUseCase,
) : ViewModel() {

    private var _players: MutableLiveData<ResultWrapper<List<SquadPlayer>>> = MutableLiveData()
    val players: LiveData<ResultWrapper<List<SquadPlayer>>> get() = _players

    fun getSquadPlayerByTeam(teamId: Int, fromRemote: Boolean = false) =
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _players.postValue(getSquadPlayersByTeamUseCase(teamId, fromRemote))
            }
        }
}