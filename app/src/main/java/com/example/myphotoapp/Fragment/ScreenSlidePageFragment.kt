package com.example.myphotoapp.Fragment
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myphotoapp.Adapter.cardRvAdapter
import com.example.myphotoapp.AddReviewActivity

import com.example.myphotoapp.Logger.Logf
import com.example.myphotoapp.R
import com.example.myphotoapp.DB.DB.Review
import com.example.myphotoapp.Review.reViewModel
import kotlinx.android.synthetic.main.activity_book.view.*
import kotlinx.android.synthetic.main.activity_dog.*


class ScreenSlidePageFragment : Fragment(){

    var TAG = "ScreenSlidePageFragment"
    var name=""
    var mAdapter : cardRvAdapter? = null
    var itemlist : MutableList<Review> = mutableListOf()
    private var reviewModel : reViewModel? = null
    
    fun newInstance(): ScreenSlidePageFragment
    {
        val args = Bundle()

        val frag = ScreenSlidePageFragment()
        frag.arguments = args

        return frag
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val v = inflater.inflate(R.layout.activity_book, container, false)
//        v.tv.text= name
        var addbtn = v.addbtn
        addbtn.setOnClickListener {
            val i = Intent(requireContext(), AddReviewActivity::class.java)
            startActivity(i)
        }
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        for(i in 1..10){
            var review = Review()
            review.title = "aaa"
            review.content= "stri"
//            review.like= i
            itemlist.add(review)

            Log.d(TAG, review.toString())

        }

        mAdapter = cardRvAdapter(requireContext(),itemlist) //itemlist as ArrayList<Dog>, { dog -> deleteDialog(dog, 1) }, { dog -> deleteDialog(dog, 2) }
        mRecyclerView.adapter = mAdapter            //mRecyclerView 아이디 가져와서 연결

        val lm =   GridLayoutManager(requireContext(), 2);
        mRecyclerView.layoutManager = lm;
        mRecyclerView.itemAnimator = DefaultItemAnimator()
        mRecyclerView.setHasFixedSize(true)

        reviewModel = ViewModelProvider(this).get(reViewModel::class.java)

        reviewModel!!.getAll().observe(viewLifecycleOwner, Observer<List<Review>> {
            //update UI
            review-> mAdapter!!.setDogsData(review)
            Logf.v("reViewModel", "test")
        })

    }

    override fun onDestroy() {
//        ReviewDB.destroyInsatance()

        super.onDestroy()
    }
}