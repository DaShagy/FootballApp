package com.dashagy.data.mapper

import com.dashagy.domain.entities.Team
import com.dashagy.data.database.entities.RoomTeam

class TeamMapperLocal : BaseMapperRepository<RoomTeam, Team> {
    override fun transform(type: RoomTeam): Team =
        Team(
            type.id,
            type.name,
            type.country,
            type.founded,
            type.national,
            type.logo
        )

    override fun transformToRepository(type: Team): RoomTeam =
        RoomTeam(
            type.id,
            type.name,
            type.country,
            type.founded,
            type.national,
            type.logo
        )
}