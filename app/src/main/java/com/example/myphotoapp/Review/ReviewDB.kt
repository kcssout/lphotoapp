
package com.example.myphotoapp.Review

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myphotoapp.DB.DB.Review
import com.example.myphotoapp.DB.Dao.ReviewDao


@Database(entities = [Review::class], version = 1, exportSchema = false)
abstract class ReviewDB: RoomDatabase(){
    abstract fun reviewDao(): ReviewDao

    companion object{
        private var INSTANCE: ReviewDB? = null

        fun getInstance(context : Context): ReviewDB? {
            if(INSTANCE == null){
                synchronized(ReviewDB::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            ReviewDB::class.java, "review.db")
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