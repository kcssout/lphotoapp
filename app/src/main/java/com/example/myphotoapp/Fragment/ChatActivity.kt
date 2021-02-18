package com.example.myphotoapp.Fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myphotoapp.Adapter.chatRvAdapter
import com.example.myphotoapp.DB.chatDb.Chat
import com.example.myphotoapp.G
import com.example.myphotoapp.MessageItem
import com.example.myphotoapp.R
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.annotations.Nullable
import kotlinx.android.synthetic.main.activity_chat.*
import java.util.*


class ChatActivity : AppCompatActivity() {

    private val TAG = this::class.java.simpleName
    var messageItems: MutableList<Chat> = mutableListOf()//?
    var adapter : chatRvAdapter?= null

    var firebaseDatabase: FirebaseDatabase? = null
    var databaseReference: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        toolbar_title.text = G.nickName

        // 채팅 내용 RecyclerView Adapter
        val lm = LinearLayoutManager(applicationContext)  //아래는 이전 recylcerview랑 같음 RecyclerView: No layout manager attached; skipping layout
        rv_chatlist.layoutManager = lm
        rv_chatlist.itemAnimator = DefaultItemAnimator()
        rv_chatlist.setHasFixedSize(true)

        adapter = chatRvAdapter(applicationContext, messageItems)
        rv_chatlist.adapter = adapter

        adapter!!.notifyDataSetChanged()
        //Firebase DB관리 객체와 'chat' 노드 참조객체 얻어오기
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase!!.getReference()

        //firebaseDB에서 채팅 메시지를 실시간 읽어오기
        //'message'노드에 저장되어 있는 데이터들을 읽어오기
        //chatRef에 데이터가 변경되는 것을 듣는 리스너추가
        databaseReference!!.child("message").addChildEventListener(object : ChildEventListener {
            //새로 추가된 것만 줌 ValueListener는 하나의 값만 바뀌어도 처음부터 다시 값을 줌
            override fun onChildAdded(dataSnapshot: DataSnapshot, @Nullable s: String?) { //새로 추가된 데이터(값 : MessageItem객체) 가져오기
                val messageItem = dataSnapshot.getValue(Chat::class.java)!!
                Log.d(TAG, "Value is: ${messageItem.message}")
                //새로운 메세지를 리스뷰에 추가하기 위해 ArrayList에 추가
//                messageItems!!.add(messageItem)
                adapter!!.add(messageItem)

                //리스트뷰를 갱신

//                rv_chatlist.scrollToPosition(messageItems!!.size - 1)
//                listView.setSelection(messageItems!!.size - 1) //리스트뷰의 마지막 위치로 스크롤 위치 이동
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, @Nullable s: String?) {

            }
            override fun onChildRemoved(dataSnapshot: DataSnapshot) {}
            override fun onChildMoved(dataSnapshot: DataSnapshot, @Nullable s: String?) {}
            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }
    fun clickNav(view: View){
        finish()
    }

    fun clickSend(view: View) {
        //firebase DB에 저장할 값들( 닉네임, 메세지, 프로필 이미지URL, 시간)
        var nickName = G.nickName;  //사용자이름
        var message = et.getText().toString();
        var profileUri = G.profileUri;

        //메세지 작성 시간 문자열로..
        var calendar = Calendar.getInstance(); //현재 시간을 가지고 있는 객체
        var time = calendar.get(Calendar.HOUR_OF_DAY).toString() + ":" + calendar.get(Calendar.MINUTE); //시간 입력

        //firebase DB에 저장할 값(MessageItem객체) 설정
        var messageItem = MessageItem(nickName, message, time, profileUri);
        //'char'노드에 MessageItem객체를 통해
        databaseReference!!.child("message").push().setValue(messageItem)   //기본 database하위 message라는 child에 data를 만들기
                                //child 키를 제대로 지정해줘야 들어옴 meesage 하니깐 안들어오지;

        //EditText에 있는 글씨 지우기
        et.setText("");

        //소프트키패드를 안보이도록..
        view?.apply {
            var imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view!!.getWindowToken(), 0);
        }


        //처음 시작할때 EditText가 다른 뷰들보다 우선시 되어 포커스를 받아 버림.
        //즉, 시작부터 소프트 키패드가 올라와 있음.

        //그게 싫으면...다른 뷰가 포커스를 가지도록
        //즉, EditText를 감싼 Layout에게 포커스를 가지도록 속성을 추가!![[XML에]
    }
}
