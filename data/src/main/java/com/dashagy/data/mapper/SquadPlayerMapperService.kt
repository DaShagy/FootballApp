package com.dashagy.data.mapper

import com.dashagy.data.Util
import com.dashagy.data.service.responses.SquadPlayerBaseResponse
import com.dashagy.data.service.responses.SquadPlayerResponse
import com.dashagy.domain.entities.SquadPlayer
import com.dashagy.domain.entities.Team


class SquadPlayerMapperService : BaseMapperRepository<SquadPlayerBaseResponse, List<SquadPlayer>> {
    override fun transform(type: SquadPlayerBaseResponse): List<SquadPlayer> =
        type.players.map {
            SquadPlayer(
                id = it.id,
                name = it.name,
                age = it.age,
                number = it.number,
                position = it.position,
                photo = it.photo,
                team = type.team.name
            )
        }

    override fun transformToRepository(type: List<SquadPlayer>): SquadPlayerBaseResponse =
        SquadPlayerBaseResponse(
            team = Team(
                id = Util.DEFAULT_ID,
                name = Util.EMPTY_STRING,
                country = Util.EMPTY_STRING,
                founded = Util.ZERO,
                national = Util.DEFAULT_BOOLEAN,
                logo = Util.EMPTY_STRING
            ),
            players = type.map {
                SquadPlayerResponse (
                    id = it.id,
                    name = it.name,
                    age = it.age,
                    number = it.number,
                    position = it.position,
                    photo = it.photo,
                )
            }
        )
}
