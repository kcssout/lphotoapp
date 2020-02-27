package com.example.myphotoapp;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.myphotoapp.DB.DbOpenHelper;
import com.example.myphotoapp.RecyclerView.RecyclerAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = MainActivity.class.getSimpleName();
    public RecyclerView rc_view;
    public RecyclerAdapter recyclerAdapter;
    public static MainActivity instance;
    private ArrayList<User> mUserlist;

    public MainActivity() {
        super();
    }

//    public static MainActivity getInstance() {
//        if (instance == null) {
//            instance = new MainActivity();
//        }
//
//        return instance;
//    }

    public static MainActivity getInstance() {
        if (instance == null) {
            synchronized (MainActivity.class) {
                if (instance == null) {
                    instance = new MainActivity();
                }
            }
        }

        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnphoto = findViewById(R.id.btnphoto);
        Button btn_read = findViewById(R.id.btn_read);
        mUserlist = new ArrayList<>();

        rc_view = findViewById(R.id.rc_view);

        rc_view.setLayoutManager(new LinearLayoutManager(this));
        recyclerAdapter = new RecyclerAdapter(this);
        recyclerAdapter.setHasStableIds(true);
        rc_view.setAdapter(recyclerAdapter);

        recyclerAdapter.setItem(mUserlist);
        recyclerAdapter.notifyDataSetChanged();

        btnphoto.setOnClickListener(this);
        btn_read.setOnClickListener(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnphoto) {
            Intent goDetail = new Intent(this, DetailActivity.class);
            startActivity(goDetail);
        }

        switch (view.getId()) {
            case R.id.btnphoto:

                break;
            case R.id.btn_read:
                cycleList(this);
                break;
            default:
                break;

        }

    }

    public void cycleList(Context mCon){
        Log.d(TAG, "----cycleList");
        DbOpenHelper dbopen = new DbOpenHelper(mCon);
        dbopen.open();
        mUserlist = dbopen.read();
        dbopen.close();
//                recyclerAdapter.users = mUserlist;

        if(recyclerAdapter == null){
            Log.d(TAG, ">>>>>>>>>>>>>>>>>>>>>>>>>>");
        }
        recyclerAdapter.setItem(mUserlist);
        recyclerAdapter.notifyDataSetChanged();
    }
}
