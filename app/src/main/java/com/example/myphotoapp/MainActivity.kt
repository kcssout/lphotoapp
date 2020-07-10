package com.example.myphotoapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myphotoapp.Fragment.PageAdapter
import kotlinx.android.synthetic.main.custom_tab_button.view.*
import kotlinx.android.synthetic.main.fragment_main.*

class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_main)

        initViewPager()
    }


    private fun createView(tabName: String): View {
        var tabView = LayoutInflater.from(baseContext).inflate(R.layout.custom_tab_button, null)

        tabView.tab_text.text = tabName
        when (tabName) {
            "사진" -> {
                tabView.tab_logo.setImageResource(android.R.drawable.ic_menu_search)
                return tabView
            }
            "정보" -> {
                tabView.tab_logo.setImageResource(android.R.drawable.ic_menu_camera)
                return tabView
            }
            "채팅" -> {
                tabView.tab_logo.setImageResource(android.R.drawable.ic_menu_call)
                return tabView
            }
            else -> {
                return tabView
            }
        }
    }

    private fun initViewPager(){


        val adapter = PageAdapter(supportFragmentManager,3) // PageAdapter 생성


        main_viewPager.adapter = adapter // 뷰페이저에 adapter 장착
        main_tablayout.setupWithViewPager(main_viewPager) // 탭레이아웃과 뷰페이저를 연동


        main_tablayout.getTabAt(0)?.setCustomView(createView("search"))
        main_tablayout.getTabAt(1)?.setCustomView(createView("look"))
        main_tablayout.getTabAt(2)?.setCustomView(createView("chat"))

    }

//
//    private fun checkPermissions() {
//        var result: Int
//        val chkpermission: MutableList<String> = ArrayList()
//        for (pm in permissionss) {
//            result = mainApplication.ActivityReference.getContext().checkSelfPermission(pm)
//            if (result != PackageManager.PERMISSION_GRANTED) {
//                chkpermission.add(pm) // deny
//            }
//        }
//
//        if (!chkpermission.isEmpty()) {
//            Log.d(TAG, chkpermission.toString())
//
//            ActivityCompat.requestPermissions(requireActivity(),chkpermission.toTypedArray(), MULTIPLE_PERMISSIONS)
//
//
//        }
//
//        if(chkpermission.size>0){
//        }
//
//    }
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
//        Log.d(TAG, "Dsjkflsdjklfjksldfjsdklf")
//
//        if (requestCode == MULTIPLE_PERMISSIONS) {
//            //안드로이드 마시멜로 이후 퍼미션 체크.
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                for (i in grantResults.indices) {
//                    Log.d(TAG, "count "+ grantResults.size)
//
//                    if (grantResults[i] == 0) {
//                        Log.d(TAG, " ok i " + i + " // "+ grantResults[i])
//                        if (grantResults.size == i + 1) {
//                            Log.d(TAG, " start activity i " + i + " // "+ grantResults[i])
//                        }
//                    } else {
//                        // 거부한 이력이 있으면 true를 반환한다.
//                        if (shouldShowRequestPermissionRationale(permissions[i])) {
//                            requestPermissions(permissions, MULTIPLE_PERMISSIONS)
//                        } else {
//                            AlertDialog.Builder(requireContext())
//
//                                    .setTitle("다시보지않기 클릭.")
//                                    .setMessage(permissions[i] + " 권한이 거부되었습니다 설정에서 직접 권한을 허용 해주세요.")
//                                    .setNeutralButton("설정") { dialog, which ->
//                                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
//                                        intent.data = Uri.fromParts("package",
//                                                BuildConfig.APPLICATION_ID, null)
//                                        startActivity(intent)
//                                        Toast.makeText(requireContext(), "권한 설정 후 다시 실행 해주세요.", Toast.LENGTH_SHORT).show()
//                                        requireActivity().finish()
//                                    }
//                                    .setPositiveButton("확인") { dialog, which ->
//                                        Toast.makeText(requireContext(), "권한 설정을 하셔야 앱을 사용할 수 있습니다.", Toast.LENGTH_SHORT)
//                                                .show()
//                                        requireActivity().finish()
//                                    }
//                                    .setCancelable(false)
//                                    .create().show()
//                        }// shouldShowRequestPermissionRationale /else
//                    } // 권한 거절
//                } // for end
//            }//Build.VERSION.SDK_INT  end
//        }//requestCode  end
//    }

}