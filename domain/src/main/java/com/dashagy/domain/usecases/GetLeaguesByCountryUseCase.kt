package com.dashagy.domain.usecases

import com.dashagy.domain.repositories.LeaguesRepository

class GetLeaguesByCountryUseCase(private val leaguesRepository: LeaguesRepository) {
    suspend operator fun invoke(country: String, fromRemote: Boolean) =
        leaguesRepository.getLeaguesByCountry(country, fromRemote)
}