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

    override suspend fun getTeamById(id: Int, fromRemote: Boolean): ResultWrapper<List<Team>> =
        if (fromRemote){
            val teamResult = service.getTeamById(id)
            if (teamResult is ResultWrapper.Success) {
                val team = teamResult.data[0]
                dao.insertTeam(mapper.transformToRepository(team))
            }
            teamResult
        } else{
            ResultWrapper.Success(
                dao.getTeamById(id).map{
                    mapper.transform(it)
                }
            )
    }
}