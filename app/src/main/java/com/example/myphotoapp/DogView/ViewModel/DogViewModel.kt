package com.example.myphotoapp.DogView.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.myphotoapp.DB.Dog
import com.example.myphotoapp.DB.Repository.dogRespository

class DogViewModel(application : Application) : AndroidViewModel(application){

    private val respository = dogRespository(application)
    private val dogs = respository.getAll()

    fun getAll() : LiveData<List<Dog>> {
        return this.dogs
    }

    fun insert(dog : Dog){
        respository.insert(dog)
    }

    fun delete(dog : Dog){
        respository.delete(dog)
    }
}