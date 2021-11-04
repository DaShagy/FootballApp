package com.dashagy.domain.usecases.player_usecases

import com.dashagy.domain.repositories.PlayersRepository

class GetPlayerByIdUseCase(private val playersRepository: PlayersRepository) {
    suspend operator fun invoke(id: Int, season: Int, fromRemote: Boolean) =
        playersRepository.getPlayerById(id, season, fromRemote)
}