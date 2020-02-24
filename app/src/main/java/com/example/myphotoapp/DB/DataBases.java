package com.example.myphotoapp.DB;

import android.provider.BaseColumns;

// DataBase Table
public final class DataBases {

    public static final class CreateDB implements BaseColumns {
        public static final String TITLE = "title";
        public static final String CONTENT = "content";
        public static final String PHOTO = "photo";
        public static final String _TABLENAME = "Memo";
        public static final String _CREATE =
                "create table "+_TABLENAME+"("
                        +_ID+" integer primary key autoincrement, "
                        +TITLE+" text not null , "
                        +CONTENT+" text not null , "
                        +PHOTO+" blob );";
    }
}
