package com.dashagy.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dashagy.data.database.entities.RoomTeam

@Dao
interface TeamDao {

    @Query("SELECT * FROM Teams")
    suspend fun getAllTeams() : List<RoomTeam>

    @Query("SELECT * FROM Teams WHERE id = :id")
    suspend fun getTeamById(id: Int) : List<RoomTeam>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeam(team: RoomTeam)
}