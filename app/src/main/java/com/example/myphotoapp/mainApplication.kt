package com.example.myphotoapp

import android.Manifest
import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.example.myphotoapp.Logger.Logf
import com.vistrav.ask.Ask

class mainApplication : Application(){

    private val TAG = "mainApplication"
    private val INT_ID_OF_YOUR_REQUEST = 100;

    override fun onCreate() {
        super.onCreate()

        registerActivityLifecycleCallbacks(mCallbacks)
    }

    private val mCallbacks = object : Application.ActivityLifecycleCallbacks{
        override fun onActivityCreated(p0: Activity, p1: Bundle?) {
            Logf.v(TAG, "onActivityCreated " + p0.localClassName)

            Ask.on(p0)
                    .id(INT_ID_OF_YOUR_REQUEST) // in case you are invoking multiple time Ask from same activity or fragment
                    .forPermissions( Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .withRationales("Location permission need for map to work properly",
                            "In order to save file you will need to grant storage permission") //optional
                    .go()
        }

        override fun onActivityStarted(p0: Activity) {
            Logf.v(TAG, "onActivityStarted " + p0.localClassName)
        }

        override fun onActivityResumed(p0: Activity) {
            Logf.v(TAG, "onActivityResumed " + p0.localClassName)
        }

        override fun onActivityStopped(p0: Activity) {
            Logf.v(TAG, "onActivityStopped " + p0.localClassName)

        }

        override fun onActivityPaused(p0: Activity) {
            Logf.v(TAG, "onActivityPaused " + p0.localClassName)
        }

        override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
//            Logf.v(TAG, "Resumed")
        }

        override fun onActivityDestroyed(p0: Activity) {
//            Logf.v(TAG, "Resumed")
        }

    }
}