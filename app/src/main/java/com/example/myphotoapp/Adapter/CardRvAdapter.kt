package com.example.myphotoapp.Adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myphotoapp.DB.DB.Card
import com.example.myphotoapp.R

class CardRvAdapter(val context: Context, var cardsList: MutableList<Card>) : RecyclerView.Adapter<CardRvAdapter.Holder>() {

    private var cardsSearchList: List<Card>? = null

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {    //holder 생성 itemclick 추가
        val title = itemView!!.findViewById<TextView>(R.id.c_title)
        val like = itemView!!.findViewById<TextView>(R.id.c_like)
        val image = itemView!!.findViewById<ImageView>(R.id.c_image)
        val reply = itemView!!.findViewById<TextView>(R.id.c_reply)



        fun bind(cards: Card, context: Context) {
            if (cards.image != null) {
                var bitmap = BitmapFactory.decodeByteArray (cards.image, 0, cards.image!!.size )
                image?.setImageBitmap(bitmap)
            } else {
                image?.setImageResource(R.mipmap.ic_launcher)
            }



            //나머지 데이터는 스트링과 연결
            title?.text = cards.title
            like?.text = cards.like.toString()
            reply?.text = cards.replyMessage

//            itemView.setOnClickListener {
//                dogItemClick(dogs)
//            }
//            itemView.setOnLongClickListener {
//                dogItemLongClick(dogs)
//                true
//            }

        }

    }

    init {
        this.cardsSearchList = cardsList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        var v = LayoutInflater.from(context).inflate(R.layout.sub_rv_album, parent, false)
        return Holder(v)
    }

    override fun getItemCount(): Int {
        return cardsList!!.size
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder!!.bind(this!!.cardsSearchList!![position], context)
    }
}