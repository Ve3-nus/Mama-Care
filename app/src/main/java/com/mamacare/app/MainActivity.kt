package com.mamacare.app

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * Single Activity that hosts every screen.
 * The NavHostFragment swaps Fragments according to res/navigation/nav_graph.xml.
 * Owner: Venus (Navigation)
 */
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById<MaterialToolbar>(R.id.toolbar))

        val navHost = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHost.navController

        // Top-level screens show the bottom bar and no back arrow.
        val topLevelDestinations = setOf(
            R.id.dashboardFragment,
            R.id.medicationListFragment,
            R.id.healthLogListFragment,
            R.id.healthInfoFragment,
            R.id.sosFragment
        )
        appBarConfiguration = AppBarConfiguration(topLevelDestinations)
        setupActionBarWithNavController(navController, appBarConfiguration)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNav.setupWithNavController(navController)

        // Hide the bottom bar on detail screens (Add/Edit forms).
        navController.addOnDestinationChangedListener { _, destination, _ ->
            bottomNav.visibility =
                if (destination.id in topLevelDestinations) View.VISIBLE else View.GONE
        }
    }

    // Makes the toolbar back arrow work
    override fun onSupportNavigateUp(): Boolean =
        navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
}
