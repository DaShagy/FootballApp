package com.dashagy.data.repositories

import com.dashagy.data.service.services.CountryService
import com.dashagy.domain.entities.Country
import com.dashagy.domain.repositories.CountriesRepository
import com.dashagy.domain.util.ResultWrapper

class CountriesRepositoryImpl(
    private val service: CountryService
) : CountriesRepository {
    override suspend fun getAllCountries(fromRemote: Boolean): ResultWrapper<List<Country>> {
        if (fromRemote){
            val countriesResult = service.getAllCountries()
            /*if (countriesResult is ResultWrapper.Success){
                TODO("Save countries got in service to local database not implemented yet")
            }*/
            return countriesResult
        } else {
            TODO("Get All countries from locale not implemented yet")
        }
    }
}