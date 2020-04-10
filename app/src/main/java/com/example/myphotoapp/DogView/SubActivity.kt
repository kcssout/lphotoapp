package com.example.myphotoapp.DogView

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myphotoapp.R
import kotlinx.android.synthetic.main.activity_sub.*

class SubActivity : AppCompatActivity(){

    var dogList = arrayListOf<Dog>(
            Dog("Chow Chow", "Male", "4", "dog00"),
            Dog("Breed Pomeranian", "Female", "1", "dog01"),
            Dog("Golden Retriver", "Female", "3", "dog02"),
            Dog("Yorkshire Terrier", "Male", "5", "dog03"),
            Dog("Pug", "Male", "4", "dog04"),
            Dog("Alaskan Malamute", "Male", "7", "dog05"),
            Dog("Shih Tzu", "Female", "5", "dog06")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub)

        val mAdapter = SubRvAdapter(this,dogList)
        mRecyclerView.adapter = mAdapter            //mRecyclerView 아이디 가져아ㅗ서 연결



        val lm = LinearLayoutManager(this)  //아래는 이전 recylcerview랑 같음
        mRecyclerView.layoutManager = lm;
        mRecyclerView.setHasFixedSize(true)


    }

}

