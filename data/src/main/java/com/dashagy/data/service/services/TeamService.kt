package com.dashagy.data.service.services

import android.content.Context
import com.dashagy.domain.entities.Team
import com.dashagy.domain.util.ResultWrapper
import com.dashagy.data.service.api.ApiSportsFootball
import com.dashagy.data.mapper.TeamMapperService
import com.dashagy.data.repositories.TeamsRepositoryImpl
import com.dashagy.data.service.RequestGenerator
import com.dashagy.data.service.responses.ApiSportsBaseResponse
import com.dashagy.data.service.responses.TeamBaseResponse
import retrofit2.Call

class TeamService(context: Context) {
    private val api: RequestGenerator = RequestGenerator(context)
    private val mapper: TeamMapperService = TeamMapperService()

    fun getTeam(queryType: TeamsRepositoryImpl.TeamQueryType)
    : ResultWrapper<List<Team>>{
        val service = api.createService(ApiSportsFootball.FootballTeams::class.java)
        val callResponse : Call<ApiSportsBaseResponse<List<TeamBaseResponse>>>
        when (queryType){
            is TeamsRepositoryImpl.TeamQueryType.ByCountry -> {
                callResponse = service.getTeamByCountry(queryType.country)
            }
            is TeamsRepositoryImpl.TeamQueryType.ById -> {
                callResponse = service.getTeamById(queryType.id)
            }
            is TeamsRepositoryImpl.TeamQueryType.ByLeague -> {
                callResponse = service.getTeamByLeague(queryType.leagueId, queryType.season)
            }
            is TeamsRepositoryImpl.TeamQueryType.ByName -> {
                callResponse = service.getTeamByName(queryType.name)
            }
            is TeamsRepositoryImpl.TeamQueryType.BySearch -> {
               callResponse = service.getTeamBySearch(queryType.search)
            }
        }
        try {
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
        } catch (e: Exception){
            return ResultWrapper.Error(e)
        }
    }
}