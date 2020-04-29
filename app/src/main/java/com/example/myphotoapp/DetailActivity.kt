package com.example.myphotoapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.example.myphotoapp.DB.DB.DbOpenHelper
import com.example.myphotoapp.DB.DB.User
import com.example.myphotoapp.Logger.Logf
import kotlinx.android.synthetic.main.edit_detail.*

import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.ArrayList

class DetailActivity : AppCompatActivity(), View.OnClickListener {


    internal var db: SQLiteDatabase? = null
    internal var dbopen: DbOpenHelper?= null
    private var mContext: Context? = null
    private var title: String? = null
    private var content: String? = null
    private val ImageUri: Uri? = null
    private val mBitmap: Bitmap? = null
    private var dImage: Drawable? = null
    private var mMainActivity: MainActivity? = null
    private var mUserlist: ArrayList<User>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_detail)
        mContext = applicationContext
        dbopen = DbOpenHelper(mContext)
        dbopen!!.open()

        initButton()
        mMainActivity = MainActivity.getInstance()
    }

    fun initButton() {

        btn_confirm.setOnClickListener(this)
        btn_phview.setOnClickListener(this)
        etview1.setOnClickListener(this)

        etview1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun afterTextChanged(editable: Editable) {
                Logf.v(TAG, editable.toString())
                title = editable.toString()
            }
        })
        etview2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun afterTextChanged(editable: Editable) {
                Logf.v(TAG, editable.toString())
                content = editable.toString()
            }
        })

    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.etview1 -> Logf.v(TAG, "etview1")

            R.id.etview2 -> Logf.v(TAG, "etview2")
            R.id.btn_phview -> takeAlbum()
            R.id.btn_confirm -> {
                Logf.v(TAG, "btn_confirm")
                mUserlist = ArrayList()
                if (title != null && content != null) {
                    if (dImage == null) {
                        dbopen!!.insert(title, content, null)
                    } else {
                        dbopen!!.insert(title, content, getByteArrayFromDrawable(dImage))
                    }


                    dbopen!!.close()
                    MainActivity.getInstance().cycleList(mContext!!)

                } else {
                    Toast.makeText(this, "데이터가 비어있습니다.", Toast.LENGTH_LONG).show()
                }
            }
            R.id.img_view -> Logf.v(TAG, "img_view")
            else -> Logf.v(TAG, "default")
        }
    }

    fun takeAlbum() {
        Logf.v(TAG, "takeAlbum")
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        startActivityForResult(intent, PICK_FROM_ALBUM)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK)
            return

        when (requestCode) {
            PICK_FROM_ALBUM -> {
                Logf.v(TAG, "PICK_FROM_ALBUM")

                val extras = data!!.data
                Logf.v(TAG, extras!!.toString())


                if (extras != null) {
                    try {
                        val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, extras)
                        img_view.setImageBitmap(bitmap)
                        dImage = BitmapDrawable(bitmap)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }


                }
            }
            else -> {
            }
        }
    }

    fun getByteArrayFromDrawable(d: Drawable?): ByteArray? {
        var data: ByteArray? = null
        if (d != null) {
            val bitmap = (d as BitmapDrawable).bitmap
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream) //png로하면 배경색이 투명, Jpg로하면 배경색이 검정
            data = stream.toByteArray() // blob
            return data
        } else {
            return data
        }

    }

    //    private byte[] getBlob(String image) {
    //        ByteArrayBuffer baf = new ByteArrayBuffer(500);
    //        try {
    //            String FILE_PATH1 = "sdcard/"
    //            File file = new File(FILE_PATH1, image + ".jpg");
    //            InputStream is = new FileInputStream(file);
    //            BufferedInputStream bis = new BufferedInputStream(is);
    //            int current = 0;
    //            while ((current = bis.read()) != -1) {
    //                baf.append((byte) current);
    //            }
    //            return baf.toByteArray();
    //        } catch (Exception e) {
    //            Log.d("ImageManager", "Error: " + e.toString());
    //        }
    //        return baf.toByteArray();
    //    }


    companion object {
        private val TAG = DetailActivity::class.java.simpleName
        private val PICK_FROM_ALBUM = 0
        private val DetailUserData = 1
    }
}
