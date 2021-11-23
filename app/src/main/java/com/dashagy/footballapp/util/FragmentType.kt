package com.dashagy.footballapp.util

import androidx.fragment.app.Fragment
import com.dashagy.footballapp.fragments.CountryListFragment
import com.dashagy.footballapp.fragments.LeagueListFragment
import com.dashagy.footballapp.fragments.SquadPlayerListFragment
import com.dashagy.footballapp.fragments.TeamListFragment

sealed class FragmentType {
    abstract val frag: Fragment

    object CountryList : FragmentType() {
        override val frag: Fragment
            get() = CountryListFragment()
    }

    class LeagueList(override val frag: LeagueListFragment, val country: String) : FragmentType()
    class TeamList(override val frag: TeamListFragment, val leagueId: Int) : FragmentType()
    class SquadPlayerList(override val frag: SquadPlayerListFragment, val teamId: Int) : FragmentType()
}