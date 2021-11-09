package com.dashagy.domain.usecases.player_usecases

import com.dashagy.domain.repositories.PlayersRepository

class GetSquadPlayersByTeamUseCase(private val playersRepository: PlayersRepository) {
    suspend operator fun invoke(teamId: Int, fromRemote: Boolean) =
        playersRepository.getSquadPlayersByTeam(teamId, fromRemote)
}