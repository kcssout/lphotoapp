package com.example.myphotoapp.Fragment

import android.os.Message
import android.os.Parcel
import android.os.Parcelable
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.myphotoapp.G
import com.example.myphotoapp.MessageItem
import com.example.myphotoapp.R

class ChatAdapter() : BaseAdapter(), Parcelable {
    var messageItems : ArrayList<MessageItem>? = null
    var layoutInflater : LayoutInflater?= null

    constructor(messageItems : ArrayList<MessageItem>, layoutInflater: LayoutInflater) : this() {
        this.messageItems = messageItems
        this.layoutInflater = layoutInflater
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): Nothing? {
        var item = messageItems!!.get(p0)

        var itemView = null;

        //메세지가 내 메세지인지??
        if(item.name.equals(G.nickName)){
            itemView= layoutInflater!!.inflate(R.layout.my_msgbox,p2,false) as Nothing?;
        }else{
            itemView= layoutInflater!!.inflate(R.layout.other_msgbox,p2,false) as Nothing?;
        }
        return itemView

    }

    override fun getItem(p0: Int): Any {
        return messageItems!!.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return messageItems!!.size
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ChatAdapter> {
        override fun createFromParcel(parcel: Parcel): ChatAdapter {
            return ChatAdapter(parcel)
        }

        override fun newArray(size: Int): Array<ChatAdapter?> {
            return arrayOfNulls(size)
        }
    }

    constructor(parcel: Parcel) : this(){

    }

}