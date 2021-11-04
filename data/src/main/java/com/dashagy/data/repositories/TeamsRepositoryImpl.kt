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
    ): ResultWrapper<List<Team>> = getTeam(TeamQueryType.Id(id), fromRemote)

    override suspend fun getTeamByName(
        name: String,
        fromRemote: Boolean,
    ): ResultWrapper<List<Team>> = getTeam(TeamQueryType.Name(name), fromRemote)

    override suspend fun getTeamByLeague(
        leagueId: Int,
        season: Int,
        fromRemote: Boolean,
    ): ResultWrapper<List<Team>> = getTeam(TeamQueryType.League(leagueId, season), fromRemote)

    override suspend fun getTeamByCountry(
        country: String,
        fromRemote: Boolean,
    ): ResultWrapper<List<Team>> = getTeam(TeamQueryType.Country(country), fromRemote)

    override suspend fun getTeamBySearch(
        search: String,
        fromRemote: Boolean,
    ): ResultWrapper<List<Team>> = getTeam(TeamQueryType.Search(search), fromRemote)

    private suspend fun getTeam (queryType: TeamQueryType, fromRemote: Boolean): ResultWrapper<List<Team>> {
        val teamResult : ResultWrapper<List<Team>>
        if (fromRemote) {
            when (queryType) {
                is TeamQueryType.Country -> {
                    teamResult = service.getTeamByCountry(queryType.country)
                }
                is TeamQueryType.Id -> {
                    teamResult = service.getTeamById(queryType.id)
                }
                is TeamQueryType.League -> {
                    teamResult = service.getTeamByLeague(queryType.leagueId, queryType.season)
                }
                is TeamQueryType.Name -> {
                    teamResult = service.getTeamByName(queryType.name)
                }
                is TeamQueryType.Search -> {
                    teamResult = service.getTeamBySearch(queryType.search)
                }
            }
            if (teamResult is ResultWrapper.Success) {
                val teams = teamResult.data.map { team -> mapper.transformToRepository(team) }
                teams.map { dao.insertTeam(it) }
            }
            return teamResult
        } else {
            when (queryType){
                is TeamQueryType.Country -> TODO("Search by country locale not implemented yet")
                is TeamQueryType.Id -> {
                    teamResult = ResultWrapper.Success(
                        dao.getTeamById(queryType.id).map { mapper.transform(it) }
                    )
                }
                is TeamQueryType.League -> TODO("Search by league locale not implemented yet")
                is TeamQueryType.Name -> {
                    teamResult = ResultWrapper.Success(
                        dao.getTeamByName(queryType.name).map{ mapper.transform(it) }
                    )
                }
                is TeamQueryType.Search -> {
                    teamResult = ResultWrapper.Success(
                        dao.getTeamBySearch("%${queryType.search}%").map{ mapper.transform(it) }
                    )
                }
            }
            return teamResult
        }
    }

    sealed class TeamQueryType {
        class Id(val id: Int) : TeamQueryType()
        class Name(val name: String): TeamQueryType()
        class League(val leagueId: Int, val season: Int): TeamQueryType()
        class Country(val country: String): TeamQueryType()
        class Search(val search: String): TeamQueryType()
    }
}