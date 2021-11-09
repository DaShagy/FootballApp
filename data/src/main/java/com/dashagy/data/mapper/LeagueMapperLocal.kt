package com.dashagy.data.mapper

import com.dashagy.data.database.entities.RoomLeague
import com.dashagy.domain.entities.League

class LeagueMapperLocal : BaseMapperRepository<RoomLeague, League> {
    override fun transform(type: RoomLeague): League =
        League(
            id = type.id,
            name = type.name,
            country = type.country,
            type = type.type,
            logo = type.logo
        )

    override fun transformToRepository(type: League): RoomLeague =
        RoomLeague(
            id = type.id,
            name = type.name,
            country = type.country,
            type = type.type,
            logo = type.logo
        )
}