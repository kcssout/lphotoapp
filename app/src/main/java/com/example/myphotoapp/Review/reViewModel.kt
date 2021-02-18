package com.example.myphotoapp.Review

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.myphotoapp.DB.DB.Review
import com.example.myphotoapp.DB.Repository.reviewRespository


class reViewModel(application : Application) : AndroidViewModel(application){

    private val respository = reviewRespository(application)
    private val reviews = respository.getAll()

    fun getAll() : LiveData<List<Review>> {
        return this.reviews
    }

    fun insert(re : Review){
        respository.insert(re)
    }

    fun delete(re : Review){
        respository.delete(re)
    }

    fun deleteAll(){
        respository.deleteAll()
    }

    override fun onCleared() {
        super.onCleared()
    }

}