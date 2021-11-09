package com.dashagy.data.repositories

import com.dashagy.data.database.daos.PlayerDao
import com.dashagy.data.mapper.PlayerMapperLocal
import com.dashagy.data.mapper.SquadPlayerMapperLocal
import com.dashagy.data.service.services.PlayerService
import com.dashagy.domain.entities.Player
import com.dashagy.domain.entities.Season
import com.dashagy.domain.entities.SquadPlayer
import com.dashagy.domain.repositories.PlayersRepository
import com.dashagy.domain.util.ResultWrapper

class PlayersRepositoryImpl (
    private val dao: PlayerDao,
    private val playerMapper: PlayerMapperLocal,
    private val squadPlayerMapper: SquadPlayerMapperLocal,
    private val service: PlayerService
) : PlayersRepository {

    override suspend fun getPlayerById(
        id: Int,
        season: Int,
        fromRemote: Boolean
    ): ResultWrapper<List<Player>> =
        if (fromRemote){
            val playerResult = service.getPlayerById(id, season)
            if (playerResult is ResultWrapper.Success){
                val teams = playerResult.data.map { team -> playerMapper.transformToRepository(team) }
                teams.map { dao.insertPlayer(it) }
            }
            playerResult
        } else {
            ResultWrapper.Success(
                dao.getPlayerById(id).map{
                    playerMapper.transform(it)
                }
            )
        }

    override suspend fun getPlayerByTeam(
        teamId: Int,
        season: Int,
        fromRemote: Boolean,
    ): ResultWrapper<List<Player>> =
        if (fromRemote){
            val playerResult = service.getPlayerByTeam(teamId, season)
            if (playerResult is ResultWrapper.Success){
                val teams = playerResult.data.map { team -> playerMapper.transformToRepository(team) }
                teams.map { dao.insertPlayer(it) }
            }
            playerResult
        } else {
            TODO("getPlayerByTeam locale")
        }

    override suspend fun getSquadPlayersByTeam(
        teamId: Int,
        fromRemote: Boolean,
    ): ResultWrapper<List<SquadPlayer>> =
        if (fromRemote){
            val playerResult = service.getSquadPlayersByTeam(teamId)
            if (playerResult is ResultWrapper.Success){
                val teams = playerResult.data
                teams.map {
                    dao.insertSquadPlayer(
                        squadPlayerMapper.transformToRepository(it)
                    )
                }
            }
            playerResult
        } else {
            TODO("getSquadByTeam locale")
        }
}