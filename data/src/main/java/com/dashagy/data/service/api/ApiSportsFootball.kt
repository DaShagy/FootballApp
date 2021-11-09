package com.dashagy.data.service.api

import com.dashagy.data.service.responses.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiSportsFootball {

    interface FootballTeams {
        @GET("/teams")
        fun getTeamById(
            @Query(value = "id") id: Int
        ): Call<ApiSportsBaseResponse<List<TeamBaseResponse>>>

        @GET("/teams")
        fun getTeamByName(
            @Query(value = "name") name: String
        ): Call<ApiSportsBaseResponse<List<TeamBaseResponse>>>

        @GET("/teams")
        fun getTeamByLeague(
            @Query(value = "league") leagueId: Int,
            @Query(value = "season") season: Int,
        ): Call<ApiSportsBaseResponse<List<TeamBaseResponse>>>

        @GET("/teams")
        fun getTeamByCountry(
            @Query(value = "country") country: String
        ): Call<ApiSportsBaseResponse<List<TeamBaseResponse>>>

        @GET("/teams")
        fun getTeamBySearch(
            @Query(value = "search") search: String
        ): Call<ApiSportsBaseResponse<List<TeamBaseResponse>>>
    }

    interface FootballPlayers{
        @GET("/players")
        fun getPlayerById(
            @Query(value = "id") id: Int,
            @Query(value = "season") season: Int,
        ): Call<ApiSportsBaseResponse<List<PlayerBaseResponse>>>

        @GET("/players")
        fun getPlayerByTeam(
            @Query(value = "team") teamId: Int,
            @Query(value = "season") season: Int,
            @Query(value = "page") page: Int = 1
        ): Call<ApiSportsBaseResponse<List<PlayerBaseResponse>>>

        @GET("/players/squads")
        fun getSquadPlayersByTeam(
            @Query(value = "team") teamId: Int
        ): Call<ApiSportsBaseResponse<List<SquadPlayerBaseResponse>>>
    }

    interface Countries{
        @GET("/countries")
        fun getAllCountries(): Call<ApiSportsBaseResponse<List<CountryResponse>>>
    }

    interface Leagues{
        @GET("/leagues")
        fun getLeaguesByCountry(
            @Query(value = "country") country: String
        ): Call<ApiSportsBaseResponse<List<LeagueBaseResponse>>>
    }
}