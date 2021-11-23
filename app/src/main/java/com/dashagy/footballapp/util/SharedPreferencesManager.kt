package com.dashagy.footballapp.util

import android.content.Context
import android.content.SharedPreferences
import androidx.fragment.app.Fragment
import com.dashagy.footballapp.R
import com.dashagy.footballapp.fragments.CountryListFragment
import com.dashagy.footballapp.fragments.LeagueListFragment
import com.dashagy.footballapp.fragments.SquadPlayerListFragment
import com.dashagy.footballapp.fragments.TeamListFragment

class SharedPreferencesManager(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(
        "Session",
        Context.MODE_PRIVATE
    )

    private val editor = prefs.edit()

    fun getTheme() : Int = prefs.getInt(KEY_THEME, DEFAULT_THEME_ID)
    fun setTheme(themeId: Int) {
        editor.putInt(KEY_THEME, themeId).apply()
    }

    fun getFragment() : FragmentType {
        val fragment = prefs.getString(KEY_FRAGMENT_NAME, null)
        val fragmentArgument = prefs.getString(KEY_FRAGMENT_DATA, "-1")
        if (fragmentArgument != null) {
            return when (fragment){
                AppUtil.S_LEAGUE_LIST_FRAGMENT -> {
                    FragmentType.LeagueList(LeagueListFragment(), fragmentArgument)
                }
                AppUtil.S_TEAM_LIST_FRAGMENT -> {
                    FragmentType.TeamList(TeamListFragment(), fragmentArgument.toInt())
                }
                AppUtil.S_SQUAD_PLAYER_LIST_FRAGMENT -> {
                    FragmentType.SquadPlayerList(SquadPlayerListFragment(), fragmentArgument.toInt())
                }
                else -> FragmentType.CountryList
            }
        }
        return FragmentType.CountryList
    }

    //TODO("Use string as argument")
    fun setFragment(fragment: Fragment) {
        editor.putString(KEY_FRAGMENT_NAME, fragment::class.simpleName.toString()).apply()
        when (fragment){
            is CountryListFragment -> {
                editor.remove(
                    KEY_FRAGMENT_DATA
                ).apply()
            }
            is LeagueListFragment -> {
                editor.putString(
                    KEY_FRAGMENT_DATA,
                    fragment.arguments?.getString("country")
                ).apply()
            }
            is TeamListFragment -> {
                editor.putString(
                    KEY_FRAGMENT_DATA,
                    fragment.arguments?.getInt("leagueId").toString()
                ).apply()
            }
            is SquadPlayerListFragment -> {
                editor.putString(
                    KEY_FRAGMENT_DATA,
                    fragment.arguments?.getInt("teamId").toString()
                ).apply()
            }
        }
    }

    companion object {
        const val KEY_THEME = "currentTheme"
        const val KEY_FRAGMENT_NAME = "fragment"
        const val KEY_FRAGMENT_DATA = "fragmentData"

        const val DEFAULT_THEME_ID = R.style.Theme_FootballApp
        const val ALT_THEME_ID = R.style.Theme_FootballAppAlt
    }
}