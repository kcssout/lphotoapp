package com.example.myphotoapp.DB.DB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "review")
class Review(@PrimaryKey(autoGenerate = true) var id: Long?,
          @ColumnInfo(name = "title") var title: String?,
          @ColumnInfo(name = "content") var content: String?,
          @ColumnInfo(name = "like") var like: Int?,
          @ColumnInfo(name = "photo") var photo: ByteArray?

){
    constructor(): this(null,null,null,0,null)
}