package com.example.myphotoapp.Fragment

import SwipeHelper
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myphotoapp.DB.DB.Dog
import com.example.myphotoapp.DogView.DogDB
import com.example.myphotoapp.DogView.DogAddActivity
import com.example.myphotoapp.Adapter.subRvAdapter
import com.example.myphotoapp.DogView.ViewModel.DogViewModel
import com.example.myphotoapp.Logger.Logf
import com.example.myphotoapp.R
import com.example.myphotoapp.touchHelper.SwipeController
import kotlinx.android.synthetic.main.activity_dog.*
import kotlinx.android.synthetic.main.activity_dog.view.*
import java.util.ArrayList

class SearchFragment : Fragment() {

    val TAG: String = this@SearchFragment::class.java.simpleName
    var itemlist: MutableList<Dog> = mutableListOf()
    var mAdapter: subRvAdapter? = null
    private lateinit var dogViewModel: DogViewModel
    var mContext : Context? = null
    var buttonWidth = 300f

    //    private val permissionss = mutableListOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.CALL_PHONE, Manifest.permission.CAMERA)
//    private val MULTIPLE_PERMISSIONS = 101
    var name = ""


    fun newInstance(): SearchFragment {
        val args = Bundle()
        val frag = SearchFragment()
        frag.arguments = args

        return frag
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.activity_dog, container, false)
        mContext= inflater.context
        instance = this
        val addbtn = v.addbtn
        addbtn.setOnClickListener {
            val i = Intent(requireContext(), DogAddActivity::class.java)
            startActivity(i)
        }
        return v
    }

    @SuppressLint("WrongConstant")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { //onCreateView 에 생성하면 addFragment 오류 뜬다... 5/15
        mAdapter = subRvAdapter(requireContext(), itemlist as ArrayList<Dog>, { dog -> deleteDialog(dog, 1) }, { dog -> deleteDialog(dog, 2) })

        val lm = LinearLayoutManager(requireContext())  //아래는 이전 recylcerview랑 같음
        mRecyclerView.layoutManager =  LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        mRecyclerView.adapter = mAdapter            //mRecyclerView 아이디 가져와서 연결

//        mRecyclerView.itemAnimator = DefaultItemAnimator()
//        mRecyclerView.setHasFixedSize(true)

//        var swipe = SwipeController()
//        var itemtouchHelper = ItemTouchHelper(swipe)
//        itemtouchHelper.attachToRecyclerView(mRecyclerView);
//
//        mRecyclerView.addItemDecoration(object : RecyclerView.ItemDecoration(){
//            override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
//                super.onDraw(c, parent, state)
//                swipe.onDraw(c)
//            }
//        })

        dogViewModel = ViewModelProvider(this).get(DogViewModel::class.java)
        dogViewModel.getAll().observe(viewLifecycleOwner, Observer<List<Dog>> {
            //update UI
            dog ->
            mAdapter!!.setDogsData(dog)
            itemlist = dog as MutableList<Dog>
            Logf.v("dogViewModel", "test LOADING")
        })
        val itemTouchHelper = ItemTouchHelper(object : SwipeHelper(mRecyclerView) {
            override fun instantiateUnderlayButton(position: Int): List<UnderlayButton> {
                var buttons = listOf<UnderlayButton>()
                val deleteButton = deleteButton(position)
//                val markAsUnreadButton = markAsUnreadButton(position)
//                val archiveButton = archiveButton(position)
//                when (position) {
//                    1 -> buttons = listOf(deleteButton)
//                    2 -> buttons = listOf(deleteButton, markAsUnreadButton)
//                    3 -> buttons = listOf(deleteButton, markAsUnreadButton, archiveButton)
//                    else -> Unit
//                }
                buttons = listOf(deleteButton)
                return buttons
            }
        })

        itemTouchHelper.attachToRecyclerView(mRecyclerView)

    }

    private fun deleteButton(position: Int) : SwipeHelper.UnderlayButton {
        return SwipeHelper.UnderlayButton(
                this!!.mContext!!,
                "Delete",
                14.0f,
                android.R.color.holo_red_light,
                object : SwipeHelper.UnderlayButtonClickListener {
                    override fun onClick() {
                        dogViewModel.delete(itemlist.get(position))
                        Toast.makeText(mContext,"Deleted photo_item $position",Toast.LENGTH_SHORT).show()
                    }
                })
    }
    

    private fun deleteDialog(dogs: Dog, type: Int) {
        var builder = AlertDialog.Builder(requireContext())

        when (type) {
            1 -> {
                builder.setMessage("선택 내용을 삭제할까요?")
                        .setNegativeButton(" 아뇨") { _, _ ->
                        }
                        .setPositiveButton("네") { _, _ ->
                            dogViewModel.delete(dogs)
                        }
            }
            2 -> {
                builder.setMessage("전체 삭제할까요?")
                        .setNegativeButton(" 아뇨") { _, _ ->
                        }
                        .setPositiveButton("네") { _, _ ->
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
        if (instance != null) {
            instance = null
        }


    }


    companion object {

        var instance: SearchFragment? = null
        fun searchinstance(): SearchFragment? {
            return instance
        }
    }
}

