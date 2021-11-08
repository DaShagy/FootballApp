package com.dashagy.domain.repositories

import com.dashagy.domain.entities.Country
import com.dashagy.domain.util.ResultWrapper

interface CountriesRepository {
    suspend fun getAllCountries(fromRemote: Boolean) : ResultWrapper<List<Country>>
}