package com.example.myphotoapp.DogView

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myphotoapp.DB.Dog
import com.example.myphotoapp.DB.DogDB
import com.example.myphotoapp.R
import com.example.myphotoapp.ViewModel.DogViewModel
import kotlinx.android.synthetic.main.activity_sub.*
import java.util.ArrayList

class SubActivity : AppCompatActivity(), SubRvAdapter.ItemClickListener, ViewModelStoreOwner {


    var dogList = arrayListOf<Dogs>(
            Dogs("Chow Chow", "Male", "4", null),
            Dogs("Breed Pomeranian", "Female", "1", null),
            Dogs("Golden Retriver", "Female", "3", null),
            Dogs("Yorkshire Terrier", "Male", "5", null),
            Dogs("Pug", "Male", "4", null),
            Dogs("Alaskan Malamute", "Male", "7", null),
            Dogs("Shih Tzu", "Female", "5", null)
    )
    var itemlist: MutableList<Dogs> = mutableListOf()
    var SearchView: SearchView? = null
    var mAdapter: SubRvAdapter? = null
    private  var dogViewModel: DogViewModel? = null

    private var viewModelFactory : ViewModelProvider.AndroidViewModelFactory? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub)
        val toolbar = toolbar
        val addbtn = addbtn


        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)



        itemlist = dogList
        mAdapter = SubRvAdapter(this, itemlist as ArrayList<Dogs>, this)
        mRecyclerView.adapter = mAdapter            //mRecyclerView 아이디 가져와서 연결


        val lm = LinearLayoutManager(this)  //아래는 이전 recylcerview랑 같음
        mRecyclerView.layoutManager = lm;
        mRecyclerView.itemAnimator = DefaultItemAnimator()
        mRecyclerView.setHasFixedSize(true)

        addbtn.setOnClickListener {
            val i = Intent(this, DogAddActivity::class.java)
            startActivity(i)

        }

    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.menu_action_search) {
            return true;
        } else if (id == android.R.id.home) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_search, menu)

        var searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        SearchView = menu.findItem(R.id.menu_action_search).actionView as SearchView

        SearchView!!.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        SearchView!!.maxWidth = Integer.MAX_VALUE
        SearchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                mAdapter!!.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                mAdapter!!.filter.filter(query)
                return false
            }
        })

        return true
    }


    override fun onItemClicked(item: Dogs) {
        Toast.makeText(this, "Dog > " + item.breed, Toast.LENGTH_SHORT).show()
    }


    override fun onDestroy() {
        DogDB.destroyInsatance()
        super.onDestroy()
    }


    companion object{
        private var instance : SubActivity? = null

        @JvmStatic
        fun getInstance() : SubActivity =
                instance ?: synchronized(this){
                    instance?: SubActivity().also { instance = it }
                }
    }

}

