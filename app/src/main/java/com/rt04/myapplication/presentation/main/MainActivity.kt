package com.rt04.myapplication.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rt04.myapplication.R
import com.rt04.myapplication.core.utils.SessionManager
import com.rt04.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var sharedPref: SessionManager
    private var role: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = SessionManager(this)
        role = sharedPref.getRole

        setupBottomNav()
    }

    private fun setupBottomNav() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        val nav: BottomNavigationView = findViewById(R.id.bottom_navigation)
        nav.itemIconTintList = null
        setupWithNavController(binding.bottomNavigation, navController)
    }

}