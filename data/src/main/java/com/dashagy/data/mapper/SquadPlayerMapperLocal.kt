package com.dashagy.data.mapper

import com.dashagy.data.database.entities.RoomSquadPlayer
import com.dashagy.domain.entities.SquadPlayer

class SquadPlayerMapperLocal : BaseMapperRepository<RoomSquadPlayer, SquadPlayer> {
    override fun transform(type: RoomSquadPlayer): SquadPlayer =
        SquadPlayer(
            id = type.id,
            name = type.name,
            age = type.age,
            number = type.number,
            position = type.position,
            photo = type.photo,
            team = type.team
        )

    override fun transformToRepository(type: SquadPlayer): RoomSquadPlayer =
        RoomSquadPlayer(
            id = type.id,
            name = type.name,
            age = type.age,
            number = type.number,
            position = type.position,
            photo = type.photo,
            team = type.team
        )
}