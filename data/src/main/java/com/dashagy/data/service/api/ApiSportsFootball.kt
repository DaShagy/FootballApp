package com.dashagy.data.service.api

import com.dashagy.data.service.responses.ApiSportsBaseResponse
import com.dashagy.data.service.responses.TeamVenueResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiSportsFootball {
    @GET("/teams")
    fun getTeamById(@Query(value = "id") id: Int): Call<ApiSportsBaseResponse<List<TeamVenueResponse>>>

    @GET("/teams")
    fun getTeamByName(@Query(value = "name") name: String): Call<ApiSportsBaseResponse<List<TeamVenueResponse>>>

    @GET("/teams")
    fun getTeamByLeague(
        @Query(value = "league") leagueId: Int,
        @Query(value = "season") season: Int
    ): Call<ApiSportsBaseResponse<List<TeamVenueResponse>>>
}