package com.dashagy.domain.usecases

import com.dashagy.domain.repositories.TeamsRepository

class GetTeamByIdUseCase(private val teamsRepository: TeamsRepository) {
    suspend operator fun invoke(id: Int, fromRemote: Boolean) = teamsRepository.getTeamById(id, fromRemote)
}