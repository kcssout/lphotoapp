package com.example.myphotoapp.DB.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import com.example.myphotoapp.DB.Dao.DogDao

@Database(entities = [Dog::class], version = 1, exportSchema = false)
abstract class DogDB: RoomDatabase(){
    abstract fun dogDao(): DogDao

    companion object{
        private var INSTANCE: DogDB? = null

        fun getInstance(context : Context): DogDB? {
            if(INSTANCE == null){
                synchronized(DogDB::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            DogDB::class.java, "dog.db")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build()
                }
            }


            return INSTANCE
        }

        fun destroyInsatance(){
            INSTANCE = null
        }

    }
}