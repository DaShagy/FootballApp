package com.dashagy.footballapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dashagy.domain.entities.League
import com.dashagy.domain.util.ResultWrapper

class LeaguesViewModel() : ViewModel() {

    private var _leagues: MutableLiveData<ResultWrapper<List<League>>> = MutableLiveData()
    val leagues: LiveData<ResultWrapper<List<League>>> get() = _leagues

}
