package com.dashagy.data.repositories

import com.dashagy.domain.entities.Team
import com.dashagy.domain.repositories.TeamsRepository
import com.dashagy.domain.util.ResultWrapper
import com.dashagy.data.database.daos.TeamDao
import com.dashagy.data.database.entities.RelationSeasonLeagueTeam
import com.dashagy.data.mapper.TeamMapperLocal
import com.dashagy.data.service.services.TeamService
import kotlin.Exception

class TeamsRepositoryImpl(
    private val dao: TeamDao,
    private val mapper: TeamMapperLocal,
    private val service: TeamService
) : TeamsRepository {

    override suspend fun getTeamById(
        id: Int,
        fromRemote: Boolean,
    ): ResultWrapper<List<Team>> = getTeam(TeamQueryType.ById(id), fromRemote)

    override suspend fun getTeamByName(
        name: String,
        fromRemote: Boolean,
    ): ResultWrapper<List<Team>> = getTeam(TeamQueryType.ByName(name), fromRemote)

    override suspend fun getTeamByLeague(
        leagueId: Int,
        season: Int,
        fromRemote: Boolean,
    ): ResultWrapper<List<Team>> = getTeam(TeamQueryType.ByLeague(leagueId, season), fromRemote)

    override suspend fun getTeamByCountry(
        country: String,
        fromRemote: Boolean,
    ): ResultWrapper<List<Team>> = getTeam(TeamQueryType.ByCountry(country), fromRemote)

    override suspend fun getTeamBySearch(
        search: String,
        fromRemote: Boolean,
    ): ResultWrapper<List<Team>> = getTeam(TeamQueryType.BySearch(search), fromRemote)

    private suspend fun getTeam (queryType: TeamQueryType, fromRemote: Boolean): ResultWrapper<List<Team>> {
        val teamResult : ResultWrapper<List<Team>>
        if (fromRemote) {
            teamResult = service.getTeam(queryType)
            if (teamResult is ResultWrapper.Success) {
                val teams = teamResult.data.map { team -> mapper.transformToRepository(team) }
                teams.map { dao.insertTeam(it) }
                if (queryType is TeamQueryType.ByLeague){
                    teams.map { team ->
                        dao.insertSeasonLeagueTeamRelation(
                            RelationSeasonLeagueTeam(
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
                is TeamQueryType.ByCountry -> {
                    teamResult = if (dao.getTeamByCountry(queryType.country).isEmpty()) {
                        ResultWrapper.Error(Exception("Can not find Team by Country: ${queryType.country} in database"))
                    } else {
                        ResultWrapper.Success(
                            dao.getTeamByCountry(queryType.country).map { mapper.transform(it) }
                        )
                    }
                }
                is TeamQueryType.ById -> {
                    teamResult = if (dao.getTeamById(queryType.id).isEmpty()) {
                        ResultWrapper.Error(Exception("Can not find Team by Id: ${queryType.id} in database"))
                    } else {
                        ResultWrapper.Success(
                            dao.getTeamById(queryType.id).map { mapper.transform(it) }
                        )
                    }
                }
                is TeamQueryType.ByLeague ->{
                    teamResult = if (
                        dao.getTeamByLeague(
                            queryType.leagueId,
                            queryType.season
                        ).isEmpty()){
                        ResultWrapper.Error(Exception("Can not find Team by League Id: ${queryType.leagueId} in database"))
                    } else {
                        ResultWrapper.Success(
                            dao.getTeamByLeague(
                                queryType.leagueId,
                                queryType.season
                            ).map { mapper.transform(it) }
                        )
                    }
                }
                is TeamQueryType.ByName -> {
                    teamResult = if (dao.getTeamByName(queryType.name).isEmpty()){
                        ResultWrapper.Error(Exception("Can not find Team by Name: ${queryType.name} in database"))
                    }else{
                        ResultWrapper.Success(
                            dao.getTeamByName(queryType.name).map{ mapper.transform(it) }
                        )
                    }
                }
                is TeamQueryType.BySearch -> {
                    teamResult = if (dao.getTeamBySearch("%${queryType.search}%").isEmpty()){
                        ResultWrapper.Error(Exception("Can not find Team with search: ${queryType.search} in database"))
                    }else{
                        ResultWrapper.Success(
                            dao.getTeamBySearch("%${queryType.search}%").map{ mapper.transform(it) }
                        )
                    }
                }
            }
            return teamResult
        }
    }

    sealed class TeamQueryType {
        class ById(val id: Int) : TeamQueryType()
        class ByName(val name: String): TeamQueryType()
        class ByLeague(val leagueId: Int, val season: Int): TeamQueryType()
        class ByCountry(val country: String): TeamQueryType()
        class BySearch(val search: String): TeamQueryType()
    }
}