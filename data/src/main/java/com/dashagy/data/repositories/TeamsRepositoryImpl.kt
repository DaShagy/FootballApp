package com.dashagy.data.repositories

import com.dashagy.domain.entities.Team
import com.dashagy.domain.repositories.TeamsRepository
import com.dashagy.domain.util.ResultWrapper
import com.dashagy.data.database.daos.TeamDao
import com.dashagy.data.mapper.TeamMapperLocal
import com.dashagy.data.service.services.TeamService

class TeamsRepositoryImpl(
    private val dao: TeamDao,
    private val mapper: TeamMapperLocal,
    private val service: TeamService
) : TeamsRepository {

    override suspend fun getTeamById(
        id: Int,
        fromRemote: Boolean,
    ): ResultWrapper<List<Team>> =
        if (fromRemote){
            val teamResult = service.getTeamById(id)
            if (teamResult is ResultWrapper.Success) {
                teamResult.data.map {
                        team -> mapper.transformToRepository(team).also { dao.insertTeam(it) }
                }
            }
            teamResult
        } else{
            ResultWrapper.Success(
                dao.getTeamById(id).map{
                    mapper.transform(it)
                }
            )
    }

    override suspend fun getTeamByName(
        name: String,
        fromRemote: Boolean,
    ): ResultWrapper<List<Team>> =
        if (fromRemote){
            val teamResult = service.getTeamByName(name)
            if (teamResult is ResultWrapper.Success) {
                teamResult.data.map {
                        team -> mapper.transformToRepository(team).also { dao.insertTeam(it) }
                }
            }
            teamResult
        } else {
            ResultWrapper.Success(
                dao.getTeamByName(name).map{
                    mapper.transform(it)
                }
            )
        }

    override suspend fun getTeamByLeague(
        leagueId: Int,
        season: Int,
        fromRemote: Boolean,
    ): ResultWrapper<List<Team>> =
        if (fromRemote){
            val teamResult = service.getTeamByLeague(leagueId, season)
            if (teamResult is ResultWrapper.Success) {
                val teams = teamResult.data.map { team -> mapper.transformToRepository(team) }
                teams.map { dao.insertTeam(it) }
            }
            teamResult
        } else {
            TODO("Search by league locale not implemented yet")
        }

    override suspend fun getTeamByCountry(
        country: String,
        fromRemote: Boolean,
    ): ResultWrapper<List<Team>> =
        if (fromRemote){
            val teamResult = service.getTeamByCountry(country)
            if (teamResult is ResultWrapper.Success) {
                val teams = teamResult.data.map { team -> mapper.transformToRepository(team) }
                teams.map { dao.insertTeam(it) }
            }
            teamResult
        } else {
            TODO("Search by country locale not implemented yet")
        }

    override suspend fun getTeamBySearch(
        search: String,
        fromRemote: Boolean,
    ): ResultWrapper<List<Team>> =
        if (fromRemote){
            val teamResult = service.getTeamBySearch(search)
            if (teamResult is ResultWrapper.Success) {
                val teams = teamResult.data.map { team -> mapper.transformToRepository(team) }
                teams.map { dao.insertTeam(it) }
            }
            teamResult
        } else {
            ResultWrapper.Success(
                dao.getTeamBySearch("%$search%").map{
                    mapper.transform(it)
                }
            )
        }
}