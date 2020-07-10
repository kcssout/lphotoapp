package com.example.myphotoapp.Fragment
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import com.example.myphotoapp.Adapter.CardRvAdapter
import com.example.myphotoapp.DB.DB.Card
import com.example.myphotoapp.DogView.DogAddActivity
import com.example.myphotoapp.R
import kotlinx.android.synthetic.main.activity_book.view.*
import kotlinx.android.synthetic.main.activity_sub.*


class ScreenSlidePageFragment : Fragment(){

    var TAG = "ScreenSlidePageFragment"
    var name=""
    var mAdapter : CardRvAdapter? = null
    var itemlist : MutableList<Card> = mutableListOf()

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
            val i = Intent(requireContext(), DogAddActivity::class.java)
            startActivity(i)
        }
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        card.replyNum=0
        for(i in 1..10){
            var card = Card()
            card.title = "aaaaa"

            card.replyMessage= "string"
            card.like= i.toLong()
            Log.d(TAG, i.toString())
            itemlist.add(card)

        }

        mAdapter = CardRvAdapter(requireContext(),itemlist) //itemlist as ArrayList<Dog>, { dog -> deleteDialog(dog, 1) }, { dog -> deleteDialog(dog, 2) }
        mRecyclerView.adapter = mAdapter            //mRecyclerView 아이디 가져와서 연결

        val lm =   GridLayoutManager(requireContext(), 2);

//        val lm = LinearLayoutManager(requireContext())  //아래는 이전 recylcerview랑 같음

//        lm.setSpanSizeLookup(object : SpanSizeLookup() {  //ui나누기
//            override fun getSpanSize(position: Int): Int {
//                val gridPosition = position % 5
//                when (gridPosition) {
//                    0, 1, 2 -> return 2
//                    3, 4 -> return 3
//                }
//                return 0
//            }
//        })

        mRecyclerView.layoutManager = lm;
        mRecyclerView.itemAnimator = DefaultItemAnimator() as RecyclerView.ItemAnimator?
        mRecyclerView.setHasFixedSize(true)



//        dogViewModel = ViewModelProvider(this).get(DogViewModel::class.java)
//
//        dogViewModel.getAll().observe(viewLifecycleOwner, Observer<List<Dog>> {
//            //update UI
//            dog-> mAdapter!!.setDogsData(dog)
//            Logf.v("dogViewModel", "test")
//        })

    }
}