package com.example.app.features.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.app.R
import com.example.app.databinding.ActivityHomeBinding
import com.example.app.features.auth.login.di.LoginServiceProvider
import com.example.app.features.auth.login.ui.AuthActivity
import com.example.network.security.TokenManager

class HomeActivity : AppCompatActivity() {
    private var binding: ActivityHomeBinding? = null
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val tokenManager: TokenManager by lazy { LoginServiceProvider().getTokenManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (binding == null) binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setupNavigation()
    }

    private fun setupNavigation() {
        Navigation.findNavController(this, R.id.nav_host_fragment_content_main).also {
            appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ))
            setSupportActionBar(binding?.appBarMain?.toolbar)
            setupActionBarWithNavController(it, appBarConfiguration)
            binding?.navView?.setupWithNavController(it)
            it.addOnDestinationChangedListener { _, destination, _ ->
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.setDisplayShowHomeEnabled(true)
                handleNavigationIcon(destination)
            }
        }
    }

    private fun handleNavigationIcon(destination: NavDestination) = binding?.appBarMain?.toolbar?.apply {
        navigationIcon = when (destination.id) {
            R.id.listFragment -> null
            else -> navigationIcon
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.home_action_logout -> {
                logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun logout() {
        tokenManager.revoke()
        val intent = AuthActivity.makeIntent(this)
        startActivity(intent)
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        fun makeIntent(context: Context) = Intent(context, HomeActivity::class.java)
    }
}