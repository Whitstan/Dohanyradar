package com.indie.whitstan.dohanyradar.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem

import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.get
import androidx.navigation.ui.NavigationUI

import kotlinx.android.synthetic.main.activity_main.*

import com.indie.whitstan.dohanyradar.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_AppCompat_Light_NoActionBar)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        setupBottomNav(navController)
        setupActionBar(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return (Navigation.findNavController(this, R.id.nav_host_fragment_container).navigateUp()
                || super.onSupportNavigateUp())
    }

    private fun setupActionBar(navController: NavController){
        val toolbar = findViewById<Toolbar>(R.id.toolbar_main)
        setSupportActionBar(toolbar)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    private fun setupBottomNav(navController: NavController) {
        bottom_nav?.let {
            NavigationUI.setupWithNavController(it, navController)
        }
        bottom_nav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.tobacco_shops_list_destination -> {
                    if (navController.currentDestination == navController.graph[R.id.tobacco_shops_map_destination]){
                        navController.navigate(R.id.navigate_to_tobacco_shops_list_from_map)
                    }
                    else if (navController.currentDestination == navController.graph[R.id.tobacco_shop_detail_destination]) {
                        navController.navigate(R.id.navigate_to_tobacco_shops_list_from_tobacco_shop_detail)
                    }
                }
                R.id.tobacco_shops_map_destination -> {
                    if (navController.currentDestination == navController.graph[R.id.tobacco_shops_list_destination]) {
                        navController.navigate(R.id.navigate_to_tobacco_shops_map_from_list)
                    }
                    else if (navController.currentDestination == navController.graph[R.id.tobacco_shop_detail_destination]){
                        navController.navigate(R.id.navigate_to_tobacco_shops_map_from_tobacco_shop_detail)
                    }
                }
            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment_container)
        val navigated = NavigationUI.onNavDestinationSelected(item, navController)
        return navigated || super.onOptionsItemSelected(item)
    }
}