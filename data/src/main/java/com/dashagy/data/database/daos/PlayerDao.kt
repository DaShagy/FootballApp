package com.dashagy.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dashagy.data.database.entities.RoomPlayer
import com.dashagy.data.database.entities.RoomSquadPlayer

@Dao
interface PlayerDao {

    @Query("SELECT * FROM Players")
    suspend fun getAllPlayers() : List<RoomPlayer>

    @Query("SELECT * FROM Players WHERE id = :id")
    suspend fun getPlayerById(id: Int) : List<RoomPlayer>

    @Query("""
        SELECT sp.id, sp.name, age, number, position, photo, team 
        FROM `Squad Players` as sp, Teams as t 
        WHERE t.id = :teamId AND sp.team = t.name
        """)
    suspend fun getSquadPlayerByTeam(teamId: Int) : List<RoomSquadPlayer>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayer(player: RoomPlayer)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSquadPlayer(player: RoomSquadPlayer)
}