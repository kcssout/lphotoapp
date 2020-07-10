package com.example.myphotoapp.DB.DB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Card")
class Card(
        @PrimaryKey(autoGenerate = true) var title: String?,
        @PrimaryKey(autoGenerate = true) var image: ByteArray?,
        @PrimaryKey(autoGenerate = true) var like: Long?,
        @PrimaryKey(autoGenerate = true) var replyMessage: String?,
        @PrimaryKey(autoGenerate = true) var replyNum: Long?
        )
{
    constructor(): this(null,null,null,null,null)

}