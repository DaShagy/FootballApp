package com.dashagy.data.service.services

import android.content.Context
import com.dashagy.data.mapper.PlayerMapperService
import com.dashagy.data.mapper.SquadPlayerMapperService
import com.dashagy.data.service.RequestGenerator
import com.dashagy.data.service.api.ApiSportsFootball
import com.dashagy.data.service.responses.PlayerBaseResponse
import com.dashagy.domain.entities.Player
import com.dashagy.domain.entities.SquadPlayer
import com.dashagy.domain.util.ResultWrapper

class PlayerService(context: Context) {
    private val api: RequestGenerator = RequestGenerator(context)
    private val playerMapper: PlayerMapperService = PlayerMapperService()
    private val squadPlayerMapper: SquadPlayerMapperService = SquadPlayerMapperService()

    fun getPlayerById(id: Int, season: Int): ResultWrapper<List<Player>> {
        val callResponse = api.createService(ApiSportsFootball.FootballPlayers::class.java)
            .getPlayerById(id, season)
        val response = callResponse.execute()
        if (response.isSuccessful) {
            return ResultWrapper.Success(
                response
                    .body()
                    ?.response
                    ?.map {
                        playerMapper.transform(it)
                    }!!
            )
        }
        return ResultWrapper.Error(Exception(response.message()))
    }

    fun getPlayerByTeam(teamId: Int, season: Int): ResultWrapper<List<Player>> {
        val service = api.createService(ApiSportsFootball.FootballPlayers::class.java)
        val callResponse = service.getPlayerByTeam(teamId, season)
        val response = callResponse.execute()
        if (response.isSuccessful) {
            val players = response.body()?.response as MutableList<PlayerBaseResponse>
            var current = response.body()?.paging?.current!!
            val total = response.body()?.paging?.total!!
            while (current < total) {
                val paginatedCallResponse = service.getPlayerByTeam(teamId, season, current+1)
                val pageResponse = paginatedCallResponse.execute()
                if (pageResponse.isSuccessful) {
                    players.addAll(pageResponse.body()?.response!!)
                    current = pageResponse.body()?.paging?.current!!
                } else break
            }
            return ResultWrapper.Success(
                players.map { playerMapper.transform(it) }
            )
        }
        return ResultWrapper.Error(Exception(response.message()))
    }

    fun getSquadPlayersByTeam(teamId: Int): ResultWrapper<List<SquadPlayer>> {
        val service = api.createService(ApiSportsFootball.FootballPlayers::class.java)
        val callResponse = service.getSquadPlayersByTeam(teamId)
        val response = callResponse.execute()
        if (response.isSuccessful) {
            return ResultWrapper.Success(
                response
                    .body()
                    ?.response?.let { squadPlayerResponse ->
                        squadPlayerMapper.transform(squadPlayerResponse[0])
                    }!!
            )
        }
        return ResultWrapper.Error(Exception(response.message()))
    }
}
