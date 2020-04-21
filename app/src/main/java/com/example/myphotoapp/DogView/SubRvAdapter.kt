package com.example.myphotoapp.DogView

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.myphotoapp.R
class SubRvAdapter(val context: Context, val dogsList: MutableList<Dogs>, private val listener: ItemClickListener) : RecyclerView.Adapter<SubRvAdapter.Holder>(), Filterable {

    private var dogsSearchList: List<Dogs>? = null

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){    //holder 생성 itemclick 추가
        val dogPhoto = itemView!!.findViewById<ImageView>(R.id.dogPhotoImg)
        val dogBreed = itemView!!.findViewById<TextView>(R.id.dogBreedTv)
        val dogAge = itemView!!.findViewById<TextView>(R.id.dogAgeTv)
        val dogGender = itemView!!.findViewById<TextView>(R.id.dogGenderTv)


        fun bind(dogs: Dogs, context: Context){
            if(dogs.photo != null){
                val resourceId = context.resources.getIdentifier(dogs.photo.toString(), "drawable", context.packageName)
                dogPhoto?.setImageResource(resourceId)
            }
            else{
                dogPhoto?.setImageResource(R.mipmap.ic_launcher)
            }


            //나머지 데이터는 스트링과 연결
            dogBreed?.text = dogs.breed
            dogAge?.text = dogs.age
            dogGender?.text = dogs.gender


            itemView.setOnClickListener {
//                view -> Toast.makeText(context,  "개의 품종은 ${dog.breed} 이며, 나이는 ${dog.age}세이다.", Toast.LENGTH_SHORT).show()

                listener.onItemClicked(dogsSearchList!![adapterPosition])
            }
        }

    }

    init {
        this.dogsSearchList = dogsList
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.sub_rv_item,parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return dogsSearchList!!.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder!!.bind(this!!.dogsSearchList!![position], context)
    }



    interface ItemClickListener {
        fun onItemClicked(item : Dogs) {}

    }

    fun setAddItem(dogs :Dogs ){
        dogsList.add(dogs)
        this.dogsSearchList= dogsList
        notifyDataSetChanged()
    }

    //필터를 위한 코드
    override fun getFilter() : Filter{


      return  object : Filter(){
          override fun performFiltering(p0: CharSequence?): FilterResults {
            val charString = p0.toString()
              if(charString.isEmpty()){
                  dogsSearchList = dogsList
              }else{
                  val filteredList = ArrayList<Dogs>()
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

          override fun publishResults(p0: CharSequence, p1: FilterResults) {
              dogsSearchList = p1.values as ArrayList<Dogs>
              notifyDataSetChanged()
          }


      }


    }

}