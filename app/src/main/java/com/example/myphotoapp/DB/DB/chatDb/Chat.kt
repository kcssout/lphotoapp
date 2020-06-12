package com.example.myphotoapp.DB.DB.chatDb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat")
class Chat(@PrimaryKey(autoGenerate = true) var id: Long?,
          @ColumnInfo(name = "uname") var uname: String?,
          @ColumnInfo(name = "message") var message: String?,
          @ColumnInfo(name = "photo") var photo: ByteArray?

){
    constructor(): this(null,null,null,null)
}