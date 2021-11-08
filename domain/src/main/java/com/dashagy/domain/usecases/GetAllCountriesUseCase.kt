package com.dashagy.domain.usecases

import com.dashagy.domain.repositories.CountriesRepository

class GetAllCountriesUseCase(private val countriesRepository: CountriesRepository) {
    suspend operator fun invoke(fromRemote: Boolean) =
        countriesRepository.getAllCountries(fromRemote)
}