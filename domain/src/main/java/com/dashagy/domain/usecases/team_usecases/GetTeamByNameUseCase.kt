package com.dashagy.domain.usecases.team_usecases

import com.dashagy.domain.repositories.TeamsRepository

class GetTeamByNameUseCase(private val teamsRepository: TeamsRepository) {
    suspend operator fun invoke(name: String, fromRemote: Boolean) =
        teamsRepository.getTeamByName(name, fromRemote)
}