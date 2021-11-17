package com.dashagy.domain.usecases.team_usecases

import com.dashagy.domain.entities.Team
import com.dashagy.domain.repositories.TeamsRepository
import com.dashagy.domain.usecases.UseCase
import com.dashagy.domain.util.ResultWrapper

class GetTeamByLeagueUseCase(
    private val teamsRepository: TeamsRepository
) : UseCase<Team> {

    override suspend operator fun invoke(
        fromRemote: Boolean,
        vararg params: String
    ): ResultWrapper<List<Team>> =
        teamsRepository.getTeamByLeague(
            leagueId = params[0].toInt(),
            season = params[1].toInt(),
            fromRemote = fromRemote
        )

}