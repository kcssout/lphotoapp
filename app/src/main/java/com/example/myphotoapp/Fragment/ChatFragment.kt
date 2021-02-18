package com.example.myphotoapp.Fragment

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myphotoapp.Adapter.chatRvAdapter
import com.example.myphotoapp.DB.chatDb.Chat
import com.example.myphotoapp.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chatroom.*
import java.util.*


class Chatroom : AppCompatActivity() {

    val TAG: String = "ChatFragment"
    var chatitemlist: MutableList<Chat> = mutableListOf()
    var mAdapter: chatRvAdapter? = null
    //    private lateinit var dogViewModel: DogViewModel
    var SearchView: SearchView? = null
    val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    val reference: DatabaseReference = firebaseDatabase.getReference()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatroom)

        var chatview = list
        var sendbutton = button
        var editText = editText

        mAdapter = chatRvAdapter(applicationContext, chatitemlist)
        chatview.adapter = mAdapter

        val lm = LinearLayoutManager(applicationContext)  //아래는 이전 recylcerview랑 같음 RecyclerView: No layout manager attached; skipping layout
        chatview.layoutManager = lm;
        chatview.itemAnimator = DefaultItemAnimator()
        chatview.setHasFixedSize(true)

        var userName = "user" + Random().nextInt(10000) // 랜덤한 유저 이름 설정 ex) user1234



        sendbutton.setOnClickListener { view ->
            var chatData = Chat()
            chatData.name = userName
            chatData.message = editText.text.toString()

            var map = HashMap<String, Any>();
            reference.updateChildren(map)

            var key = reference.child("room_title").push().key
            var root = reference.child(key!!)
            Log.d(TAG, "key : " + key + " , root : " + root)
            val chatMap = HashMap<String, Any>()
            chatMap.put("name", chatData.name!!)
            chatMap.put("message", chatData.message!!)
            root.updateChildren(chatMap)

            root.child("chatting").push().setValue(chatMap)    // 기본 database  message
//            reference.child("room_title")
            Log.d(TAG, "sendbutton >> " + chatMap.toString())

            editText.setText("")
        }


        val childEventListener = object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildAdded(db: DataSnapshot, text: String?) {

                val chatData: Chat? = db.getValue(Chat::class.java)// chatData를 가져오고

                if (chatData != null) {
                    Log.d(TAG, "onChildAdded(메시지입력) >> " + chatData.message + ", " + chatData.name)

                    mAdapter!!.add(chatData)
                }


            }

            override fun onChildRemoved(p0: DataSnapshot) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }

        reference.child("message").addChildEventListener(childEventListener)

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
    }
}