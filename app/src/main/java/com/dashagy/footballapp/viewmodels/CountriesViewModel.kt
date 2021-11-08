package com.dashagy.footballapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dashagy.domain.entities.Country
import com.dashagy.domain.usecases.GetAllCountriesUseCase
import com.dashagy.domain.util.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CountriesViewModel(
    private val getAllCountriesUseCase: GetAllCountriesUseCase
) : ViewModel() {
    private var _countries: MutableLiveData<ResultWrapper<List<Country>>> = MutableLiveData()
    val countries: LiveData<ResultWrapper<List<Country>>> get() = _countries

    fun getAllCountries(fromRemote: Boolean = false) =
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _countries.postValue(getAllCountriesUseCase(fromRemote))
            }
        }
}