package com.dashagy.data.service.services

import android.content.Context
import com.dashagy.domain.entities.Team
import com.dashagy.domain.util.ResultWrapper
import com.dashagy.data.service.api.ApiSportsFootball
import com.dashagy.data.mapper.TeamMapperService
import com.dashagy.data.service.RequestGenerator

class TeamService(context: Context) {
    private val api: RequestGenerator = RequestGenerator(context)
    private val mapper: TeamMapperService = TeamMapperService()

    fun getTeamById(id: Int): ResultWrapper<List<Team>> {
        val callResponse = api.createService(ApiSportsFootball::class.java).getTeamById(id)
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

    fun getTeamByName(name: String): ResultWrapper<List<Team>> {
        val callResponse = api.createService(ApiSportsFootball::class.java).getTeamByName(name)
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

    fun getTeamByLeague(leagueId: Int, season: Int): ResultWrapper<List<Team>> {
        val callResponse = api.createService(ApiSportsFootball::class.java).getTeamByLeague(leagueId, season)
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

    fun getTeamByCountry(country: String): ResultWrapper<List<Team>> {
        val callResponse = api.createService(ApiSportsFootball::class.java).getTeamByCountry(country)
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
    fun getTeamBySearch(search: String): ResultWrapper<List<Team>> {
        val callResponse = api.createService(ApiSportsFootball::class.java).getTeamBySearch(search)
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