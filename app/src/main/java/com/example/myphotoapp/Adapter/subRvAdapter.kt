package com.example.myphotoapp.Adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.request.RequestOptions
import com.example.myphotoapp.DB.DB.Dog
import com.example.myphotoapp.R

class subRvAdapter(val context: Context, var dogsList: MutableList<Dog>, val dogItemClick: (Dog) -> Unit, val dogItemLongClick: (Dog) -> Unit) : RecyclerView.Adapter<subRvAdapter.Holder>(), Filterable {

    private var dogsSearchList: List<Dog>? = null

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {    //holder 생성 itemclick 추가
        val dogPhoto = itemView.findViewById<ImageView>(R.id.dogPhotoImg)
        val dogBreed = itemView.findViewById<TextView>(R.id.dogBreedTv)
        val dogAge = itemView.findViewById<TextView>(R.id.dogAgeTv)
        val dogGender = itemView.findViewById<TextView>(R.id.dogGenderTv)


        fun bind(dogs: Dog) {



            if (dogs.photo != null) {
//                var numbersIterator = dogs!!.photo!!.iterator()
//
//                while(numbersIterator.hasNext()){
//                    print(numbersIterator.next())
//                }


                var bitmap = BitmapFactory.decodeByteArray (dogs.photo, 0, dogs.photo!!.size )
//                dogPhoto?.setImageBitmap(bitmap)

                Glide.with(context).load(bitmap).apply(RequestOptions.circleCropTransform()).into(dogPhoto)
            } else {
                dogPhoto?.setImageResource(R.mipmap.ic_launcher)
            }


            //나머지 데이터는 스트링과 연결
            dogBreed?.text = dogs.breed
            dogAge?.text = dogs.age
            dogGender?.text = dogs.gender

            itemView.setOnClickListener {
                dogItemClick(dogs)
            }
            itemView.setOnLongClickListener {
                dogItemLongClick(dogs)
                true
            }
        }

    }

    init {
        this.dogsSearchList = dogsList
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.sub_rv_item, parent, false)


        return Holder(view)
    }

    override fun getItemCount(): Int {
        return dogsSearchList!!.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(this.dogsSearchList!![position])
    }

    //필터를 위한 코드
    override fun getFilter(): Filter {


        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val charString = p0.toString()
                if (charString.isEmpty()) {
                    dogsSearchList = dogsList
                } else {
                    val filteredList = ArrayList<Dog>()
                    for (row in dogsList) {
                        if (row.breed!!.toLowerCase().contains(charString.toLowerCase()) || row.gender!!.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row)
                        }
                    }
                    dogsSearchList = filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = dogsSearchList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(p0: CharSequence, p1: FilterResults) {
                dogsSearchList = p1.values as ArrayList<Dog>
                notifyDataSetChanged()
            }


        }
    }

    fun setDogsData(dogs: List<Dog>) {
        this.dogsSearchList = dogs
        dogsList = dogs.toMutableList()
        notifyDataSetChanged()
    }

}