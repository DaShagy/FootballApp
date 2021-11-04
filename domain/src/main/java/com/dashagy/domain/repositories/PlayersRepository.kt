package com.dashagy.domain.repositories

import com.dashagy.domain.entities.Player
import com.dashagy.domain.util.ResultWrapper

interface PlayersRepository {
    suspend fun getPlayerById(id: Int, season: Int, fromRemote: Boolean) : ResultWrapper<List<Player>>
}