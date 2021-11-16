package com.dashagy.data.service.services

import android.content.Context
import com.dashagy.data.mapper.LeagueMapperService
import com.dashagy.data.service.RequestGenerator
import com.dashagy.data.service.api.ApiSportsFootball
import com.dashagy.domain.entities.League
import com.dashagy.domain.util.ResultWrapper

class LeagueService(context: Context) {
    private val api: RequestGenerator = RequestGenerator(context)
    private val mapper: LeagueMapperService = LeagueMapperService()

    fun getLeaguesByCountry(country: String) : ResultWrapper<List<League>>{
        val service = api.createService(ApiSportsFootball.Leagues::class.java)
            .getLeaguesByCountry(country)
        try {
            val response = service.execute()
            return if (response.isSuccessful) {
                ResultWrapper.Success(
                    response.body()?.response?.let { list ->
                        list.map {
                            mapper.transform(it)
                        }
                    }!!
                )
            } else {
                ResultWrapper.Error(Exception(response.message()))
            }
        } catch (e: Exception){
            return ResultWrapper.Error(e)
        }
    }
}