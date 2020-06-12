package com.example.myphotoapp.Adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myphotoapp.R
import kotlinx.android.synthetic.main.chat_rv_item.view.*

class roomRvAdapter(val context: Context, var chatroomList: MutableList<String>) : RecyclerView.Adapter<roomRvAdapter.Holder>(){
    var roomList: MutableList<String>? = null
    var TAG = "roomRvAdapter"

    init {
        this.roomList = chatroomList
    }

    inner class Holder(view : View) : RecyclerView.ViewHolder(view) {
        var uname = view.uname
        var message = view.message
        var PhotoImg = view.PhotoImg

        fun bind(chatlist:  String, context: Context) {

            uname.text= chatlist

//            if (chatlist.photo != null) {
//                var bitmap = BitmapFactory.decodeByteArray (chatlist.photo, 0, chatlist.photo!!.size )
//                PhotoImg?.setImageBitmap(bitmap)
//            } else {
//                PhotoImg?.setImageResource(R.mipmap.ic_launcher)
//            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        var v = LayoutInflater.from(context).inflate(R.layout.chat_rv_item, parent, false)
        return Holder(v);
    }

    override fun getItemCount(): Int {
        return roomList!!.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(this.roomList!![position],context)
    }

    fun add(chatroom : MutableList<String>){
        Log.d(TAG, "roomList > "+ chatroom)
        roomList!!.add(chatroom.toString())

        notifyDataSetChanged()
    }

}