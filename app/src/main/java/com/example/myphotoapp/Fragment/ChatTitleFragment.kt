package com.example.myphotoapp.Fragment

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.example.myphotoapp.Adapter.roomRvAdapter
import com.example.myphotoapp.G
import com.example.myphotoapp.R
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_chatroomlist.view.*
import kotlinx.android.synthetic.main.chat_enter.*
import kotlinx.android.synthetic.main.chat_enter.view.*
import java.text.SimpleDateFormat
import java.util.*


class ChatTitleFragment : Fragment() {

    val TAG: String = "ChatFragment"
    var chatroomList: MutableList<String> = mutableListOf()
    var mAdapter: roomRvAdapter? = null
    //    private lateinit var dogViewModel: DogViewModel
    var SearchView: SearchView? = null
    val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    val databaseReference: DatabaseReference = firebaseDatabase.getReference()
    var mcontext : Context? = null

    var preferences: SharedPreferences? = null
    var editor : SharedPreferences.Editor? = null
    var isFirst = true //앱을 처음 실행한 것인가?
    var isChanged = false //프로필을 변경한 적이 있는가?

    var etName : EditText? = null
    var ivProfile : CircleImageView? = null
    var imgUri : Uri? = null
    var enterBtn : Button? = null

    fun newInstance(): ChatTitleFragment {
        val args = Bundle()

        val frag = ChatTitleFragment()
        frag.arguments = args

        return frag
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.chat_enter, container, false)
        mcontext = inflater.context

        preferences=  mcontext!!.getSharedPreferences("account", MODE_PRIVATE)
        editor = preferences!!.edit()
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { //onCreateView 에 생성하면 addFragment 오류 뜬다... 5/15

        etName = view.et_name
        ivProfile = view.iv_profile
        enterBtn= view.enterBtn

        loadData();
        if(G.nickName!=null){
            etName!!.setText(G.nickName);
            Picasso.get().load(G.profileUri).into(ivProfile);

            //처음이 아니다, 즉, 이미 접속한 적이 있다.
            isFirst=false;

        }

        enterBtn!!.setOnClickListener {
            Log.d(TAG, "왜안돼1 "+ isChanged +  isFirst)
            //ChatActivity로 전환
            var intent= Intent(context, ChatActivity::class.java)
            startActivity(intent);
            if(!isChanged && !isFirst){
                Log.d(TAG, "왜안돼")


            }else{
                saveData();
            }
        }

//        var chatroomview = view.rv_chattingroom
//        var createbutton = view.bt_create
//        var editTitle = view.et_ChatRoomTitle

//        mAdapter = roomRvAdapter(requireContext(), chatroomList)
//        chatroomview.adapter = mAdapter
//
//        val lm = LinearLayoutManager(requireContext())  //아래는 이전 recylcerview랑 같음 RecyclerView: No layout manager attached; skipping layout
//        chatroomview.layoutManager = lm;
//        chatroomview.itemAnimator = DefaultItemAnimator()
//        chatroomview.setHasFixedSize(true)
//
//        createbutton.setOnClickListener { view ->
//
//            var roomTitle = editTitle.text.toString()
//            databaseReference.child("room_title").push().setValue(roomTitle)
//
//            editTitle.setText("")
//            var go = Intent(requireContext(), Chatroom::class.java)
//            startActivity(go)
//
//
//        }
//
//        databaseReference.root.addValueEventListener(object : ValueEventListener {
//            override fun onCancelled(p0: DatabaseError) {
//            }
//
//            override fun onDataChange(data: DataSnapshot) {
//                var set : MutableList<String> = mutableListOf()
//                var i = data.children.iterator()
//
//                while (i.hasNext()) {
//                    set.add(i.next().key!!)
//                }
//
//                chatroomList.clear()
//                chatroomList.addAll(set)
//                mAdapter!!.add(chatroomList)
//
//
//            }
//
//        })

    }

    fun clickImage(view : View?){

        var intent = Intent(Intent.ACTION_PICK)
        intent.setType("image/*")
        startActivityForResult(intent, 10)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            10->
                if(resultCode == RESULT_OK){
                    imgUri = data!!.data
//                    Glide.with(this@ChatTitleFragment).load(imgUri).into(ivProfile!!) 글라이드는 이미지를 읽어와서 보여줄때 내 device의 퍼미션이 요구되서 퍼미션 없을시 이미지가 보이지않음
                    Picasso.get().load(imgUri).into(ivProfile!!)
                    isChanged=true;
                }
        }
    }
    fun saveData(){
        G.nickName = etName!!.text.toString()

        if(imgUri == null){
            return
        }

        //Firebase storage에 이미지 저장 위해 파일명(날짜)
        val sdf = SimpleDateFormat("yyyyMMddhhmmss")
        var fileName = sdf.format(Date())+".png"

        //Firebase storage에 저장
        var fbs = FirebaseStorage.getInstance()
        val imgRef = fbs.getReference("profileImages/"+fileName);

        //파일 업로드

        var uploadTask = imgRef.putFile(imgUri!!)
        uploadTask.addOnSuccessListener(OnSuccessListener {
            it->
            imgRef.downloadUrl.addOnSuccessListener {
                G.profileUri= it.toString()
                Toast.makeText(mcontext,"프로필 저장완료", Toast.LENGTH_SHORT)

                var firebaseDatabase = FirebaseDatabase.getInstance()
                var profileRef = firebaseDatabase.getReference("profiles")

                //닉네임을 key 식별자로 하고 프로필 이미지의 주소를 값으로 저장
                profileRef.child(G.nickName).setValue(G.profileUri)

                //내 폰에 저장
                //2. 내 phone에 nickName, profileUrl을 저장

                editor!!.putString("nickName", G.nickName)
                editor!!.putString("profileUrl", G.profileUri)
                editor!!.commit();

                var intent = Intent(context, ChatActivity::class.java)
                startActivity(intent)

            }
        })
    }
    fun loadData(){
        G.nickName=preferences!!.getString("nickName", null);
        G.profileUri=preferences!!.getString("profileUri", null);
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