package com.dashagy.data.mapper

import com.dashagy.data.Util
import com.dashagy.data.service.responses.PlayerBaseResponse
import com.dashagy.domain.entities.*

class PlayerMapperService : BaseMapperRepository<PlayerBaseResponse, Player> {
    override fun transform(type: PlayerBaseResponse): Player =
        Player(
            id = type.player.id,
            name = type.player.name,
            firstname = type.player.firstname,
            lastname = type.player.lastname,
            age = type.player.age,
            nationality = type.player.nationality,
            height = type.player.height,
            weight = type.player.weight,
            injured = type.player.injured,
            photo = type.player.photo,
            currentTeam = type.statistics[0].team.name
        )

    override fun transformToRepository(type: Player): PlayerBaseResponse =
        PlayerBaseResponse(
            type,
            listOf(
                PlayerStats(
                    team = Team(
                        id = Util.DEFAULT_ID,
                        name = Util.EMPTY_STRING,
                        country = Util.EMPTY_STRING,
                        founded = Util.ZERO,
                        national = Util.DEFAULT_BOOLEAN,
                        logo = Util.EMPTY_STRING
                    ),
                    league = League(
                        id = Util.DEFAULT_ID,
                        name = Util.EMPTY_STRING,
                        country = Util.EMPTY_STRING,
                        type = Util.EMPTY_STRING,
                        logo = Util.EMPTY_STRING,
                        flag = Util.EMPTY_STRING,
                        season = Util.ZERO
                    )
                )
            )
        )
}