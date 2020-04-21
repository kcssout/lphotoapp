package com.example.myphotoapp.DB.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myphotoapp.DB.Dog

@Dao
interface DogDao {

    @Query("SELECT * FROM dog")
    fun getAll(): List<Dog>

    @Insert
    fun insert(dog : Dog)

    @Query("Delete from dog")
    fun deleteAll()

}