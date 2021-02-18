package com.example.myphotoapp.DB.Repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.myphotoapp.DB.Dao.ReviewDao
import com.example.myphotoapp.DB.DB.Review
import com.example.myphotoapp.Review.ReviewDB

class reviewRespository(application: Application){

    private val reviewDatabase = ReviewDB.getInstance(application)!!

    private val reviewDao: ReviewDao = reviewDatabase.reviewDao()
    private val listReview: LiveData<List<Review>> = reviewDao.getAll()

    fun getAll(): LiveData<List<Review>> {
        return listReview
    }

    fun insert(re: Review) {
        reviewDao.insert(re)
    }

    fun delete(re: Review){
        reviewDao.delete(re)
    }

    fun deleteAll(){
        reviewDao.deleteAll()
    }
}