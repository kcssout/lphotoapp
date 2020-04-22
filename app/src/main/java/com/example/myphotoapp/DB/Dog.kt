package com.example.myphotoapp.DB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dog")
class Dog(@PrimaryKey(autoGenerate = true) var id: Long?,
          @ColumnInfo(name = "breed") var breed: String?,
          @ColumnInfo(name = "gender") var gender: String?,
          @ColumnInfo(name = "age") var age: String?,
          @ColumnInfo(name = "photo") var photo: ByteArray?

){
    constructor(): this(null,null,null,null,null)
}