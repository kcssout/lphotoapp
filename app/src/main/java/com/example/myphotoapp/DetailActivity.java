package com.example.myphotoapp;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myphotoapp.DB.DbOpenHelper;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    EditText et1,et2;
    SQLiteDatabase db;
    DbOpenHelper dbopen;
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_detail);
        mContext = this;
        dbopen = new DbOpenHelper(mContext);

        dbopen.open();

        et1 = findViewById(R.id.etview1);
        et2 = findViewById(R.id.etview2);

        et1.setOnClickListener(this);
        et2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.etview1:
                et1.getText();
                break;

            case R.id.etview2:
                break;
        }

    }
}
