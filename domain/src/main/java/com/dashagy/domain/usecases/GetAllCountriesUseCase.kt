package com.dashagy.domain.usecases

import com.dashagy.domain.entities.Country
import com.dashagy.domain.repositories.CountriesRepository
import com.dashagy.domain.util.ResultWrapper

class GetAllCountriesUseCase(
    private val countriesRepository: CountriesRepository
) : UseCase<Country> {

    override suspend operator fun invoke(
        fromRemote: Boolean,
        vararg params: String
    ): ResultWrapper<List<Country>> =
        countriesRepository.getAllCountries(fromRemote = fromRemote)
}