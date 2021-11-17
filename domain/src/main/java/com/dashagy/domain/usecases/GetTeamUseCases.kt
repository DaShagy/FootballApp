package com.dashagy.domain.usecases

import com.dashagy.domain.repositories.TeamsRepository
import com.dashagy.domain.usecases.team_usecases.*

class GetTeamUseCases(
    private val getTeamByIdUseCase: GetTeamByIdUseCase,
    private val getTeamByNameUseCase: GetTeamByNameUseCase,
    private val getTeamByLeagueUseCase: GetTeamByLeagueUseCase,
    private val getTeamByCountryUseCase: GetTeamByCountryUseCase,
    private val getTeamBySearchUseCase: GetTeamBySearchUseCase
) {
    
    suspend fun byId(id: Int, fromRemote: Boolean) =
        getTeamByIdUseCase(id, fromRemote)

    suspend fun byName(name: String, fromRemote: Boolean) =
        getTeamByNameUseCase(name, fromRemote)

    /*suspend fun byLeague(leagueId: Int, season: Int, fromRemote: Boolean) =
        getTeamByLeagueUseCase(leagueId, season, fromRemote)*/

    suspend fun byCountry(country: String, fromRemote: Boolean) =
        getTeamByCountryUseCase(country, fromRemote)

    suspend fun bySearch(search: String, fromRemote: Boolean) =
        getTeamBySearchUseCase(search, fromRemote)
}