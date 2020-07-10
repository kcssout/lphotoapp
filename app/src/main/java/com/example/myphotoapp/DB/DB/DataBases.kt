package com.example.myphotoapp.DB.DB

import android.provider.BaseColumns

// DataBase Table
class DataBases {
    object CreateDB : BaseColumns {
        const val TITLE = "title"
        const val CONTENT = "content"
        const val PHOTO = "photo"
        const val _TABLENAME = "Memo"
        const val _CREATE = ("create table " + _TABLENAME + "("
                + BaseColumns._ID + " integer primary key autoincrement, "
                + TITLE + " text not null , "
                + CONTENT + " text not null , "
                + PHOTO + " blob );")
    }
}