package com.example.myphotoapp.DogView

import android.app.AlertDialog
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myphotoapp.DB.DogDB
import com.example.myphotoapp.R
import com.example.myphotoapp.DB.Dog
import com.example.myphotoapp.Logger.Logf
import com.example.myphotoapp.DogView.ViewModel.DogViewModel
import kotlinx.android.synthetic.main.activity_sub.*
import java.util.ArrayList

class SubActivity : AppCompatActivity() {

    var itemlist: MutableList<Dog> = mutableListOf()
    var SearchView: SearchView? = null
    var mAdapter: SubRvAdapter? = null
    private lateinit var dogViewModel: DogViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub)
        val toolbar = toolbar
        val addbtn = addbtn

        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)

        mAdapter = SubRvAdapter(this, itemlist as ArrayList<Dog>,{dog->},{dog->deleteDialog(dog)})
        mRecyclerView.adapter = mAdapter            //mRecyclerView 아이디 가져와서 연결


        val lm = LinearLayoutManager(this)  //아래는 이전 recylcerview랑 같음
        mRecyclerView.layoutManager = lm;
        mRecyclerView.itemAnimator = DefaultItemAnimator()
        mRecyclerView.setHasFixedSize(true)

        addbtn.setOnClickListener {
            val i = Intent(this, DogAddActivity::class.java)
            startActivity(i)
        }

        dogViewModel = ViewModelProvider(this).get(DogViewModel::class.java)

        dogViewModel.getAll().observe(this, Observer<List<Dog>> {
            //update UI
            dog-> mAdapter!!.setDogsData(dog)
            Logf.v("dogViewModel", "test")
        })


    }

    private fun deleteDialog(dogs : Dog){
        var builder = AlertDialog.Builder(this)
        builder.setMessage("삭제할까요?")
                .setNegativeButton(" 아뇨"){
                    _,_->
                }
                .setPositiveButton("네"){_,_->
                    dogViewModel.delete(dogs)
                }
        builder.show()
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

