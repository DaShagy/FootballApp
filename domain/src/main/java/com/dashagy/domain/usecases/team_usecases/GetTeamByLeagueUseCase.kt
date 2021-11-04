package com.dashagy.domain.usecases.team_usecases

import com.dashagy.domain.repositories.TeamsRepository

class GetTeamByLeagueUseCase(private val teamsRepository: TeamsRepository) {
    suspend operator fun invoke(leagueId: Int, fromRemote: Boolean) =
        teamsRepository.getTeamByLeague(leagueId, fromRemote)
}