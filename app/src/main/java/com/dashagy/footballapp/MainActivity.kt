package com.dashagy.footballapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.dashagy.data.database.AppDatabase
import com.dashagy.data.mapper.TeamMapperLocal
import com.dashagy.data.repositories.TeamsRepositoryImpl
import com.dashagy.data.service.services.TeamService
import com.dashagy.domain.entities.Country
import com.dashagy.domain.entities.Player
import com.dashagy.domain.entities.SquadPlayer
import com.dashagy.domain.entities.Team
import com.dashagy.domain.util.ResultWrapper
import com.dashagy.footballapp.adapters.CountriesAdapter
import com.dashagy.footballapp.databinding.ActivityMainBinding
import com.dashagy.footballapp.viewmodels.CountriesViewModel
import com.dashagy.footballapp.viewmodels.PlayersViewModel
import com.dashagy.footballapp.viewmodels.SquadPlayersViewModel
import com.dashagy.footballapp.viewmodels.TeamsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        setupActionBarWithNavController(navController)
    }

    private fun showMessage(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}