package com.dashagy.domain.usecases.player_usecases

import com.dashagy.domain.repositories.PlayersRepository
import com.dashagy.domain.usecases.UseCase
import com.dashagy.domain.util.ResultWrapper

class GetSquadPlayersByTeamUseCase(
    private val playersRepository: PlayersRepository
) : UseCase {

    override suspend operator fun invoke(
        fromRemote: Boolean,
        vararg params: String,
    ): ResultWrapper<List<Any>> =
        playersRepository.getSquadPlayersByTeam(
            teamId = params[0].toInt(),
            fromRemote = fromRemote
        )
}