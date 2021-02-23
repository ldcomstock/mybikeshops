package com.example.mybikeshops

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("bikeShopImage")
fun bindBikeShopImage(bikeShopImageView: ImageView,
                   imageUrl: String) {
    Glide.with(bikeShopImageView)
        .load(imageUrl)
        .centerCrop()
        .placeholder(R.drawable.ic_baseline_cloud_download_24)
        .error(R.drawable.ic_baseline_pedal_bike_24)
        .into(bikeShopImageView)
}
