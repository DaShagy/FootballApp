package com.dashagy.footballapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dashagy.domain.entities.Team
import com.dashagy.domain.usecases.GetTeamByIdUseCase
import com.dashagy.domain.util.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TeamsViewModel(
    val getTeamByIdUseCase: GetTeamByIdUseCase
) : ViewModel() {

    private var _teams: MutableLiveData<ResultWrapper<List<Team>>> = MutableLiveData()
    val teams: LiveData<ResultWrapper<List<Team>>> get() = _teams

    fun getTeamById(id: Int) = viewModelScope.launch {
        withContext(Dispatchers.IO){
            _teams.postValue(getTeamByIdUseCase(id, true))
        }
    }
}
