package com.example.myphotoapp.DogView

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myphotoapp.R
class SubRvAdapter(val context: Context, val dogList: ArrayList<Dog>) : RecyclerView.Adapter<SubRvAdapter.Holder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.sub_rv_item,parent, false)
        return Holder(view)  //itemclick
    }

    override fun getItemCount(): Int {
        return dogList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(dogList[position], context)
    }

    class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!){    //holder 생성 itemclick 추가
        val dogPhoto = itemView?.findViewById<ImageView>(R.id.dogPhotoImg)
        val dogBreed = itemView?.findViewById<TextView>(R.id.dogBreedTv)
        val dogAge = itemView?.findViewById<TextView>(R.id.dogAgeTv)
        val dogGender = itemView?.findViewById<TextView>(R.id.dogGenderTv)


        fun bind(dog: Dog,context: Context){
            if(dog.photo != ""){
                val resourceId = context.resources.getIdentifier(dog.photo, "drawable", context.packageName)
                dogPhoto?.setImageResource(resourceId)
            }
            else{
                dogPhoto?.setImageResource(R.mipmap.ic_launcher)
            }


            //나머지 데이터는 스트링과 연결
            dogBreed?.text = dog.breed
            dogAge?.text = dog.age
            dogGender?.text = dog.gender


            itemView.setOnClickListener {
                view -> Toast.makeText(context,  "개의 품종은 ${dog.breed} 이며, 나이는 ${dog.age}세이다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

}