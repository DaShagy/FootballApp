package com.dashagy.domain.usecases.team_usecases

import com.dashagy.domain.repositories.TeamsRepository

class GetTeamBySeasonUseCase(private val teamsRepository: TeamsRepository) {
    suspend operator fun invoke(season: Int, fromRemote: Boolean) =
        teamsRepository.getTeamBySeason(season, fromRemote)
}