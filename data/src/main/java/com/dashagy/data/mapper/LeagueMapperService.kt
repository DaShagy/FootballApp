package com.dashagy.data.mapper

import com.dashagy.data.Util
import com.dashagy.data.service.responses.LeagueBaseResponse
import com.dashagy.data.service.responses.LeagueResponse
import com.dashagy.domain.entities.Country
import com.dashagy.domain.entities.League

class LeagueMapperService : BaseMapperRepository<LeagueBaseResponse, League> {
    override fun transform(type: LeagueBaseResponse): League =
        League(
            name = type.league.name,
            id = type.league.id,
            type = type.league.type,
            logo = type.league.logo,
            country = type.country.name
        )

    override fun transformToRepository(type: League): LeagueBaseResponse =
        LeagueBaseResponse(
            league = LeagueResponse(
                id = type.id,
                name = type.name,
                type = type.type,
                logo = type.logo
            ),
            country = Country(
                name = Util.EMPTY_STRING,
                code = null,
                flag = null
            )
        )
}
