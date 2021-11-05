package com.dashagy.data.mapper

import com.dashagy.data.database.entities.RoomPlayer
import com.dashagy.domain.entities.Player

class PlayerMapperLocal : BaseMapperRepository<RoomPlayer, Player> {
    override fun transform(type: RoomPlayer): Player =
        Player(
            id = type.id,
            name = type.name,
            firstname = type.firstname,
            lastname = type.lastname,
            age = type.age,
            nationality = type.nationality,
            height = type.height,
            weight = type.weight,
            injured = type.injured,
            photo = type.photo,
            currentTeam = type.currentTeam
        )

    override fun transformToRepository(type: Player): RoomPlayer =
        RoomPlayer(
            id = type.id,
            name = type.name,
            firstname = type.firstname,
            lastname = type.lastname,
            age = type.age,
            nationality = type.nationality,
            height = type.height,
            weight = type.weight,
            injured = type.injured,
            photo = type.photo,
            currentTeam = type.currentTeam
        )
}