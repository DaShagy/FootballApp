package com.dashagy.footballapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.dashagy.footballapp.databinding.ActivityMainBinding
import com.dashagy.footballapp.fragments.CountryListFragment
import com.dashagy.footballapp.fragments.LeagueListFragment
import com.dashagy.footballapp.fragments.TeamListFragment

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)

        val countryListFragment = CountryListFragment()
        val frameLayoutFragmentId = binding.frameLayoutFragment.id

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().apply {
                replace(frameLayoutFragmentId, countryListFragment)
                this.addToBackStack("countryFragment")
                commit()
            }
        }

        setContentView(binding.root)
    }

    override fun onBackPressed() {

        if (supportFragmentManager.backStackEntryCount == 1) {
            supportFragmentManager.popBackStack();
        }

        super.onBackPressed()
    }
}