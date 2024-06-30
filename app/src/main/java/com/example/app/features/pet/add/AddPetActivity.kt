package com.example.app.features.pet.add

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.app.R
import com.example.app.databinding.ActivityPetsBinding

class AddPetActivity : AppCompatActivity() {
    private var binding: ActivityPetsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (binding == null) binding = ActivityPetsBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setupNavigation()
    }

    private fun setupNavigation() {
        Navigation.findNavController(this, R.id.nav_host_fragment).also {
            val config = AppBarConfiguration(it.graph)
            setSupportActionBar(binding?.toolbar)
            setupActionBarWithNavController(it, config)
            it.addOnDestinationChangedListener { _, destination, _ ->
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.setDisplayShowHomeEnabled(true)
                handleNavigationIcon(destination)
            }
        }
    }

    private fun handleNavigationIcon(destination: NavDestination) = binding?.toolbar?.apply {
        navigationIcon = when (destination.id) {
            R.id.listFragment -> null
            else -> navigationIcon
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                super.onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        fun makeIntent(context: Context): Intent = Intent(context, AddPetActivity::class.java)
    }

}