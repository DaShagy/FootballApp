package com.dashagy.domain.usecases

import com.dashagy.domain.repositories.CountriesRepository
import com.dashagy.domain.util.ResultWrapper

class GetAllCountriesUseCase(
    private val countriesRepository: CountriesRepository
) : UseCase {

    override suspend operator fun invoke(
        fromRemote: Boolean,
        vararg params: String
    ): ResultWrapper<List<Any>> =
        countriesRepository.getAllCountries(fromRemote = fromRemote)
}