package com.myapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.smarteist.autoimageslider.SliderViewAdapter


class SliderAdapter(
    val context: Context?,
    val sliderItems: MutableList<String>) :
    SliderViewAdapter<SliderAdapter.SliderAdapterViewHolder>() {
    // list for storing urls of images.

    // We are inflating the slider_layout
    // inside on Create View Holder method.
    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterViewHolder {
        val inflate: View =
            LayoutInflater.from(parent.context).inflate(com.myapplication.R.layout.slider_layout, null)
        return SliderAdapterViewHolder(inflate)
    }

    // Inside on bind view holder we will
    // set data to item of Slider View.
    override fun onBindViewHolder(viewHolder: SliderAdapterViewHolder, position: Int) {
        // Glide is use to load image
        // from url in your imageview.
        Glide.with(viewHolder.itemView)
            .load(sliderItems[position])
            .into(viewHolder.imageViewBackground)
    }

    // this method will return
    // the count of our list.
    override fun getCount(): Int {
        return sliderItems.size
    }

    class SliderAdapterViewHolder(itemView: View) : ViewHolder(itemView) {
        // Adapter class for initializing
        // the views of our slider view.
        var imageViewBackground: ImageView

        init {
            imageViewBackground = itemView.findViewById(com.myapplication.R.id.myimage)
        }
    }
}