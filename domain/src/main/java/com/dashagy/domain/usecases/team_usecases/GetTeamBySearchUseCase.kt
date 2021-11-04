package com.dashagy.domain.usecases.team_usecases

import com.dashagy.domain.repositories.TeamsRepository

class GetTeamBySearchUseCase(private val teamsRepository: TeamsRepository) {
    suspend operator fun invoke(search: String, fromRemote: Boolean) =
        teamsRepository.getTeamBySearch(search, fromRemote)
}