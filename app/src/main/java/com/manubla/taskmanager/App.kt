package com.manubla.taskmanager

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import com.jakewharton.threetenabp.AndroidThreeTen
import com.manubla.taskmanager.controller.RetrofitController
import com.manubla.taskmanager.controller.SharedPreferencesController
import com.manubla.taskmanager.view.auth.AuthActivity
import java.lang.ref.WeakReference

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        application = this

        // Initializing LocalDate backport
        AndroidThreeTen.init(this)

        listenActivityCallbacks()

        RetrofitController.accessToken = SharedPreferencesController.getToken(this)
    }

    private fun listenActivityCallbacks() {
        registerActivityLifecycleCallbacks(Lifecycle())
    }

    inner class Lifecycle : ActivityLifecycleCallbacks {
        override fun onActivityPaused(activity: Activity?) {
        }
        override fun onActivityResumed(activity: Activity?) {
            activity?.let {
                currentActivity = WeakReference(it)
            }
        }
        override fun onActivityStarted(activity: Activity?) {
            activity?.let {
                currentActivity = WeakReference(it)
            }
        }
        override fun onActivityDestroyed(activity: Activity?) {
            if (activity == currentActivity.get()) {
                currentActivity.clear()
            }
        }
        override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
        }
        override fun onActivityStopped(activity: Activity?) {
        }
        override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        }
    }

    companion object {
        var application : Application? = null
        var currentActivity = WeakReference<Activity>(null)

        fun goToLoginScreen() {
            currentActivity.get()?.let {
                val intent = Intent(it, AuthActivity::class.java)
                it.startActivity(intent)
                it.finish()
            }
        }
    }
}
