package com.example.myphotoapp.Card

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.myphotoapp.DB.DB.Card
import com.example.myphotoapp.DogView.DogDB
import com.example.myphotoapp.DogView.ViewModel.DogViewModel
import com.example.myphotoapp.Logger.Logf
import com.example.myphotoapp.R
import kotlinx.android.synthetic.main.add_detail_dog.*
import java.io.ByteArrayOutputStream
import java.io.IOException


class cardAddActivity : AppCompatActivity() {

    private lateinit var cardViewModel: DogViewModel //card

    private var cardDb: DogDB? = null
    private var cardPhoto: Drawable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_detail_dog)
        cardDb = DogDB.getInstance(this)
        cardViewModel = ViewModelProvider(this).get(DogViewModel::class.java)


        btn_phview.setOnClickListener {
            takeAlbum()
        }

        btn_confirm.setOnClickListener {
            insertNewDog()
        }
    }


    fun insertNewDog() {

        val newCard = Card()
//        newCard.title =
//        newCard.age = etDogAge.text.toString()
//        newCard.breed = etDogName.text.toString()
//        newCard.gender = etDogGender.text.toString()
//        newCard.photo = getByteArrayFromDrawable(dogPhoto)
//
//        dogViewModel.insert(newDog)
//        Logf.v(TAG, newDog.toString())
//        finish()
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

                var bitmap: Bitmap? = null
                if (extras != null) {

                    try {
                        if (android.os.Build.VERSION.SDK_INT >= 29) {
                            // To handle deprication use
                            bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(contentResolver, extras))
                        } else {
                            // Use older version
                            bitmap = MediaStore.Images.Media.getBitmap(contentResolver, extras)
                        }
                        img_view.setImageBitmap(bitmap)
                        cardPhoto = BitmapDrawable(resources, bitmap)
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

        Intent(Intent.ACTION_GET_CONTENT).setType("*/*").also {
            startActivityForResult(Intent.createChooser(it, "Get Album"), PICK_FROM_ALBUM)
        }
    }


    fun getByteArrayFromDrawable(d: Drawable?): ByteArray? {
        var data: ByteArray? = null
        if (d != null) {
            val bitmap = (d as BitmapDrawable).bitmap

            val resizedBitmap =  Bitmap.createScaledBitmap(bitmap, bitmap.width/10, bitmap.height/10, true);
            val stream = ByteArrayOutputStream()
            resizedBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream) //png로하면 배경색이 투명, Jpg로하면 배경색이 검정
            data = stream.toByteArray() // blob

            Logf.v(TAG, data.size.toString())
            return data
        } else {
            return data
        }

    }

    companion object {
        private val TAG = cardAddActivity::class.java.simpleName
        private val PICK_FROM_ALBUM = 0
    }
}