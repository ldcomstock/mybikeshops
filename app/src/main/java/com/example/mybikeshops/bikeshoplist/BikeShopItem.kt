package com.example.mybikeshops.bikeshoplist

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BikeShopItem(
    val name: String,
    val photoUrl: String,
    val address: String,
) : Parcelable