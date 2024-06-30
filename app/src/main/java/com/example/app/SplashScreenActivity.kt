package com.example.app

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.app.databinding.ActivitySplashBinding
import com.example.app.features.auth.login.di.LoginServiceProvider
import com.example.app.features.auth.login.ui.AuthActivity
import com.example.app.features.home.HomeActivity
import com.example.network.security.TokenManager
import com.example.utils.extension.onJsonAnimationEnd
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE
import com.google.android.play.core.install.model.UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS
import com.google.android.play.core.install.model.UpdateAvailability.UPDATE_AVAILABLE

/* Native way cost us to use SDK 31 as min target */

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private var binding: ActivitySplashBinding? = null
    private val updateManager: AppUpdateManager by lazy { AppUpdateManagerFactory.create(this) }
    private val tokenManager: TokenManager by lazy { LoginServiceProvider().getTokenManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (binding == null) binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        checkForUpdates(UPDATE_AVAILABLE)
    }

    private fun checkForUpdates(type: Int) {
        updateManager.appUpdateInfo.addOnSuccessListener {
            if (it.updateAvailability() == type && it.isUpdateTypeAllowed(IMMEDIATE)) {
                updateManager.startUpdateFlowForResult(it, IMMEDIATE, this, REQUEST_CODE)
            } else setupSplash()
        }.addOnFailureListener {
            Log.e("SplashActivity", "Failed to check for updates: $it")
            setupSplash()
        }
    }

    private fun setupSplash() = binding?.lottieAnimation?.apply {
        setFailureListener { Log.e("ANIMATION ERROR", it.toString()) }
        setAnimation(SPLASH_LOTTIE_JSON)
        onJsonAnimationEnd {
            startApp()
        }
    }

    private fun startApp() {
        if (tokenManager.hasToken()) {
            val intent = HomeActivity.makeIntent(this)
            startActivity(intent)
        } else {
            val intent = AuthActivity.makeIntent(this)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        checkForUpdates(DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data) //TODO: use new activityResult
        if (requestCode == REQUEST_CODE && resultCode == RESULT_CANCELED) finish()
    }

    companion object {
        const val REQUEST_CODE = 123
        const val SPLASH_LOTTIE_JSON = "animations/appearing_paw_lottie.json"
    }
}