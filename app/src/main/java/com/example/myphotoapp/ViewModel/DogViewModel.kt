package com.example.myphotoapp.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.myphotoapp.DB.Dog
import com.example.myphotoapp.Repository.dogRespository

class DogViewModel(application : Application) : AndroidViewModel(application){

    private val respository = dogRespository(application)
    private val dogs = respository.getAll()

    fun getAll() : List<Dog> {
        return this.dogs
    }

    fun insert(dog : Dog){
        respository.insert(dog)
    }

    fun delete(dog : Dog){
        respository.delete(dog)
    }
}