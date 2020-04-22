package com.example.myphotoapp.DB.Repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.myphotoapp.DB.Dao.DogDao
import com.example.myphotoapp.DB.Dog
import com.example.myphotoapp.DB.DogDB

class dogRespository(application: Application){

    private val dogDatabase = DogDB.getInstance(application)!!
    private val dogDao: DogDao = dogDatabase.dogDao()
    private val LiveDog: LiveData<List<Dog>> = dogDao.getAll()

    fun getAll(): LiveData<List<Dog>> {
        return LiveDog
    }

    fun insert(dog: Dog) {
        dogDao.insert(dog)
    }

    fun delete(dog: Dog){
        dogDao.delete(dog)
    }
}