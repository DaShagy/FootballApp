package com.dashagy.footballapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.dashagy.footballapp.databinding.ActivityMainBinding
import com.dashagy.footballapp.fragments.CountryListFragment
import com.dashagy.footballapp.fragments.LeagueListFragment
import com.dashagy.footballapp.fragments.SquadPlayerListFragment
import com.dashagy.footballapp.fragments.TeamListFragment
import org.koin.ext.isInt

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getSharedPreferences(
            "Session",
            Context.MODE_PRIVATE
        )

        theme.applyStyle(sharedPreferences.getInt(AppUtil.KEY_THEME, AppUtil.DEFAULT_THEME_ID), true)

        _binding = ActivityMainBinding.inflate(layoutInflater)

        val frameLayoutFragmentId = binding.frameLayoutFragment.id

        if (savedInstanceState == null) {
            setFragmentToShow(CountryListFragment(), frameLayoutFragmentId)
            restoreFragmentFromSharedPreferences(frameLayoutFragmentId)
        }

        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menuBtnChangeTheme){
            changeTheme()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {

        if (supportFragmentManager.backStackEntryCount == 1) {
            supportFragmentManager.popBackStack()
        }

        super.onBackPressed()
    }

    private fun changeTheme(){
        if (sharedPreferences.getInt(AppUtil.KEY_THEME, AppUtil.DEFAULT_THEME_ID) == AppUtil.DEFAULT_THEME_ID){
            sharedPreferences.edit().putInt(AppUtil.KEY_THEME, AppUtil.ALT_THEME_ID).apply()
        } else {
            sharedPreferences.edit().putInt(AppUtil.KEY_THEME, AppUtil.DEFAULT_THEME_ID).apply()
        }

        val currentFragment = supportFragmentManager.fragments[0]

        saveFragmentInSharedPreferences(currentFragment)

        val intent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        }
        finish()
        startActivity(intent)
    }

    private fun saveFragmentInSharedPreferences(currentFragment: Fragment) {
        sharedPreferences.edit().putString(AppUtil.KEY_FRAGMENT_NAME, currentFragment::class.simpleName.toString()).apply()
        when (currentFragment){
            is CountryListFragment -> {
                sharedPreferences.edit().remove(
                    AppUtil.KEY_FRAGMENT_DATA
                ).apply()
            }
            is LeagueListFragment -> {
                sharedPreferences.edit().putString(
                    AppUtil.KEY_FRAGMENT_DATA,
                    currentFragment.arguments?.getString("country")
                ).apply()
            }
            is TeamListFragment -> {
                sharedPreferences.edit().putString(
                    AppUtil.KEY_FRAGMENT_DATA,
                    currentFragment.arguments?.getInt("leagueId").toString()
                ).apply()
            }
            is SquadPlayerListFragment -> {
                sharedPreferences.edit().putString(
                    AppUtil.KEY_FRAGMENT_DATA,
                    currentFragment.arguments?.getInt("teamId").toString()
                ).apply()
            }
        }
    }

    private fun restoreFragmentFromSharedPreferences(frameLayoutFragmentId: Int){

        val fragmentToRestore = sharedPreferences.getString(AppUtil.KEY_FRAGMENT_NAME, AppUtil.S_COUNTRY_LIST_FRAGMENT)!!
        val fragmentToRestoreArgument = sharedPreferences.getString(AppUtil.KEY_FRAGMENT_DATA, "-1")!!

        val bundle = Bundle()

        when (fragmentToRestore){
            AppUtil.S_LEAGUE_LIST_FRAGMENT -> {
                bundle.putString("country", fragmentToRestoreArgument)
                val leagueListFragment = LeagueListFragment()
                leagueListFragment.arguments = bundle
                setFragmentToShow(leagueListFragment, frameLayoutFragmentId)
            }
            AppUtil.S_TEAM_LIST_FRAGMENT -> {
                bundle.putInt("leagueId", fragmentToRestoreArgument.toInt())
                val teamListFragment = TeamListFragment()
                teamListFragment.arguments = bundle
                setFragmentToShow(teamListFragment, frameLayoutFragmentId)
            }
            AppUtil.S_SQUAD_PLAYER_LIST_FRAGMENT -> {
                bundle.putInt("teamId", fragmentToRestoreArgument.toInt())
                val squadPlayerListFragment = SquadPlayerListFragment()
                squadPlayerListFragment.arguments = bundle
                setFragmentToShow(squadPlayerListFragment, frameLayoutFragmentId)
            }
        }
    }

    private fun setFragmentToShow(fragment: Fragment, frameLayoutFragmentId: Int){
        supportFragmentManager.beginTransaction().apply {
            replace(frameLayoutFragmentId, fragment)
            this.addToBackStack("squadPlayerFragment")
            commit()
        }
    }
}