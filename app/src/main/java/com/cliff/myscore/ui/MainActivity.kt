package com.cliff.myscore.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.cliff.myscore.R
import com.cliff.myscore.data.local.sharePref.PrefManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navView: BottomNavigationView

    @Inject
    lateinit var pref: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup)

        //bottomBar
        navView = findViewById(R.id.bottom_navigation_view)

        //container
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.setup_navigation)


        when (pref.intFirstRunPref) {
            true -> graph.startDestination = R.id.countriesFragment
            false -> graph.startDestination = R.id.mobile_navigation
        }

        navHostFragment.navController.graph = graph

        val navController = navHostFragment.navController
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_matches, R.id.navigation_countries
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_home, R.id.navigation_matches, R.id.navigation_countries -> showBottomNavigation()
                else -> hideBottomNavigation()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp() || super.onSupportNavigateUp()
    }

    private fun hideBottomNavigation() {
        navView.visibility = View.GONE
    }

    private fun showBottomNavigation() {
        navView.visibility = View.VISIBLE
    }
}