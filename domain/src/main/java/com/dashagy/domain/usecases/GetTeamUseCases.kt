package com.dashagy.domain.usecases

import com.dashagy.domain.repositories.TeamsRepository

class GetTeamUseCases( private val teamsRepository: TeamsRepository ) {
    
    suspend fun ByIdUseCase(id: Int, fromRemote: Boolean) =
        teamsRepository.getTeamById(id, fromRemote)

    suspend fun ByNameUseCase(name: String, fromRemote: Boolean) =
        teamsRepository.getTeamByName(name, fromRemote)

    suspend fun ByLeagueUseCase(leagueId: Int, season: Int, fromRemote: Boolean) =
        teamsRepository.getTeamByLeague(leagueId, season, fromRemote)

    suspend fun ByCountryUseCase(country: String, fromRemote: Boolean) =
        teamsRepository.getTeamByCountry(country, fromRemote)

    suspend fun BySearchUseCase(search: String, fromRemote: Boolean) =
        teamsRepository.getTeamBySearch(search, fromRemote)
}