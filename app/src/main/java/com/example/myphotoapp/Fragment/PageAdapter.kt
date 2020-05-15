package com.example.myphotoapp.Fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class PageAdapter(fm: FragmentManager, s: Int) : FragmentPagerAdapter(fm, s) {

    override fun getItem(position: Int): Fragment {

        when(position){
            0 ->{
                val searchFragment = SearchSlidePageFragment()
//                searchFragment.name = "사진dxd"

                return searchFragment
            }
            1 ->{
                val cameraFragment = ScreenSlidePageFragment()
                cameraFragment.name = "정보 창"
                return cameraFragment
            }
            else -> {
                val callFragment = ScreenSlidePageFragment()
                callFragment.name = "채팅 창"
                return callFragment
            }
        }
    }


    override fun getCount(): Int {
        return 3
    }



}
