package com.dashagy.data.repositories

import com.dashagy.data.database.daos.PlayerDao
import com.dashagy.data.mapper.PlayerMapperLocal
import com.dashagy.data.service.services.PlayerService
import com.dashagy.domain.entities.Player
import com.dashagy.domain.entities.Season
import com.dashagy.domain.repositories.PlayersRepository
import com.dashagy.domain.util.ResultWrapper

class PlayersRepositoryImpl(
    private val dao: PlayerDao,
    private val mapper: PlayerMapperLocal,
    private val service: PlayerService
) : PlayersRepository {

    override suspend fun getPlayerById(id: Int, season: Int, fromRemote: Boolean): ResultWrapper<List<Player>> =
        if (fromRemote){
            val playerResult = service.getPlayerById(id, season)
            if (playerResult is ResultWrapper.Success){
                val teams = playerResult.data.map { team -> mapper.transformToRepository(team) }
                teams.map { dao.insertPlayer(it) }
            }
            playerResult
        } else {
            ResultWrapper.Success(
                dao.getPlayerById(id).map{
                    mapper.transform(it)
                }
            )
        }

}