package com.dashagy.data.mapper

import com.dashagy.domain.entities.Team
import com.dashagy.domain.entities.Venue
import com.dashagy.data.service.responses.TeamBaseResponse
import com.dashagy.data.Util

class TeamMapperService : BaseMapperRepository<TeamBaseResponse, Team> {
    override fun transform(type: TeamBaseResponse): Team = type.team

    override fun transformToRepository(type: Team): TeamBaseResponse =
        TeamBaseResponse(
            type,
            Venue(
                Util.DEFAULT_ID,
                Util.EMPTY_STRING,
                Util.EMPTY_STRING,
                Util.EMPTY_STRING,
                Util.ZERO,
                Util.EMPTY_STRING,
                Util.EMPTY_STRING
            )
        )
}