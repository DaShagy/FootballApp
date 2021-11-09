package com.dashagy.footballapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dashagy.domain.entities.League
import com.dashagy.domain.usecases.GetLeaguesByCountryUseCase
import com.dashagy.domain.util.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LeaguesViewModel(
    private val getLeaguesByCountryUseCase: GetLeaguesByCountryUseCase
) : ViewModel() {

    private var _leagues: MutableLiveData<ResultWrapper<List<League>>> = MutableLiveData()
    val leagues: LiveData<ResultWrapper<List<League>>> get() = _leagues

    fun getLeaguesByCountry(country: String, fromRemote: Boolean = false) =
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _leagues.postValue(getLeaguesByCountryUseCase(country, fromRemote))
            }
        }
}
