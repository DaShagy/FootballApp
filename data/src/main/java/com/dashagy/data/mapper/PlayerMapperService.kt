package com.dashagy.data.mapper

import com.dashagy.data.Util
import com.dashagy.data.service.responses.PlayerBaseResponse
import com.dashagy.domain.entities.*

class PlayerMapperService : BaseMapperRepository<PlayerBaseResponse, Player> {
    override fun transform(type: PlayerBaseResponse): Player = type.player

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