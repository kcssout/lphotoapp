package com.example.myphotoapp.DB.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.myphotoapp.DB.DB.Review


@Dao
interface ReviewDao {

    @Query("SELECT * FROM review ")//ORDER BY title ASC
    fun getAll(): LiveData<List<Review>>

    @Insert
    fun insert(review : Review)

    @Query("Delete from review")
    fun deleteAll()

    @Delete
    fun delete(review : Review)

}