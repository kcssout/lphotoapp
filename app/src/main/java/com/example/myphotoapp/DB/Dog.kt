package com.example.myphotoapp.DB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dog")
class Dog(@PrimaryKey(autoGenerate = true) var id: Long?,
          @ColumnInfo(name = "breed") val breed: String?,
          @ColumnInfo(name = "gender") val gender: String?,
          @ColumnInfo(name = "age") val age: String?,
          @ColumnInfo(name = "photo") val photo: ByteArray?
    )
{
    constructor(): this(null,null,null,null,null)
}