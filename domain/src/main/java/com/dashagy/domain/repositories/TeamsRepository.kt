package com.dashagy.domain.repositories

import com.dashagy.domain.entities.Team
import com.dashagy.domain.util.ResultWrapper

interface TeamsRepository {
    suspend fun getTeamById(id: Int, fromRemote: Boolean) : ResultWrapper<List<Team>>
}