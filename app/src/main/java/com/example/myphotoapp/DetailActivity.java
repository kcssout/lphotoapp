package com.example.myphotoapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myphotoapp.DB.DbOpenHelper;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    private static String TAG= DetailActivity.class.getSimpleName();
    private static final int PICK_FROM_ALBUM = 0;
    EditText et1, et2;
    Button btn_confirm, btn_phview;
    ImageView img_view;
    SQLiteDatabase db;
    DbOpenHelper dbopen;
    private Context mContext;
    private String title, content;
    private Uri ImageUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_detail);
        mContext = this;

        dbopen = new DbOpenHelper(mContext);
        dbopen.open();

        initButton();
    }

    public void initButton() {
        et1 = findViewById(R.id.etview1);
        et2 = findViewById(R.id.etview2);
        btn_phview = findViewById(R.id.btn_phview);
        btn_confirm = findViewById(R.id.btn_confirm);
        img_view = findViewById(R.id.img_view);
        et1.setOnClickListener(this);
        et2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.etview1:
                title = et1.getText().toString();
                break;

            case R.id.etview2:
                content = et2.getText().toString();

                break;
            case R.id.btn_phview:
                takeAlbum();
                break;
            case R.id.btn_confirm:
                if (title != null && content != null) {
                    dbopen.insert(title, content, null);
                }
                break;
            case R.id.img_view:

                break;
            default:
                break;

        }
    }

    public void takeAlbum() {
        Log.d(TAG, "takeAlbum");
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK)
            return;

        switch (requestCode) {
            case PICK_FROM_ALBUM:
                ImageUri = data.getData();
                Log.d(TAG, ImageUri.getPath().toString());
                break;
            default:
                break;

        }
    }
}
