package com.example.myphotoapp

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.app.AppCompatActivity

import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.appcompat.widget.Toolbar

import com.example.myphotoapp.DB.DbOpenHelper
import com.example.myphotoapp.DogView.SubActivity
import com.example.myphotoapp.RecyclerView.RecyclerAdapter
import kotlinx.android.synthetic.main.activity_main.*

import java.util.ArrayList
import java.util.concurrent.Executor

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var mUserlist: ArrayList<User>? = null
    private var rcAdapter : RecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnphoto = findViewById<Button>(R.id.btnphoto)
        val btn_read = findViewById<Button>(R.id.btn_read)
        val btn_dog = findViewById<Button>(R.id.btn_dog)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)

        instance = this
        mUserlist = ArrayList()

        rcAdapter = RecyclerAdapter(this)
        rc_view.adapter = rcAdapter
        val lm = LinearLayoutManager(this)

        rc_view.layoutManager = lm
        rc_view.setHasFixedSize(true)

        rcAdapter!!.setItem(mUserlist!!)
        rcAdapter!!.notifyDataSetChanged()

        btnphoto.setOnClickListener(this)
        btn_read.setOnClickListener(this)
        btn_dog.setOnClickListener(this)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id =  item.itemId

        if(id == R.id.menu_action_search){
            return true;
        } else if(id== android.R.id.home){
            finish();
            return true;
        } else{
            return super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_search, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onClick(view: View) {
        if (view.id == R.id.btnphoto) {
            val goDetail = Intent(this, DetailActivity::class.java)
            startActivity(goDetail)
        }

        when (view.id) {
            R.id.btnphoto -> {
            }
            R.id.btn_read -> cycleList(this)
            R.id.btn_dog -> {
                val goSub = Intent(this, SubActivity::class.java)
                startActivity(goSub)
            }
            else -> {
            }
        }

    }

    fun cycleList(mCon: Context) {
        Log.d(TAG, "--cycleList--")
        val dbopen = DbOpenHelper(mCon)
        dbopen.open()
        mUserlist = dbopen.read()
        dbopen.close()
        rcAdapter!!.setItem(mUserlist!!)
        rcAdapter!!.notifyDataSetChanged()
    }

    //    @Override
    //    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    //        super.onActivityResult(requestCode, resultCode, data);
    //        Log.d(TAG, "request : "+ requestCode + " / intent : "+ data.toString());
    //        switch (requestCode) {
    //            case 001:
    //
    //                ArrayList<User> list = (ArrayList<User>) data.getSerializableExtra("userlist");
    //
    //                recyclerAdapter.setItem(list);
    //                recyclerAdapter.notifyDataSetChanged();
    //                break;
    //            default:
    //                break;
    //
    //
    //        }
    //    }

    companion object {

        private val TAG = MainActivity::class.java.simpleName

        @Volatile private var instance: MainActivity? = null

        @JvmStatic fun getInstance(): MainActivity =
                instance ?: synchronized(this) {
                    instance ?: MainActivity().also {
                        instance = it
                    }
                }
    }
}
