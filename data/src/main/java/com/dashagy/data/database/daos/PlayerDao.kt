package com.dashagy.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dashagy.data.database.entities.RoomPlayer

@Dao
interface PlayerDao {

    @Query("SELECT * FROM Players")
    suspend fun getAllPlayers() : List<RoomPlayer>

    @Query("SELECT * FROM Players WHERE id = :id")
    suspend fun getPlayerById(id: Int) : List<RoomPlayer>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayer(player: RoomPlayer)
}