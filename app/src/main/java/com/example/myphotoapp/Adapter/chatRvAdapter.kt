package com.example.myphotoapp.Adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myphotoapp.DB.chatDb.Chat
import com.example.myphotoapp.R
import kotlinx.android.synthetic.main.chat_rv_item.view.*

class chatRvAdapter(val context: Context, var chatitemlist: MutableList<Chat>) : RecyclerView.Adapter<chatRvAdapter.Holder>(){
    private var chatList:  MutableList<Chat>? = null

    init {
        this.chatList = chatitemlist
    }

    inner class Holder(view : View) : RecyclerView.ViewHolder(view) {
        var uname = view.uname
        var message = view.message
        var PhotoImg = view.PhotoImg

        fun bind(chatlist: Chat) {
            uname.text= chatlist.name
            message.text = chatlist.message

            // time, profileuri

            if (chatlist.photo != null) {
                var bitmap = BitmapFactory.decodeByteArray (chatlist.photo, 0, chatlist.photo!!.size )
                PhotoImg?.setImageBitmap(bitmap)
            } else {
                PhotoImg?.setImageResource(R.mipmap.ic_launcher)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        var v = LayoutInflater.from(context).inflate(R.layout.chat_rv_item, parent, false)
        return Holder(v);
    }

    override fun getItemCount(): Int {
        return chatList!!.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(this.chatList!![position])
    }

    fun add(chat : Chat){
        chatList!!.add(chat)
        Log.d("chatRvAdapter"," $ "+chat.message + chat.time)
        notifyDataSetChanged()
    }

    fun datachange(){
        notifyDataSetChanged()
    }
}