//package com.example.myphotoapp.SearchView
//
//import android.view.View
//import android.view.ViewGroup
//import android.widget.AdapterView
//import android.widget.Filter
//import android.widget.Filterable
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//
//class SearchAdapter(private val Context, private val excelList: MutableList<SearchData>, private val listener: AdapterView.OnItemClickListener) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>(), Filterable{
//
//    private var excelSearchList: List<SearchData>? = null
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.SearchViewHolder {
//    }
//
//    override fun getItemCount(): Int {
//    }
//
//    override fun onBindViewHolder(holder: SearchAdapter.SearchViewHolder, position: Int) {
//    }
//
//
//
//    override fun getFilter(): Filter {
//    }
//
//    inner class SearchViewHolder(view: View) : RecyclerView.ViewHolder(view){
//
//        val info: TextView
//        val quiz: TextView
//
//    }
//}