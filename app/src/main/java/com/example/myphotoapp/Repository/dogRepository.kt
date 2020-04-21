package com.example.myphotoapp.Repository

import android.app.Application
import com.example.myphotoapp.DB.Dao.DogDao
import com.example.myphotoapp.DB.Dog
import com.example.myphotoapp.DB.DogDB

class dogRespository(application: Application){

    private val dogDatabase = DogDB.getInstance(application)!!
    private val dogDao: DogDao = dogDatabase.dogDao()
    private val LiveDog: List<Dog> = dogDao.getAll()

    fun getAll(): List<Dog> {
        return LiveDog
    }

    fun insert(dog: Dog) {
        dogDao.insert(dog)
    }

    fun delete(dog: Dog){
        dogDao.deleteAll()
    }
}