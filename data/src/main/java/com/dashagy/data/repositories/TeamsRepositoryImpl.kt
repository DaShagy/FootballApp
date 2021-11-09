package com.dashagy.data.repositories

import com.dashagy.domain.entities.Team
import com.dashagy.domain.repositories.TeamsRepository
import com.dashagy.domain.util.ResultWrapper
import com.dashagy.data.database.daos.TeamDao
import com.dashagy.data.database.entities.SeasonLeagueTeamRelation
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
            teamResult = service.getTeam(queryType)
            if (teamResult is ResultWrapper.Success) {
                val teams = teamResult.data.map { team -> mapper.transformToRepository(team) }
                teams.map { dao.insertTeam(it) }
                if (queryType is TeamQueryType.League){
                    teams.map { team ->
                        dao.insertSeasonLeagueTeamRelation(
                            SeasonLeagueTeamRelation(
                                teamId = team.id,
                                leagueId = queryType.leagueId,
                                season = queryType.season
                            )
                        )
                    }
                }
            }
            return teamResult
        } else {
            when (queryType){
                is TeamQueryType.Country -> {
                    teamResult = ResultWrapper.Success(
                        dao.getTeamByCountry(queryType.country).map { mapper.transform(it) }
                    )
                }
                is TeamQueryType.Id -> {
                    teamResult = ResultWrapper.Success(
                        dao.getTeamById(queryType.id).map { mapper.transform(it) }
                    )
                }
                is TeamQueryType.League ->{
                    teamResult = ResultWrapper.Success(
                        dao.getTeamByLeague(
                            queryType.leagueId,
                            queryType.season
                        ).map { mapper.transform(it) }
                    )
                }
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