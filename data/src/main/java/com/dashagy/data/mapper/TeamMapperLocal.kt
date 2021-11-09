package com.dashagy.data.mapper

import com.dashagy.domain.entities.Team
import com.dashagy.data.database.entities.RoomTeam

class TeamMapperLocal : BaseMapperRepository<RoomTeam, Team> {
    override fun transform(type: RoomTeam): Team =
        Team(
            id = type.id,
            name = type.name,
            country = type.country,
            founded = type.founded,
            national = type.national,
            logo = type.logo
        )

    override fun transformToRepository(type: Team): RoomTeam =
        RoomTeam(
            id = type.id,
            name = type.name,
            country = type.country,
            founded = type.founded,
            national = type.national,
            logo = type.logo
        )
}