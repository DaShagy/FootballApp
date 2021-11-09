package com.dashagy.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dashagy.data.database.entities.RoomTeam
import com.dashagy.data.database.entities.SeasonLeagueTeamRelation

@Dao
interface TeamDao {

    @Query("SELECT * FROM Teams")
    suspend fun getAllTeams() : List<RoomTeam>

    @Query("SELECT * FROM Teams WHERE id = :id")
    suspend fun getTeamById(id: Int) : List<RoomTeam>

    @Query("SELECT * FROM Teams WHERE country = :country")
    suspend fun getTeamByCountry(country: String) : List<RoomTeam>

    @Query("SELECT * FROM Teams WHERE name LIKE :name")
    suspend fun getTeamByName(name: String): List<RoomTeam>

    @Query("SELECT * FROM Teams WHERE Teams.country LIKE :search OR Teams.name LIKE :search")
    suspend fun getTeamBySearch(search: String): List<RoomTeam>

    @Query("""
        SELECT id, name, country, founded, national, logo 
        FROM Teams, SeasonLeagueTeams 
        WHERE Teams.id = SeasonLeagueTeams.leagueId 
            AND SeasonLeagueTeams.leagueId = :leagueId
            AND SeasonLeagueTeams.season = :season
            """)
    suspend fun getTeamByLeague(leagueId: Int, season: Int): List<RoomTeam>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeasonLeagueTeamRelation(sltRelation: SeasonLeagueTeamRelation)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeam(team: RoomTeam)
}