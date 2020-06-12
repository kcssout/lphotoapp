package com.example.myphotoapp.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myphotoapp.Adapter.roomRvAdapter
import com.example.myphotoapp.DB.DB.chatDb.Chat
import com.example.myphotoapp.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chatroomlist.view.*
import kotlin.collections.HashSet


class ChatTitleFragment : Fragment() {

    val TAG: String = "ChatFragment"
    var chatroomList: MutableList<String> = mutableListOf()
    var mAdapter: roomRvAdapter? = null
    //    private lateinit var dogViewModel: DogViewModel
    var SearchView: SearchView? = null
    val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    val databaseReference: DatabaseReference = firebaseDatabase.getReference()

    fun newInstance(): ChatTitleFragment {
        val args = Bundle()

        val frag = ChatTitleFragment()
        frag.arguments = args

        return frag
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.activity_chatroomlist, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { //onCreateView 에 생성하면 addFragment 오류 뜬다... 5/15


        var chatroomview = view.rv_chattingroom
        var createbutton = view.bt_create
        var editTitle = view.et_ChatRoomTitle

        mAdapter = roomRvAdapter(requireContext(), chatroomList)
        chatroomview.adapter = mAdapter

        val lm = LinearLayoutManager(requireContext())  //아래는 이전 recylcerview랑 같음 RecyclerView: No layout manager attached; skipping layout
        chatroomview.layoutManager = lm;
        chatroomview.itemAnimator = DefaultItemAnimator()
        chatroomview.setHasFixedSize(true)

        createbutton.setOnClickListener { view ->

            var roomTitle = editTitle.text.toString()
            databaseReference.child("room_title").push().setValue(roomTitle)

            editTitle.setText("")
            var go = Intent(requireContext(), Chatroom::class.java)
            startActivity(go)


        }

        databaseReference.root.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(data: DataSnapshot) {
                var set : MutableList<String> = mutableListOf()
                var i = data.children.iterator()

                while (i.hasNext()) {
                    set.add(i.next().key!!)
                }

                chatroomList.clear()
                chatroomList.addAll(set)
                mAdapter!!.add(chatroomList)


            }

        })

    }

    override fun onDestroy() {
        super.onDestroy()
    }

}

//        dogViewModel = ViewModelProvider(this).get(DogViewModel::class.java)
//
//        dogViewModel.getAll().observe(viewLifecycleOwner, Observer<List<Dog>> {
//            //update UI
//            dog-> mAdapter!!.setDogsData(dog)
//            Logf.v("dogViewModel", "test")
//        })


//    private fun deleteDialog(dogs : Dog, type : Int){
//        var builder = AlertDialog.Builder(requireContext())
//
//        when(type){
//            1 ->{
//                builder.setMessage("선택 내용을 삭제할까요?")
//                        .setNegativeButton(" 아뇨"){
//                            _,_->
//                        }
//                        .setPositiveButton("네"){_,_->
//                            dogViewModel.delete(dogs)
//                        }
//            }
//            2 ->{
//                builder.setMessage("전체 삭제할까요?")
//                        .setNegativeButton(" 아뇨"){
//                            _,_->
//                        }
//                        .setPositiveButton("네"){_,_->
//                            dogViewModel.deleteAll(dogs)
//                        }
//            }
//        }
//        builder.show()
//    }