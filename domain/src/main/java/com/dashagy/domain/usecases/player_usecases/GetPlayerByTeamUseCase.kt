package com.dashagy.domain.usecases.player_usecases

import com.dashagy.domain.repositories.PlayersRepository

class GetPlayerByTeamUseCase(private val playersRepository: PlayersRepository) {
    suspend operator fun invoke(teamId: Int, season: Int, fromRemote: Boolean) =
        playersRepository.getPlayerByTeam(teamId, season, fromRemote)
}