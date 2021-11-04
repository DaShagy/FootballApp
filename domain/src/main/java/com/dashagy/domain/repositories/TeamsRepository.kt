package com.dashagy.domain.repositories

import com.dashagy.domain.entities.Team
import com.dashagy.domain.util.ResultWrapper

interface TeamsRepository {
    suspend fun getTeamById(id: Int, fromRemote: Boolean) : ResultWrapper<List<Team>>
    suspend fun getTeamByName(name: String, fromRemote: Boolean) : ResultWrapper<List<Team>>
    suspend fun getTeamByLeague(leagueId: Int, fromRemote: Boolean) : ResultWrapper<List<Team>>
    suspend fun getTeamBySeason(season: Int, fromRemote: Boolean) : ResultWrapper<List<Team>>
    suspend fun getTeamByCountry(country: String, fromRemote: Boolean) : ResultWrapper<List<Team>>
    suspend fun getTeamBySearch(search: String, fromRemote: Boolean) : ResultWrapper<List<Team>>
}