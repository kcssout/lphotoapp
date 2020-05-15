package com.example.myphotoapp.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myphotoapp.R
import kotlinx.android.synthetic.main.content_main.view.*

class ScreenSlidePageFragment : Fragment(){

    var name=""

    fun newInstance(): ScreenSlidePageFragment
    {
        val args = Bundle()

        val frag = ScreenSlidePageFragment()
        frag.arguments = args

        return frag
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val v = inflater.inflate(R.layout.content_main, container, false)
        v.tv.text= name
        return v
    }
}