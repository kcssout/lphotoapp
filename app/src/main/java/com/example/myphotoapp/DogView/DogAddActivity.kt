package com.example.myphotoapp.DogView

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import com.example.myphotoapp.DB.Dog
import com.example.myphotoapp.DB.DogDB
import com.example.myphotoapp.Logger.Logf
import com.example.myphotoapp.R
import kotlinx.android.synthetic.main.add_detail_dog.*
import java.io.ByteArrayOutputStream
import java.io.IOException


class DogAddActivity : AppCompatActivity() {


    private var dogDb: DogDB? = null
    private var dogPhoto : Drawable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_detail_dog)
        dogDb = DogDB.getInstance(this)


        btn_phview.setOnClickListener {
            takeAlbum()
        }

        btn_confirm.setOnClickListener {
            insertNewDog()
        }
    }


    fun insertNewDog() {
        val mHandler = Handler(Looper.getMainLooper())

        val addRunnable = Runnable {
            val newDog = Dog()
            newDog.age = etDogAge.text.toString()
            newDog.breed= etDogName.text.toString()
            newDog.gender= etDogGender.text.toString()
            newDog.photo= getByteArrayFromDrawable(dogPhoto)

            var dogs = Dogs(newDog.age, newDog.gender, newDog.age, newDog.photo)
//            SubActivity.getInstance().dogList.add(dogs)

            SubActivity.getInstance().mAdapter!!.setAddItem(dogs)

            dogDb?.dogDao()?.insert(newDog)

            Logf.v(TAG, newDog.toString())


        }
        mHandler.postDelayed(addRunnable,0)
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
                        dogPhoto = BitmapDrawable(bitmap)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }


                }
            }
            else -> {
            }
        }
    }

    fun takeAlbum() {
        Logf.v(TAG, "takeAlbum")
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        startActivityForResult(intent, DogAddActivity.PICK_FROM_ALBUM)
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

    companion object {
        private val TAG = DogAddActivity::class.java.simpleName
        private val PICK_FROM_ALBUM = 0
    }
}