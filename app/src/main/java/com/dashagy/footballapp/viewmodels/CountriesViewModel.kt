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

    fun getAllCountries() =
        viewModelScope.launch {
            _countries.postValue(ResultWrapper.Loading)
            withContext(Dispatchers.IO) {
                var result = getAllCountriesUseCase(false)

                if (result is ResultWrapper.Success){
                    if (result.data.isEmpty()){
                        result = getAllCountriesUseCase(true)
                    }
                }

                result = if (result is ResultWrapper.Success){
                    ResultWrapper.Success(
                        result.data.filterNotNull()
                    )
                } else {
                    ResultWrapper.Error(Exception("Un error ha ocurrido"))
                }
                _countries.postValue(result)
            }
        }
}