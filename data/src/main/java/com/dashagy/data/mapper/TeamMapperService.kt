package com.dashagy.data.mapper

import com.dashagy.domain.entities.Team
import com.dashagy.domain.entities.Venue
import com.dashagy.data.service.responses.TeamVenueResponse
import com.dashagy.data.Util

class TeamMapperService : BaseMapperRepository<TeamVenueResponse, Team> {
    override fun transform(type: TeamVenueResponse): Team = type.team

    override fun transformToRepository(type: Team): TeamVenueResponse =
        TeamVenueResponse(
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