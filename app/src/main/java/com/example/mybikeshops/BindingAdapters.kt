package com.example.mybikeshops

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mybikeshops.bikeshoplist.BikeShopListAdapter
import com.example.mybikeshops.bikeshoplist.BikeShopItem

@BindingAdapter("listData")
fun setRecyclerViewProperties(recyclerView: RecyclerView,
                                  items: List<BikeShopItem>) {
    if (recyclerView.adapter is BikeShopListAdapter) {
        (recyclerView.adapter as BikeShopListAdapter).setData(items)
    }
}

@BindingAdapter("bikeShopImage")
fun bindBikeShopImage(bikeShopImageView: ImageView,
                   imageUrl: String) {
    Glide.with(bikeShopImageView)
        .load(imageUrl)
        .centerCrop()
        .placeholder(R.drawable.ic_baseline_cloud_download_24)
        .error(R.drawable.ic_baseline_error_outline_24)
        .into(bikeShopImageView)
}
