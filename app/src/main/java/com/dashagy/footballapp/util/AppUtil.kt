package com.dashagy.footballapp.util

import android.content.Context
import android.widget.Toast
import com.dashagy.footballapp.fragments.CountryListFragment
import com.dashagy.footballapp.fragments.LeagueListFragment
import com.dashagy.footballapp.fragments.SquadPlayerListFragment
import com.dashagy.footballapp.fragments.TeamListFragment

object AppUtil {
    fun showMessage(context: Context, message: String){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    val S_COUNTRY_LIST_FRAGMENT = CountryListFragment::class.simpleName.toString()
    val S_LEAGUE_LIST_FRAGMENT = LeagueListFragment::class.simpleName.toString()
    val S_TEAM_LIST_FRAGMENT = TeamListFragment::class.simpleName.toString()
    val S_SQUAD_PLAYER_LIST_FRAGMENT = SquadPlayerListFragment::class.simpleName.toString()
}
