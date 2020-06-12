package com.example.myphotoapp.Fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class PageAdapter(fm: FragmentManager, s: Int) : FragmentPagerAdapter(fm, s) {

    override fun getItem(position: Int): Fragment {

        when(position){
            0 ->{
                val searchFragment = SearchFragment()

                return searchFragment
            }
            1 ->{
                val cameraFragment = ScreenSlidePageFragment()
                cameraFragment.name = "정보 창"
                return cameraFragment
            }
            else -> {
                val ChatTitleFragment = ChatTitleFragment()   //ChatFragment()
                return ChatTitleFragment
            }
        }
    }


    override fun getCount(): Int {
        return 3
    }



}
