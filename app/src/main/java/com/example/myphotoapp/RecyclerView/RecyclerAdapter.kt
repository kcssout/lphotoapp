package com.example.myphotoapp.RecyclerView

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.example.myphotoapp.DB.DbOpenHelper
import com.example.myphotoapp.R
import com.example.myphotoapp.User

import java.lang.reflect.Array
import java.util.ArrayList

class RecyclerAdapter(var mContext: Context) : RecyclerView.Adapter<RecyclerAdapter.RcViewHolder>() {
    internal var viewHolder: RcViewHolder? = null
    internal var dbopen: DbOpenHelper? = null
    var users: ArrayList<User>? = null

    fun setItem(aa: ArrayList<User>) {
        this.users = aa
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RcViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rc_layout, parent, false)
        viewHolder = RcViewHolder(view)
        return viewHolder as RcViewHolder
    }

    override fun onBindViewHolder(holder: RcViewHolder, position: Int) {

        holder.img.setOnLongClickListener {
            Log.d(TAG, "pos : $position")
            dbopen = DbOpenHelper(mContext)
            dbopen!!.open()
            dbopen!!.delete(Integer.toString(position + 1))
            dbopen!!.close()
            notifyDataSetChanged()
            false
        }


        try {
            holder.textArtist.text = users!![position].artist
            holder.textTitle.text = users!![position].title
            val blob = users!![position].img
            val bmp = BitmapFactory.decodeByteArray(blob, 0, blob!!.size)
            holder.img.setImageBitmap(bmp)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun getItemCount(): Int {
        return if (users == null) {
            0
        } else users!!.size
    }

    fun UserDb(): ArrayList<User>? {
        val userlist: ArrayList<User>? = null
        dbopen = DbOpenHelper(mContext)
        dbopen!!.open()
        dbopen!!.read()
        dbopen!!.close()

        return userlist
    }

    inner class RcViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var img: ImageView
        var textTitle: TextView
        var textArtist: TextView

        init {
            this.img = view.findViewById(R.id.img)
            this.textTitle = view.findViewById(R.id.textTitle)
            this.textArtist = view.findViewById(R.id.textArtist)


        }
    }

    companion object {

        private val TAG = "RecyclerAdapter"
    }
}
