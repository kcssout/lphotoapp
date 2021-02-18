package com.example.myphotoapp.DB.chatDb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat")
class Chat(@PrimaryKey(autoGenerate = true) var id: Long?,
          @ColumnInfo(name = "name") var name: String?,
          @ColumnInfo(name = "message") var message: String?,
           @ColumnInfo(name = "time") var time: String?,
           @ColumnInfo(name = "profileUri") var profileUri: String?,

           @ColumnInfo(name = "photo") var photo: ByteArray?

){
    constructor(): this(null,null,null,null,null,null)
}
