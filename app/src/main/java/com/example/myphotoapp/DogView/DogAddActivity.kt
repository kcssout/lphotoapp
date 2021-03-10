package com.example.myphotoapp.DogView

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.myphotoapp.DB.DB.Dog
import com.example.myphotoapp.DogView.ViewModel.DogViewModel
import com.example.myphotoapp.Logger.Logf
import com.example.myphotoapp.R
import kotlinx.android.synthetic.main.add_detail_dog.*
import kotlinx.android.synthetic.main.viewpage.*
import java.io.ByteArrayOutputStream
import java.io.IOException


class DogAddActivity : AppCompatActivity() {

    private lateinit var dogViewModel: DogViewModel

    private var dogDb: DogDB? = null
    private var dogPhoto: ArrayList<Drawable>? = null
    private var filelist : ArrayList<Bitmap>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_detail_dog)
        dogDb = DogDB.getInstance(this)
        dogViewModel = ViewModelProvider(this).get(DogViewModel::class.java)



        btn_phview.setOnClickListener {
            takeAlbum()
        }

        btn_confirm.setOnClickListener {
            insertNewDog()
        }
    }


    fun insertNewDog() {

        val newDog = Dog()
        newDog.age = etDogAge.text.toString()
        newDog.breed = etDogName.text.toString()
        newDog.gender = etDogGender.text.toString()
        newDog.photo = getByteArrayFromDrawable(dogPhoto)


        dogViewModel.insert(newDog)
        Logf.v(TAG, newDog.toString())
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK)
            return

        when (requestCode) {
            PICK_FROM_ALBUM -> {
                Logf.v(TAG, "PICK_FROM_ALBUM")
                filelist = ArrayList<Bitmap>()

                val extras = data!!.data
//                Logf.v(TAG, extras!!.toString())

                var bitmap: Bitmap? = null
                if (extras != null) {

                    if(data.clipData== null){
                        Log.v(TAG, "1. 하나만 선택 > "+ data.data);// 멀티 선택을 지원하지 않는 기기에서는 getClipdata()가 없음 => getData()로 접근해야 함

                        filelist!!.add(makebitmap(data.data!!))
                    }else{
                        var clipData = data.clipData
                        Log.v(TAG, "다중 선택 > " + data.clipData!!.itemCount)
                        if(clipData!!.itemCount >10){
                            Toast.makeText(applicationContext,"10개까지만 선택이 가능합니다.",Toast.LENGTH_SHORT).show()
                            return;
                        }
                        // 멀티 선택에서 하나만 선택했을 경우
                        else if (clipData.itemCount == 1) {
                            var dataStr = (clipData.getItemAt(0).getUri());
                            Log.i("2. clipdata choice", dataStr.toString());
                            Log.i("2. single choice", clipData.getItemAt(0).getUri().getPath());
                            filelist!!.add(makebitmap(dataStr));

                        } else if (clipData.getItemCount() > 1 && clipData.getItemCount() < 10) {
                            for (i in 0 until clipData.itemCount) {
                                var dataStr = clipData.getItemAt(i).getUri();
                                Log.i("3. single choice", dataStr.toString());
                                filelist!!.add(makebitmap(dataStr));
                            }
                        }
                    }

                    viewPager!!.adapter = ViewPagerAdapter(this,filelist)





                } else{
                    Toast.makeText(applicationContext,"사진 선택을 취소하였습니다.",Toast.LENGTH_SHORT).show()
                }
            }
            else -> {
            }
        }
    }

    fun takeAlbum() {
        Logf.v(TAG, "takeAlbum")

        Intent(Intent.ACTION_PICK).setType("*/*").putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true).also {
            startActivityForResult(Intent.createChooser(it, "Get Album"), PICK_FROM_ALBUM)
        }
    }

    fun makebitmap(uri : Uri) : Bitmap{
        var bitmap:Bitmap? =null
        try {
            if (android.os.Build.VERSION.SDK_INT >= 29) {
                // To handle deprication use
                bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(contentResolver, uri))

            } else {
                // Use older version
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
            }
//            img_view.setImageBitmap(bitmap)
            dogPhoto!!.add(BitmapDrawable(resources, bitmap))
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return bitmap!!
    }


    fun getByteArrayFromDrawable(d: ArrayList<Drawable>?): ArrayList<ByteArray>? {
        var data: ArrayList<ByteArray>? = null

        while(!d!!.isEmpty()){
            val bitmap = (d as BitmapDrawable).bitmap

            val resizedBitmap =  Bitmap.createScaledBitmap(bitmap, bitmap.width/10, bitmap.height/10, true);
            val stream = ByteArrayOutputStream()
            resizedBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream) //png로하면 배경색이 투명, Jpg로하면 배경색이 검정
            data!!.add(stream.toByteArray()) // blob

        }
        Logf.v(TAG, data!!.size.toString())
        return data;
    }

//    fun getImages(limit: Int? = null, offset: Int? = null): MutableList<PhotoItem> {
//        val photos: MutableList<PhotoItem> = mutableListOf()
//
//        val projection = arrayOf(
//                MediaStore.Images.Media.DATA,
//                MediaStore.Images.Media.DISPLAY_NAME
//        )
//
//        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
//        val order = MediaStore.Video.Media.DATE_TAKEN
//        val sortOrder =
//                if (limit == null) "$order DESC"
//                else "$order DESC LIMIT $limit OFFSET $offset"
//
//        val imageCursor = contentResolver.query(
//                uri, projection, null, null, sortOrder
//        )
//
//        if (imageCursor != null) {
//            val columnIndex = imageCursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
//            while (imageCursor.moveToNext()) {
//                val imageDataPath = imageCursor.getString(columnIndex)
//                photos.add(PhotoItem(imageDataPath))
//            }
//        }
//
//        return photos
//    }



    companion object {
        private val TAG = DogAddActivity::class.java.simpleName
        private val PICK_FROM_ALBUM = 0
    }
}