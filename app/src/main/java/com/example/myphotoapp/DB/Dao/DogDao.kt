package com.example.myphotoapp.DB.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.myphotoapp.DB.Dog

@Dao
interface DogDao {

    @Query("SELECT * FROM dog ORDER BY breed ASC")
    fun getAll(): LiveData<List<Dog>>

    @Insert
    fun insert(dog : Dog)

    @Query("Delete from dog")
    fun deleteAll()

    @Delete
    fun delete(dogs : Dog)

}