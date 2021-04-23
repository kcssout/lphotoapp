package com.example.myphotoapp.DogView

import android.graphics.Bitmap
import android.net.Uri

class FileInfo {
    val filename: String //파일이름
    val ext : String //확장자
    val uri : Uri
    val bitmap : Bitmap




    constructor(Filename: String?, ext: String, uri: Uri, bitmap: Bitmap) {
        this.filename = Filename!!
        this.ext = ext
        this.uri = uri
        this.bitmap = bitmap
    }

}