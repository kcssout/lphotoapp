package com.example.myphotoapp.Fragment

import android.Manifest
import android.app.AlertDialog
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myphotoapp.DB.DB.Dog
import com.example.myphotoapp.DogView.DogDB
import com.example.myphotoapp.DogView.DogAddActivity
import com.example.myphotoapp.Adapter.subRvAdapter
import com.example.myphotoapp.DogView.ViewModel.DogViewModel
import com.example.myphotoapp.Logger.Logf
import com.example.myphotoapp.R
import kotlinx.android.synthetic.main.activity_dog.*
import kotlinx.android.synthetic.main.activity_dog.view.*
import kotlinx.android.synthetic.main.main_toolbar.view.*
import java.util.ArrayList

class SearchFragment : Fragment(){

    val TAG : String = "SearchSlidePageFragment"
    var itemlist: MutableList<Dog> = mutableListOf()
    var mAdapter: subRvAdapter? = null
    private lateinit var dogViewModel: DogViewModel
    var SearchView: SearchView? = null

//    private val permissionss = mutableListOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.CALL_PHONE, Manifest.permission.CAMERA)
//    private val MULTIPLE_PERMISSIONS = 101
    var name=""


    fun newInstance(): SearchFragment
    {
        val args = Bundle()

        val frag = SearchFragment()
        frag.arguments = args

        return frag
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.activity_dog, container, false)

        instance = this
//        toolbar = v.toolbar
//
//        if(activity is AppCompatActivity){
//            (activity as AppCompatActivity).setSupportActionBar(toolbar)
//
//        }
//        val ab = (activity as AppCompatActivity).supportActionBar!!
//        ab.setDisplayShowTitleEnabled(false)
//        ab.setDisplayHomeAsUpEnabled(true)
//        ab.setHomeAsUpIndicator(R.drawable.baseline_menu_black_18dp)
//        setHasOptionsMenu(true) // 이거 없으면 돋보기 아이콘 안나온다(어이없다.ㅠ)


        val addbtn = v.addbtn
        addbtn.setOnClickListener {
            val i = Intent(requireContext(), DogAddActivity::class.java)
            startActivity(i)
        }

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { //onCreateView 에 생성하면 addFragment 오류 뜬다... 5/15
        mAdapter = subRvAdapter(requireContext(), itemlist as ArrayList<Dog>, { dog -> deleteDialog(dog, 1) }, { dog -> deleteDialog(dog, 2) })
        mRecyclerView.adapter = mAdapter            //mRecyclerView 아이디 가져와서 연결


        val lm = LinearLayoutManager(requireContext())  //아래는 이전 recylcerview랑 같음
        mRecyclerView.layoutManager = lm;
        mRecyclerView.itemAnimator = DefaultItemAnimator()
        mRecyclerView.setHasFixedSize(true)



        dogViewModel = ViewModelProvider(this).get(DogViewModel::class.java)

        dogViewModel.getAll().observe(viewLifecycleOwner, Observer<List<Dog>> {
            //update UI
            dog-> mAdapter!!.setDogsData(dog)
            Logf.v("dogViewModel", "test")
        })

    }
    private fun deleteDialog(dogs : Dog, type : Int){
        var builder = AlertDialog.Builder(requireContext())

        when(type){
            1 ->{
                builder.setMessage("선택 내용을 삭제할까요?")
                        .setNegativeButton(" 아뇨"){
                            _,_->
                        }
                        .setPositiveButton("네"){_,_->
                            dogViewModel.delete(dogs)
                        }
            }
            2 ->{
                builder.setMessage("전체 삭제할까요?")
                        .setNegativeButton(" 아뇨"){
                            _,_->
                        }
                        .setPositiveButton("네"){_,_->
                            dogViewModel.deleteAll(dogs)
                        }
            }
        }
        builder.show()
    }


//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        val inflater = inflater
//       inflater.inflate(R.menu.menu_search, menu)
//        var searchManager = (activity as AppCompatActivity).getSystemService(Context.SEARCH_SERVICE) as SearchManager
//        SearchView = menu.findItem(R.id.menu_action_search).actionView as SearchView
//        SearchView!!.setSearchableInfo(searchManager.getSearchableInfo((activity as AppCompatActivity).componentName))
//        SearchView!!.maxWidth = Integer.MAX_VALUE
//        SearchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                mAdapter!!.filter.filter(query)
//                return false
//            }
//
//            override fun onQueryTextChange(query: String?): Boolean {
//                mAdapter!!.filter.filter(query)
//                return false
//            }
//        })
//    }


    override fun onDestroy() {
        super.onDestroy()
        DogDB.destroyInsatance()

    }


    companion object {

        lateinit var instance : SearchFragment
        fun searchinstance() : SearchFragment {
            return instance
        }
    }
}