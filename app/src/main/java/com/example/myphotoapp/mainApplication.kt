package com.example.myphotoapp

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import java.lang.ref.WeakReference
import java.util.*


class mainApplication : Application() {


    override fun onCreate() {
        super.onCreate()

        ActivityReference.initialize(this)


    }

    object ActivityReference : AppCompatActivity() {
        private val permissionss = mutableListOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.CALL_PHONE, Manifest.permission.CAMERA)
        private val MULTIPLE_PERMISSIONS = 101
        private val TAG = "mainApplication"
        private var mTopActivityWeakRef: WeakReference<Activity>? = null
        private var mApplicationWeakRef: WeakReference<Application>? = null



        private val mCallbacks = object : Application.ActivityLifecycleCallbacks {

            override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
                Log.d(TAG, "onActivityCreated " + activity.localClassName)
                setTopActivityWeakRef(activity)

                checkPermissions()
            }

            override fun onActivityStarted(activity: Activity) {
                Log.d(TAG, "onActivityStarted " + activity.localClassName)
                setTopActivityWeakRef(activity)

            }

            override fun onActivityResumed(activity: Activity) {

                setTopActivityWeakRef(activity)

            }

            override fun onActivityDestroyed(activity: Activity) {}
            override fun onActivityPaused(activity: Activity) {}
            override fun onActivityStopped(activity: Activity) {}
            override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle?) {}

        }

        private fun checkPermissions() {
            var result: Int
            val chkpermission: MutableList<String> = ArrayList()
            for (pm in permissionss) {
                result = (getContext() as Activity).checkSelfPermission(pm)
                if (result != PackageManager.PERMISSION_GRANTED) {
                    chkpermission.add(pm) // deny
                }
            }

            if (!chkpermission.isEmpty()) {
                Log.d(TAG, chkpermission.toString())

                ActivityCompat.requestPermissions(getContext() as Activity,chkpermission.toTypedArray(), MULTIPLE_PERMISSIONS)


            }

            if(chkpermission.size>0){
            }

        }

        override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
            Log.d(TAG, "Dsjkflsdjklfjksldfjsdklf")

            if (requestCode == MULTIPLE_PERMISSIONS) {
                //안드로이드 마시멜로 이후 퍼미션 체크.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    for (i in grantResults.indices) {
                        Log.d(TAG, "count "+ grantResults.size)

                        if (grantResults[i] == 0) {
                            Log.d(TAG, " ok i " + i + " // "+ grantResults[i])
                            if (grantResults.size == i + 1) {
                                Log.d(TAG, " start activity i " + i + " // "+ grantResults[i])
                            }
                        } else {
                            // 거부한 이력이 있으면 true를 반환한다.
                            if (shouldShowRequestPermissionRationale(permissions[i])) {
                                requestPermissions(permissions, MULTIPLE_PERMISSIONS)
                            } else {
                                AlertDialog.Builder(getContext() as Activity)

                                        .setTitle("다시보지않기 클릭.")
                                        .setMessage(permissions[i] + " 권한이 거부되었습니다 설정에서 직접 권한을 허용 해주세요.")
                                        .setNeutralButton("설정") { dialog, which ->
                                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                            intent.data = Uri.fromParts("package",
                                                    BuildConfig.APPLICATION_ID, null)
                                            startActivity(intent)
                                            Toast.makeText(applicationContext, "권한 설정 후 다시 실행 해주세요.", Toast.LENGTH_SHORT).show()
                                            finish()
                                        }
                                        .setPositiveButton("확인") { dialog, which ->
                                            Toast.makeText(applicationContext, "권한 설정을 하셔야 앱을 사용할 수 있습니다.", Toast.LENGTH_SHORT)
                                                    .show()
                                            finish()
                                        }
                                        .setCancelable(false)
                                        .create().show()
                            }// shouldShowRequestPermissionRationale /else
                        } // 권한 거절
                    } // for end
                }//Build.VERSION.SDK_INT  end
            }//requestCode  end
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
