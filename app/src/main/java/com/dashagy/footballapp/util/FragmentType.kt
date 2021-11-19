package com.dashagy.footballapp.util

import com.dashagy.footballapp.fragments.LeagueListFragment
import com.dashagy.footballapp.fragments.SquadPlayerListFragment
import com.dashagy.footballapp.fragments.TeamListFragment

sealed class FragmentType {
    object CountryList : FragmentType()
    class LeagueList(val frag: LeagueListFragment, val country: String) : FragmentType()
    class TeamList(val frag: TeamListFragment, val leagueId: Int) : FragmentType()
    class SquadPlayerList(val frag: SquadPlayerListFragment, val teamId: Int) : FragmentType()
}