package com.dashagy.data.repositories

import com.dashagy.data.database.daos.CountryDao
import com.dashagy.data.mapper.CountryMapperLocal
import com.dashagy.data.service.services.CountryService
import com.dashagy.domain.entities.Country
import com.dashagy.domain.repositories.CountriesRepository
import com.dashagy.domain.util.ResultWrapper

class CountriesRepositoryImpl(
    private val service: CountryService,
    private val mapper: CountryMapperLocal,
    private val dao: CountryDao
) : CountriesRepository {
    override suspend fun getAllCountries(fromRemote: Boolean): ResultWrapper<List<Country>> {
        if (fromRemote){
            val countriesResult = service.getAllCountries()
            if (countriesResult is ResultWrapper.Success){
                countriesResult.data.map {
                    dao.insertCountry(mapper.transformToRepository(it))
                }
            }
            return countriesResult
        } else {
            TODO("Get All countries from locale not implemented yet")
        }
    }
}