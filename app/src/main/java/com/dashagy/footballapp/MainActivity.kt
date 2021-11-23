package com.dashagy.footballapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.dashagy.footballapp.databinding.ActivityMainBinding
import com.dashagy.footballapp.fragments.CountryListFragment
import com.dashagy.footballapp.fragments.LeagueListFragment
import com.dashagy.footballapp.fragments.SquadPlayerListFragment
import com.dashagy.footballapp.fragments.TeamListFragment
import com.dashagy.footballapp.util.FragmentType
import com.dashagy.footballapp.util.SharedPreferencesManager
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val sharedPrefsManager: SharedPreferencesManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        theme.applyStyle(sharedPrefsManager.getTheme(), true)

        _binding = ActivityMainBinding.inflate(layoutInflater)

        val frameLayoutFragmentId = binding.frameLayoutFragment.id

        if (savedInstanceState == null) {
            setFragmentInLayout(CountryListFragment(), frameLayoutFragmentId)
            getFragmentFromSharedPreferences(frameLayoutFragmentId)
        }

        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menuBtnChangeTheme){
            onClickChangeTheme()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {

        if (supportFragmentManager.backStackEntryCount == 1) {
            supportFragmentManager.popBackStack()
        }

        super.onBackPressed()
    }

    private fun onClickChangeTheme(){
        if (sharedPrefsManager.getTheme() == SharedPreferencesManager.DEFAULT_THEME_ID){
            sharedPrefsManager.setTheme(SharedPreferencesManager.ALT_THEME_ID)
        } else {
            sharedPrefsManager.setTheme(SharedPreferencesManager.DEFAULT_THEME_ID)
        }

        sharedPrefsManager.setFragment(fragmentType(supportFragmentManager.fragments[0]))

        val intent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        }
        finish()
        startActivity(intent)
    }

    private fun getFragmentFromSharedPreferences(frameLayoutFragmentId: Int){

        val fragmentToRestore = sharedPrefsManager.getFragment()
        val bundle = Bundle()

        when (fragmentToRestore){
            is FragmentType.LeagueList -> {
                bundle.putString("country", fragmentToRestore.country)
                fragmentToRestore.frag.arguments = bundle
                setFragmentInLayout(fragmentToRestore.frag, frameLayoutFragmentId)
            }
            is FragmentType.TeamList -> {
                bundle.putInt("leagueId", fragmentToRestore.leagueId)
                fragmentToRestore.frag.arguments = bundle
                setFragmentInLayout(fragmentToRestore.frag, frameLayoutFragmentId)
            }
            is FragmentType.SquadPlayerList -> {
                bundle.putInt("teamId", fragmentToRestore.teamId)
                fragmentToRestore.frag.arguments = bundle
                setFragmentInLayout(fragmentToRestore.frag, frameLayoutFragmentId)
            }
            FragmentType.CountryList -> Unit
        }
    }

    private fun setFragmentInLayout(fragment: Fragment, frameLayoutFragmentId: Int){
        supportFragmentManager.beginTransaction().apply {
            replace(frameLayoutFragmentId, fragment)
            this.addToBackStack(null)
            commit()
        }
    }

    private fun fragmentType(fragment: Fragment) : FragmentType{
        when (fragment){
            is LeagueListFragment -> return FragmentType.LeagueList(fragment, fragment.requireArguments().getString("country")!!)
            is TeamListFragment -> return FragmentType.TeamList(fragment, fragment.requireArguments().getInt("leagueId"))
            is SquadPlayerListFragment -> return FragmentType.SquadPlayerList(fragment, fragment.requireArguments().getInt("teamId"))
            else -> return FragmentType.CountryList
        }
    }

}