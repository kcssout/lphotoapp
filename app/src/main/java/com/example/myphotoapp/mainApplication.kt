package com.example.myphotoapp

import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import com.example.myphotoapp.Logger.Logf
import com.vistrav.ask.Ask
import java.lang.ref.WeakReference

class mainApplication : Application() {


    override fun onCreate() {
        super.onCreate()

        ActivityReference.initialize(this)

    }


    object ActivityReference {

        private val TAG = "mainApplication"
        private var mTopActivityWeakRef: WeakReference<Activity>? = null
        private var mApplicationWeakRef: WeakReference<Application>? = null

        private val mCallbacks = object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
                Logf.v(TAG, "onActivityCreated " + activity.localClassName)

                setTopActivityWeakRef(activity)
            }

            override fun onActivityStarted(activity: Activity) {
                Logf.v(TAG, "onActivityStarted " + activity.localClassName)

                setTopActivityWeakRef(activity)
            }

            override fun onActivityResumed(activity: Activity) {
                Logf.v(TAG, "onActivityResumed " + activity.localClassName)

                setTopActivityWeakRef(activity)

            }

            override fun onActivityDestroyed(activity: Activity) {}
            override fun onActivityPaused(activity: Activity) {}
            override fun onActivityStopped(activity: Activity) {}
            override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle?) {}
        }

        @JvmStatic
        fun initialize(application: Application) {
            mApplicationWeakRef = WeakReference(application)
            application.registerActivityLifecycleCallbacks(mCallbacks)

        }

        private fun setTopActivityWeakRef(activity: Activity) {
            if (mTopActivityWeakRef == null || activity != (mTopActivityWeakRef as WeakReference<Activity>).get()) {
                mTopActivityWeakRef = WeakReference(activity)
            }
        }

        @JvmStatic
        fun getActivityReference(): Activity? {
            if (mTopActivityWeakRef != null) {
                val activity = (mTopActivityWeakRef as WeakReference<Activity>).get()
                if (activity != null) {
                    return activity
                }
            }

            return null
        }

        fun getContext(): Context {
            return getActivityReference() ?: mApplicationWeakRef?.get() as Context
        }
    }
}