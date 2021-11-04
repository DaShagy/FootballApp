package com.dashagy.data.service.services

import android.content.Context
import com.dashagy.data.mapper.PlayerMapperService
import com.dashagy.data.service.RequestGenerator
import com.dashagy.data.service.api.ApiSportsFootball
import com.dashagy.domain.entities.Player
import com.dashagy.domain.util.ResultWrapper

class PlayerService(context: Context) {
    private val api: RequestGenerator = RequestGenerator(context)
    private val mapper: PlayerMapperService = PlayerMapperService()

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
                        mapper.transform(it)
                    }!!
            )
        }
        return ResultWrapper.Error(Exception(response.message()))
    }
}
