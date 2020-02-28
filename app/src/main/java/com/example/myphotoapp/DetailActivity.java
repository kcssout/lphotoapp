package com.example.myphotoapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myphotoapp.DB.DbOpenHelper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener, ActivityDataListener {
    private static String TAG = DetailActivity.class.getSimpleName();
    private static final int PICK_FROM_ALBUM = 0;
    private static final int DetailUserData = 001;
    EditText et1, et2;
    Button btn_confirm, btn_phview;
    ImageView img_view;
    SQLiteDatabase db;
    DbOpenHelper dbopen;
    private Context mContext;
    private String title, content;
    private Uri ImageUri;
    private Bitmap mBitmap;
    private Drawable dImage;
    private MainActivity mMainActivity;
    private ArrayList<User> mUserlist;
    private DataManager dataManager = DataManager.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_detail);
        mContext = getApplicationContext();
        dbopen = new DbOpenHelper(mContext);
        dbopen.open();

        initButton();
        mMainActivity = MainActivity.getInstance();
    }

    public void initButton() {
        et1 = findViewById(R.id.etview1);
        et2 = findViewById(R.id.etview2);
        btn_phview = findViewById(R.id.btn_phview);
        btn_confirm = findViewById(R.id.btn_confirm);
        img_view = findViewById(R.id.img_view);

        btn_confirm.setOnClickListener(this);
        btn_phview.setOnClickListener(this);
        et1.setOnClickListener(this);

        et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d(TAG, editable.toString());
                title = editable.toString();
            }
        });
        et2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d(TAG, editable.toString());
                content = editable.toString();
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.etview1:
                Log.d(TAG, "etview1");
                break;

            case R.id.etview2:
                Log.d(TAG, "etview2");
                break;
            case R.id.btn_phview:
                takeAlbum();
                break;
            case R.id.btn_confirm:
                Log.d(TAG, "btn_confirm");
                mUserlist= new ArrayList<>();
                if (title != null && content != null) {
                    dbopen.insert(title, content, getByteArrayFromDrawable(dImage));

//                    Intent inserdb = new Intent(this, MainActivity.class);
//                    mUserlist = dbopen.read();
//                    inserdb.putExtra("userlist", mUserlist);
//                    startActivityForResult(inserdb,DetailUserData);

                    dbopen.close();
//                    ((MainActivity)getParent()).cycleList(getApplicationContext());
                    MainActivity.instance.cycleList(mContext);
//                    MainActivity.instance=null;

                } else {
                    Toast.makeText(this, "데이터가 비어있습니다.", Toast.LENGTH_LONG).show();
                }

//                MainActivity.getInstance().recyclerAdapter.notifyDataSetChanged();
                break;
            case R.id.img_view:
                Log.d(TAG, "img_view");

                break;
            default:
                Log.d(TAG, "default");
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
                Log.d(TAG, "PICK_FROM_ALBUM");

                Uri extras = data.getData();
                Log.d(TAG, extras.toString());


                if (extras != null) {
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), extras);
                        img_view.setImageBitmap(bitmap);
                        dImage = new BitmapDrawable(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }


                break;
            default:
                break;

        }
    }

    public byte[] getByteArrayFromDrawable(Drawable d) {
        byte[] data = null;
        if (d != null) {
            Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream); //png로하면 배경색이 투명, Jpg로하면 배경색이 검정
            data = stream.toByteArray(); // blob
            return data;
        } else {
            return data;
        }

    }

    @Override
    public void onActivityMessageReceived(int actionCode, Bundle data) {

    }
}
