package com.example.myphotoapp.DB.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myphotoapp.Logger.Logf;

import java.util.ArrayList;

public class DbOpenHelper {

    private static String TAG= "DbOpenHelper";
    private static final String DATABASE_NAME = "addressbook.db";
    private static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase mDB;
    private DatabaseHelper mDBHelper;
    private Context mCtx;
    private User mUser;

    ArrayList<User> userlist;

    private class DatabaseHelper extends SQLiteOpenHelper {

        // 생성자
        public DatabaseHelper(Context context, String name,
                              SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        // 최초 DB를 만들때 한번만 호출된다.
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DataBases.CreateDB._CREATE);
        }

        // 버전이 업데이트 되었을 경우 DB를 다시 만들어 준다.
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+DataBases.CreateDB._TABLENAME);
            onCreate(db);
        }
    }

    public DbOpenHelper(Context context){
        this.mCtx = context;
    }

    public DbOpenHelper open() throws SQLException {
        try{
            mDBHelper = new DatabaseHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
            mDB = mDBHelper.getWritableDatabase();
            mDB = mDBHelper.getReadableDatabase();
        }catch (Exception e ){
            e.printStackTrace();
        }
        return this;
    }

    public void insert(String title, String content , byte[] photo){
        mDB = mDBHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("TITLE", title);
        cv.put("CONTENT", content);
        cv.put("PHOTO", photo);

        mDB.insert("Memo", null, cv);

        Logf.v(TAG, "insert 완료");
    }

    public void delete(String idx){
        mDB = mDBHelper.getWritableDatabase();
        mDB.beginTransaction();
        mDB.execSQL("DELETE FROM Memo WHERE _ID='" + idx + "';");
        mDB.endTransaction();
        mDB.close();
        Logf.v(TAG, "delete 완료");
    }

    public ArrayList<User> read(){
        mDB = mDBHelper.getReadableDatabase();
        String sql = "Select * from Memo;";
        Cursor result = mDB.rawQuery(sql, null);
        userlist = new ArrayList<>();
        // result(Cursor 객체)가 비어 있으면 false 리턴
        result.moveToFirst();
        while(result.isAfterLast()==false){
            int index = result.getInt(0);  //id
            String title = result.getString(1);  //title
            String content = result.getString(2); //content
            byte[] image = result.getBlob(3); // byte
//            Toast.makeText(mCtx, "index= "+index+" title="+title+" content="+content, Toast.LENGTH_LONG).show();
            Logf.v(TAG, "index= "+index+" title="+title+" content="+content);
            mUser = new User(title, content, image);
            userlist.add(mUser);
            result.moveToNext();
        }

//        if(result.moveToFirst()){
//
//        }
        result.close();

        mDB.close();

        return userlist;
    }

    public void close(){
        mDB.close();
    }

}