package com.dashagy.data.service.responses

import com.dashagy.domain.entities.Team
import com.dashagy.domain.entities.Venue

data class TeamVenueResponse(
    val team: Team,
    val venue: Venue
)