package com.example.app

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.example.app.common.utils.SessionManager
import com.example.app.features.auth.login.di.LoginServiceProvider
import com.example.network.event.ExpiredTokenEvent.isForbiddenResponse
import com.example.network.retrofit.LocalWebService
import com.example.network.retrofit.RemoteWebService
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.crashreporter.CrashReporterPlugin
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.navigation.NavigationFlipperPlugin
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import java.lang.ref.WeakReference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ExampleApplication : Application() {
    private var currentActivity: WeakReference<Activity>? = null

    override fun onCreate() {
        super.onCreate()
        findCurrentActivity()
        manageAutomaticLogout()
        /* This works only on Mac */
        //Fresco.initialize(this)
        //setUpFlipper()
        /* Only works if you have your google-services.json */
        // FirebaseRemoteConfig.initRemoteConfig(
        //     FeatureFlags.getDefaults(),
        //     BuildConfig.DEBUG
        // )
    }

    private fun manageAutomaticLogout() {
        val sessionManager = SessionManager(LoginServiceProvider().getTokenManager(this))
        CoroutineScope(Dispatchers.Main).launch {
            isForbiddenResponse.collectLatest { hasToLogout ->
                if (hasToLogout) getCurrentActivity()?.let {
                    sessionManager.logout(currentActivity)
                    isForbiddenResponse.value = false
                }
            }
        }
    }

    private fun setUpFlipper() {
        if (FlipperUtils.shouldEnableFlipper(this)) {
            val client = AndroidFlipperClient.getInstance(this)
            client.addPlugin(NavigationFlipperPlugin.getInstance())
            client.addPlugin(InspectorFlipperPlugin(this, DescriptorMapping.withDefaults()))
            //client.addPlugin(FrescoFlipperPlugin()) only with mac
            val networkPlugin = NetworkFlipperPlugin()
            client.addPlugin(networkPlugin)
            val flipperInterceptor = FlipperOkhttpInterceptor(networkPlugin)
            LocalWebService.flipperInterceptor = flipperInterceptor
            RemoteWebService.flipperInterceptor = flipperInterceptor
            client.addPlugin(CrashReporterPlugin.getInstance())
            client.start()
        }
    }

    private fun findCurrentActivity() {

        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                currentActivity = WeakReference(activity)
            }

            override fun onActivityStarted(activity: Activity) {
                currentActivity = WeakReference(activity)
            }

            override fun onActivityResumed(activity: Activity) {
                currentActivity = WeakReference(activity)
            }

            override fun onActivityPaused(activity: Activity) {}

            override fun onActivityStopped(activity: Activity) {}

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

            override fun onActivityDestroyed(activity: Activity) {
                if (getCurrentActivity() === activity) {
                    currentActivity = null
                }
            }
        })
    }

    private fun getCurrentActivity(): Activity? {
        return currentActivity?.get()
    }
}