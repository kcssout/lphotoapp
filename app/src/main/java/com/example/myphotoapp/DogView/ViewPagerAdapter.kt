package com.example.myphotoapp.DogView

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter
import com.example.myphotoapp.R
import java.util.*
import kotlin.collections.ArrayList


class ViewPagerAdapter(context: Context, images: ArrayList<Bitmap>?) : PagerAdapter() {
    // Context object
    var context: Context
    // Array of images
    var images: ArrayList<Bitmap>
    // Layout Inflater
    var mLayoutInflater: LayoutInflater

    override fun getCount(): Int { // return the number of images
        return images.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as LinearLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any { // inflating the photo_item.xmlem.xml
        val itemView: View = mLayoutInflater.inflate(R.layout.photo_item, container, false)

        // referencing the image view from the photo_item.xmlem.xml file
        val imageView: ImageView = itemView.findViewById(R.id.imageViewMain) as ImageView
        // setting the image in the imageView
        imageView.setImageBitmap(images.get(position))
        // Adding the View
        Objects.requireNonNull(container).addView(itemView)
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }

    // Viewpager Constructor
    init {
        this.context = context
        this.images = images!!
        mLayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }
}