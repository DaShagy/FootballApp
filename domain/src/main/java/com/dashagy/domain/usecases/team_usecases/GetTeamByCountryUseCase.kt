package com.dashagy.domain.usecases.team_usecases

import com.dashagy.domain.repositories.TeamsRepository

class GetTeamByCountryUseCase(private val teamsRepository: TeamsRepository) {
    suspend operator fun invoke(country: String, fromRemote: Boolean) =
        teamsRepository.getTeamByCountry(country, fromRemote)
}